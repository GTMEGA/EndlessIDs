package com.falsepattern.endlessids;

import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import lombok.val;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;

public class Hooks {

    public static void getBlockData(final IExtendedBlockStorageMixin ebs, final byte[] data, final int offset) {
        val lsb = ebs.getLSB();
        val msb = ebs.getMSB();
        Unsafer.arraycopy(lsb, 0, data, offset, lsb.length);
        System.arraycopy(msb, 0, data, offset + lsb.length * 2, msb.length);
    }

    public static void setBlockData(final IExtendedBlockStorageMixin ebs, final byte[] data, final int offset) {
        val lsb = ebs.getLSB();
        val msb = ebs.getMSB();
        Unsafer.arraycopy(data, offset, lsb, 0, lsb.length);
        System.arraycopy(data, offset + lsb.length * 2, msb, 0, msb.length);
    }

    public static void getBlockMeta(final IExtendedBlockStorageMixin ebs, final byte[] data, final int offset) {
        val meta = ebs.getMetaArray();
        Unsafer.arraycopy(meta, 0, data, offset, meta.length);
    }

    public static void setBlockMeta(final IExtendedBlockStorageMixin ebs, final byte[] data, final int offset) {
        val meta = ebs.getMetaArray();
        Unsafer.arraycopy(data, offset, meta, 0, meta.length);
    }

    public static void writeBlockDataToNBT(final NBTTagCompound nbt, final IExtendedBlockStorageMixin ebs) {
        val ebsLSB = ebs.getLSB();
        val ebsMSB = ebs.getMSB();

        val byte1Data = new byte[ebsLSB.length];
        byte[] byte2BottomNibbleData = null;
        byte[] byte2TopNibbleData = null;
        byte[] byte3Data = null;
        for (int i = 0; i < ebsLSB.length; ++i) {
            if (ebsMSB[i] != 0) {
                if (byte3Data == null) {
                    byte3Data = new byte[ebsLSB.length];
                }
                byte3Data[i] = ebsMSB[i];
            }
            val idLSB = (ebsLSB[i] & 0xFFFF);
            byte1Data[i] = (byte) (idLSB & 0xFF);

            if (idLSB <= 0xFF) {
                continue;
            }
            val nibbleIndex = i >> 1;
            val nibbleShift = (i & 1) * 4;
            if (byte2BottomNibbleData == null) {
                byte2BottomNibbleData = new byte[ebsLSB.length / 2];
            }
            byte2BottomNibbleData[nibbleIndex] |= ((idLSB >>> 8) & 0xF) << nibbleShift;

            if (idLSB <= 0xFFF) {
                continue;
            }
            if (byte2TopNibbleData == null) {
                byte2TopNibbleData = new byte[ebsLSB.length / 2];
            }
            byte2TopNibbleData[nibbleIndex] |= ((idLSB >>> 12) & 0xF) << nibbleShift;

        }
        nbt.setByteArray("Blocks", byte1Data);
        if (byte2BottomNibbleData != null) {
            nbt.setByteArray("Add", byte2BottomNibbleData);
        }
        if (byte2TopNibbleData != null) {
            nbt.setByteArray("BlocksB2Hi", byte2TopNibbleData);
        }
        if (byte3Data != null) {
            nbt.setByteArray("BlocksB3", byte3Data);
        }
    }

    public static void readBlockDataFromNBT(final IExtendedBlockStorageMixin ebs, final NBTTagCompound nbt) {
        assert nbt.hasKey("Blocks");
        val byte1Data = nbt.getByteArray("Blocks");
        val byte2BottomNibbleData = nbt.hasKey("Add") ? nbt.getByteArray("Add") : null;
        val byte2TopNibbleData = nbt.hasKey("BlocksB2Hi") ? nbt.getByteArray("BlocksB2Hi") : null;
        val byte3Data = nbt.hasKey("BlocksB3") ? nbt.getByteArray("BlocksB3") : null;

        val lsb = ebs.getLSB();
        val msb = ebs.getMSB();
        for (int i = 0; i < lsb.length; i++) {
            if (byte3Data != null) {
                msb[i] = byte3Data[i];
            }
            int nibbleShift = (i & 1) * 4;
            int id = byte1Data[i] & 0xFF;
            if (byte2BottomNibbleData != null) {
                id |= ((byte2BottomNibbleData[i >> 1] >> nibbleShift) & 0xF) << 8;
            }
            if (byte2TopNibbleData != null) {
                id |= ((byte2TopNibbleData[i >> 1] >> nibbleShift) & 0xF) << 12;
            }
            lsb[i] = (short) id;
        }
    }

    public static void removeInvalidBlocksHook(final IExtendedBlockStorageMixin ebs) {
        val blkIdsLSB = ebs.getLSB();
        val blkIdsMSB = ebs.getMSB();
        int cntNonEmpty = 0;
        int cntTicking = 0;
        for (int off = 0; off < blkIdsLSB.length; ++off) {
            final int id =
                    ((blkIdsLSB[off] & 0xFFFF) | ((blkIdsMSB[off] & 0xFF) << 16)) & ExtendedConstants.blockIDMask;
            if (id > 0) {
                final Block block = (Block) Block.blockRegistry.getObjectById(id);
                if (block == null) {
                    if (GeneralConfig.removeInvalidBlocks) {
                        blkIdsLSB[off] = 0;
                    }
                } else if (block != Blocks.air) {
                    ++cntNonEmpty;
                    if (block.getTickRandomly()) {
                        ++cntTicking;
                    }
                }
            }
        }
        ebs.setBlockRefCount(cntNonEmpty);
        ebs.setTickRefCount(cntTicking);
    }

