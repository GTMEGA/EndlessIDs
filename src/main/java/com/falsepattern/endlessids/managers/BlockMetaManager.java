package com.falsepattern.endlessids.managers;

import com.falsepattern.chunk.api.ArrayUtil;
import com.falsepattern.chunk.api.DataManager;
import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import lombok.val;
import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.nio.ByteBuffer;

public class BlockMetaManager implements DataManager.PacketDataManager, DataManager.SubChunkDataManager {

    @Override
    public String domain() {
        return Tags.MODID;
    }

    @Override
    public String id() {
        return "metadata";
    }

    @Override
    public int maxPacketSize() {
        return 2 * 16 * 16 * 16 * 16 + 4;
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

            storageFlags |= ebs.getEBSMask() << (i * 2);

            val m1Low = ebs.getM1Low();
            data.put(m1Low.data);

            val m1High = ebs.getM1High();
            if (m1High == null) {
                continue;
            }
            data.put(m1High.data);

            val m2 = ebs.getM2();
            if (m2 != null) {
                data.put(m2);
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
            val m1Low = ebs.getM1Low();
            buffer.get(m1Low.data);
            if (storageFlag == 0b01) {
                ebs.setM1High(null);
                ebs.setM2(null);
                continue;
            }
            var m1High = ebs.getM1High();
            if (m1High == null) {
                m1High = ebs.createM1High();
            }
            buffer.get(m1High.data);
            if (storageFlag == 0b10) {
                ebs.setM2(null);
                continue;
            }
            var m2 = ebs.getM2();
            if (m2 == null) {
                m2 = ebs.createM2();
            }
            buffer.get(m2);
        }
    }

    @Override
    public boolean subChunkPrivilegedAccess() {
        return true;
    }

    @Override
    public void writeSubChunkToNBT(Chunk chunk, ExtendedBlockStorage ebsVanilla, NBTTagCompound section) {
        val ebs = (IExtendedBlockStorageMixin) ebsVanilla;
        val m1Low = ebs.getM1Low();
        val m1High = ebs.getM1High();
        val m2 = ebs.getM2();
        section.setByteArray("Data", m1Low.data);
        if (m1High != null) {
            section.setByteArray("Data1High", m1High.data);
        }
        if (m2 != null) {
            section.setByteArray("Data2", m2);
        }
    }

    @Override
    public void readSubChunkFromNBT(Chunk chunk, ExtendedBlockStorage ebsVanilla, NBTTagCompound section) {
        val ebs = (IExtendedBlockStorageMixin) ebsVanilla;
        assert section.hasKey("Data");
        val m1Low = section.getByteArray("Data");
        final byte[] m1High = section.hasKey("Data1High") ? section.getByteArray("Data1High") : null;
        final byte[] m2 = section.hasKey("Data2") ? section.getByteArray("Data2") : null;

        ebs.setM1Low(new NibbleArray(m1Low, 4));
        ebs.setM1High(m1High == null ? null : new NibbleArray(m1High, 4));
        ebs.setM2(m2);
    }

    @Override
    public void cloneSubChunk(Chunk fromChunk, ExtendedBlockStorage fromVanilla, ExtendedBlockStorage toVanilla) {
        val from = (IExtendedBlockStorageMixin) fromVanilla;
        val to = (IExtendedBlockStorageMixin) toVanilla;

        to.setM1Low(ArrayUtil.copyArray(from.getM1Low(), to.getM1Low()));
        to.setM1High(ArrayUtil.copyArray(from.getM1High(), to.getM1High()));
        to.setM2(ArrayUtil.copyArray(from.getM2(), to.getM2()));
    }

    @Override
    public @NotNull String version() {
        return Tags.VERSION;
    }

    @Override
    public @Nullable String newInstallDescription() {
        return "EndlessIDs extended block metadata. Vanilla worlds can be converted to EndlessIDs worlds safely.";
    }

    @Override
    public @NotNull String uninstallMessage() {
        return "EndlessIDs extended block metadata removed. Any blocks with metadata above 15 will get corrupted.";
    }

    @Override
    public @Nullable String versionChangeMessage(String priorVersion) {
        return null;
    }
}
