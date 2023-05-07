package com.falsepattern.endlessids.managers;

import com.falsepattern.chunk.api.ChunkDataManager;
import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import lombok.val;
import lombok.var;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.nio.ByteBuffer;

public class BlockIDManager implements ChunkDataManager, ChunkDataManager.PacketDataManager, ChunkDataManager.SectionNBTDataManager {

    @Override
    public String domain() {
        return Tags.MODID;
    }

    @Override
    public String id() {
        return "blockid";
    }

    @Override
    public int maxPacketSize() {
        return 3 * 16 * 16 * 16 * 16 + 4;
    }

    @Override
    public void writeToBuffer(Chunk chunk, int ebsMask, boolean forceUpdate, ByteBuffer data) {
        val ebsList = chunk.getBlockStorageArray();
        int storageFlags = 0;
        val start = data.position() + 4;
        data.position(start);
        for (int i = 0; i < 16; i++) {
            if ((ebsMask & (1 << i)) == 0 || ebsList[i] == null) {
                continue;
            }
            val ebs = (IExtendedBlockStorageMixin) ebsList[i];

            storageFlags |= ebs.getEBSMSBMask() << (i * 2);

            val b1 = ebs.getB1();
            data.put(b1);

            val b2Low = ebs.getB2Low();
            if (b2Low == null) {
                continue;
            }
            data.put(b2Low.data);

            val b2High = ebs.getB2High();
            if (b2High == null) {
                continue;
            }
            data.put(b2High.data);

            val b3 = ebs.getB3();
            if (b3 != null) {
                data.put(b3);
            }
        }
        data.putInt(start - 4, storageFlags);
    }

    @Override
    public void readFromBuffer(Chunk chunk, int ebsMask, boolean forceUpdate, ByteBuffer buffer) {
        val ebsList = chunk.getBlockStorageArray();
        val storageFlags = buffer.getInt();
        for (int i = 0; i < 16; i++) {
            if ((ebsMask & (1 << i)) == 0 || ebsList[i] == null) {
                continue;
            }
            val ebs = (IExtendedBlockStorageMixin) ebsList[i];
            val storageFlag = (storageFlags >>> (i * 2)) & 3;
            val b1 = ebs.getB1();
            buffer.get(b1);
            if (storageFlag == 0b00) {
                ebs.clearB2Low();
                ebs.clearB2High();
                ebs.clearB3();
                continue;
            }
            var b2Low = ebs.getB2Low();
            if (b2Low == null) {
                b2Low = ebs.createB2Low();
            }
            buffer.get(b2Low.data);
            if (storageFlag == 0b01) {
                ebs.clearB2High();
                ebs.clearB3();
                continue;
            }
            var b2High = ebs.getB2High();
            if (b2High == null) {
                b2High = ebs.createB2High();
            }
            buffer.get(b2High.data);
            if (storageFlag == 0b10) {
                ebs.clearB3();
                continue;
            }
            var b3 = ebs.getB3();
            if (b3 == null) {
                b3 = ebs.createB3();
            }
            buffer.get(b3);
        }
    }

    @Override
    public boolean sectionPrivilegedAccess() {
        return true;
    }

    @Override
    public void writeSectionToNBT(Chunk chunk, ExtendedBlockStorage ebsVanilla, NBTTagCompound section) {
        val ebs = (IExtendedBlockStorageMixin) ebsVanilla;
        val b1 = ebs.getB1();
        val b2Low = ebs.getB2Low();
        val b2High = ebs.getB2High();
        val b3 = ebs.getB3();
        section.setByteArray("Blocks", b1);
        if (b2Low != null) {
            section.setByteArray("Add", b2Low.data);
        }
        if (b2High != null) {
            section.setByteArray("BlocksB2Hi", b2High.data);
        }
        if (b3 != null) {
            section.setByteArray("BlocksB3", b3);
        }
    }

    //NotEnoughIDs world compatibility
    public static void readSectionFromNBTNotEnoughIDsDFU(IExtendedBlockStorageMixin ebs, NBTTagCompound section) {
        val data = section.getByteArray("Blocks16");
        val dataShort = new short[data.length >>> 1];
        ByteBuffer.wrap(data).asShortBuffer().get(dataShort);
        val b1 = new byte[dataShort.length];
        val b2L = new byte[dataShort.length >>> 1];
        val b2H = new byte[dataShort.length >>> 1];
        for (int i = 0; i < dataShort.length; i++) {
            val s = dataShort[i];
            val nI = i >>> 1;
            val mI = (i & 1) * 4;
            b1[i] = (byte) (s & 0x00FF);
            b2L[nI] |= ((s & 0x0F00) >>> 8) << mI;
            b2H[nI] |= ((s & 0xF000) >>> 12) << mI;
        }
        ebs.setB1(b1);
        ebs.setB2Low(new NibbleArray(b2L, 4));
        ebs.setB2High(new NibbleArray(b2H, 4));
    }

    @Override
    public void readSectionFromNBT(Chunk chunk, ExtendedBlockStorage ebsVanilla, NBTTagCompound section) {
        val ebs = (IExtendedBlockStorageMixin) ebsVanilla;
        if (section.hasKey("Blocks16")) {
            readSectionFromNBTNotEnoughIDsDFU(ebs, section);
            return;
        }
        assert section.hasKey("Blocks");
        val b1 = section.getByteArray("Blocks");
        final byte[] b2Low = section.hasKey("Add") ? section.getByteArray("Add") : null;
        final byte[] b2High = section.hasKey("BlocksB2Hi") ? section.getByteArray("BlocksB2Hi") : null;
        final byte[] b3 = section.hasKey("BlocksB3") ? section.getByteArray("BlocksB3") : null;

        ebs.setB1(b1);
        if (b2Low == null) {
            ebs.clearB2Low();
        } else {
            ebs.setB2Low(new NibbleArray(b2Low, 4));
        }
        if (b2High == null) {
            ebs.clearB2High();
        } else {
            ebs.setB2High(new NibbleArray(b2High, 4));
        }
        ebs.setB3(b3);
    }
}