    public static int getIdFromBlockWithCheck(final Block block, final Block oldBlock) {
        final int id = Block.getIdFromBlock(block);
        if (GeneralConfig.catchUnregisteredBlocks && id == -1) {
            throw new IllegalArgumentException("Block " + block +
                                               " is not registered. <-- Say about this to the author of this mod, or you can try to enable \"RemoveInvalidBlocks\" option in EID config.");
        }
        if (id >= 0 && id <= ExtendedConstants.maxBlockID) {
            return id;
        }
        if (id == -1) {
            return Block.getIdFromBlock(oldBlock);
        }
        throw new IllegalArgumentException("id out of range: " + id);
    }

    public static void shortToByteArray(short[] shortArray, int shortOffset, byte[] byteArray, int byteOffset, int shortCount) {
        for (int i = 0; i < shortCount; i++) {
            short s = shortArray[shortOffset + i];
            byteArray[byteOffset + i * 2] = (byte)(s & 255);
            byteArray[byteOffset + i * 2 + 1] = (byte)((s >>> 8) & 255);
        }
    }

    public static void byteToShortArray(byte[] byteArray, int byteOffset, short[] shortArray, int shortOffset, int byteCount) {
        for (int i = 0; i < byteCount; i++) {
            byte b = byteArray[byteOffset + i];
            shortArray[shortOffset + i / 2] = (short) (i % 2 == 0
                                                       ? (b & 255)
                                                       : (shortArray[shortOffset + i / 2] | ((b & 255) << 8)));
        }
    }

    public static byte[] shortToByteArray(short[] shortArray) {
        byte[] byteArray = new byte[shortArray.length * 2];
        shortToByteArray(shortArray, 0, byteArray, 0, shortArray.length);
        return byteArray;
    }

    public static short[] byteToShortArray(byte[] byteArray) {
        short[] shortArray = new short[(byteArray.length + 1) / 2];
        byteToShortArray(byteArray, 0, shortArray, 0, byteArray.length);
        return shortArray;
    }

    public static void byteToShortArrayLegacy(byte[] byteArray, short[] shortArray) {
        for (int i = 0; i < 256; ++i) {
            shortArray[i] = (short) (((byteArray[i + 256] << 8) & 255) | (byteArray[i] & 255));
        }
    }

    private static void scatter(byte[] byteArray, short[] shortArray) {
        for (int i = 0; i < byteArray.length; i++) {
            shortArray[i] = (short) (byteArray[i] & 0xff);
        }
    }

    public static int readBiomeArrayFromPacket(Chunk chunk, byte[] array, int offset) {
        short[] biomeArray = ((IChunkMixin)chunk).getBiomeShortArray();
        byteToShortArray(array, offset, biomeArray, 0, biomeArray.length * 2);
        return biomeArray.length * 2;
    }

    public static int writeBiomeArrayToPacket(Chunk chunk, byte[] array, int offset) {
        short[] biomeArray = ((IChunkMixin)chunk).getBiomeShortArray();
        shortToByteArray(biomeArray, 0, array, offset, biomeArray.length);
        return biomeArray.length * 2;
    }

    public static void writeChunkBiomeArrayToNbt(Chunk chunk, NBTTagCompound nbt) {
        byte[] byteArray = shortToByteArray(((IChunkMixin) chunk).getBiomeShortArray());
        nbt.setByteArray("Biomes16v2", byteArray);
    }

    public static void readChunkBiomeArrayFromNbt(Chunk chunk, NBTTagCompound nbt) {
        short[] data = ((IChunkMixin)chunk).getBiomeShortArray();
        boolean put = false;
        if (data == null) {
            data = new short[16 * 16];
            put = true;
        }
        if (nbt.hasKey("Biomes16v2", 7)) {
            byteToShortArray(nbt.getByteArray("Biomes16v2"), 0, data, 0, data.length * 2);
        } else if (nbt.hasKey("Biomes16", 7)) {
            byteToShortArrayLegacy(nbt.getByteArray("Biomes16"), data);
        } else if (nbt.hasKey("Biomes", 7)) {
            scatter(nbt.getByteArray("Biomes"), data);
        } else {
            put = false;
        }
        if (put) {
            ((IChunkMixin) chunk).setBiomeShortArray(data);
        }
    }

    public static void readBlockMetaFromNBT(IExtendedBlockStorageMixin instance, NBTTagCompound nbt) {
        short[] data = instance.getMetaArray();
        if (nbt.hasKey("Data16", 7)) {
            byteToShortArray(nbt.getByteArray("Data16"), 0, data, 0, data.length * 2);
        } else {
            scatter(nbt.getByteArray("Data"), data);
        }
    }

    public static void writeBlockMetaToNBT(IExtendedBlockStorageMixin instance, NBTTagCompound nbt) {
        nbt.setByteArray("Data16", shortToByteArray(instance.getMetaArray()));
    }
}
