package com.falsepattern.endlessids;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import lombok.val;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import java.nio.ShortBuffer;
import java.nio.ByteBuffer;

public class Hooks {

    public static byte[] getBlockData(final IExtendedBlockStorageMixin ebs) {
        val lsb = ebs.getLSB();
        val msb = ebs.getMSB();
        final byte[] ret = new byte[lsb.length * 3];
        ByteBuffer.wrap(ret).asShortBuffer().put(lsb);
        System.arraycopy(msb, 0, ret, lsb.length * 2, lsb.length);
        return ret;
    }
    
    public static void setBlockData(final IExtendedBlockStorageMixin ebs, final byte[] data, final int offset) {
        val lsb = ebs.getLSB();
        val msb = ebs.getMSB();
        val lsbBytes = lsb.length * 2;
        ShortBuffer.wrap(ebs.getLSB()).put(ByteBuffer.wrap(data, offset, lsbBytes).asShortBuffer());
        System.arraycopy(data, offset + lsbBytes, msb, 0, msb.length);
    }

    public static void writeChunkToNbt(final NBTTagCompound nbt, final IExtendedBlockStorageMixin ebs) {
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
            byte1Data[i] = (byte)(idLSB & 0xFF);

            if (idLSB <= 0xFF) continue;
            val nibbleIndex = i >> 1;
            val nibbleShift = (i & 1) * 4;
            if (byte2BottomNibbleData == null) {
                byte2BottomNibbleData = new byte[ebsLSB.length / 2];
            }
            byte2BottomNibbleData[nibbleIndex] |= ((idLSB >>> 8) & 0xF) << nibbleShift;

            if (idLSB <= 0xFFF) continue;
            if (byte2TopNibbleData == null) {
                byte2TopNibbleData = new byte[ebsLSB.length / 2];
            }
            byte2TopNibbleData[nibbleIndex] |= ((idLSB >>> 12) & 0xF) << nibbleShift;

        }
        nbt.setByteArray("Blocks", byte1Data);
        if (byte2BottomNibbleData != null)
            nbt.setByteArray("Add", byte2BottomNibbleData);
        if (byte2TopNibbleData != null)
            nbt.setByteArray("BlocksB2Hi", byte2TopNibbleData);
        if (byte3Data != null)
            nbt.setByteArray("BlocksB3", byte3Data);
    }

    public static void readChunkFromNbt(final IExtendedBlockStorageMixin ebs, final NBTTagCompound nbt) {
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
            lsb[i] = (short)id;
        }
    }

    public static void removeInvalidBlocksHook(final IExtendedBlockStorageMixin ebs) {
        val blkIdsLSB = ebs.getLSB();
        val blkIdsMSB = ebs.getMSB();
        int cntNonEmpty = 0;
        int cntTicking = 0;
        for (int off = 0; off < blkIdsLSB.length; ++off) {
            final int id = ((blkIdsLSB[off] & 0xFFFF) | ((blkIdsMSB[off] & 0xFF) << 16)) & ExtendedConstants.blockIDMask;
            if (id > 0) {
                final Block block = (Block)Block.blockRegistry.getObjectById(id);
                if (block == null) {
                    if (IEConfig.removeInvalidBlocks) {
                        blkIdsLSB[off] = 0;
                    }
                }
                else if (block != Blocks.air) {
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
        if (IEConfig.catchUnregisteredBlocks && id == -1) {
            throw new IllegalArgumentException("Block " + block + " is not registered. <-- Say about this to the author of this mod, or you can try to enable \"RemoveInvalidBlocks\" option in NEID config.");
        }
        if (id >= 0 && id <= ExtendedConstants.maxBlockID) {
            return id;
        }
        if (id == -1) {
            return Block.getIdFromBlock(oldBlock);
        }
        throw new IllegalArgumentException("id out of range: " + id);
    }
    
}
