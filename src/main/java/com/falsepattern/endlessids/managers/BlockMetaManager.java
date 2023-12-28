package com.falsepattern.endlessids.managers;

import com.falsepattern.chunk.api.ArrayUtil;
import com.falsepattern.chunk.api.DataManager;
import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.mixin.helpers.SubChunkBlockHook;
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
    public void writeToBuffer(Chunk chunk, int subChunkMask, boolean forceUpdate, ByteBuffer data) {
        val subChunkList = chunk.getBlockStorageArray();
        int storageFlags = 0;
        val start = data.position() + 4;
        data.position(start);
        for (int i = 0; i < 16; i++) {
            if ((subChunkMask & (1 << i)) == 0 || subChunkList[i] == null) {
                continue;
            }
            val subChunk = (SubChunkBlockHook) subChunkList[i];

            storageFlags |= subChunk.getMetadataMask() << (i * 2);

            val m1Low = subChunk.getM1Low();
            data.put(m1Low.data);

            val m1High = subChunk.getM1High();
            if (m1High == null) {
                continue;
            }
            data.put(m1High.data);

            val m2 = subChunk.getM2();
            if (m2 != null) {
                data.put(m2);
            }
        }
        data.putInt(start - 4, storageFlags);
    }

    @Override
    public void readFromBuffer(Chunk chunk, int subChunkMask, boolean forceUpdate, ByteBuffer buffer) {
        val subChunkList = chunk.getBlockStorageArray();
        val storageFlags = buffer.getInt();
        for (int i = 0; i < 16; i++) {
            if ((subChunkMask & (1 << i)) == 0 || subChunkList[i] == null) {
                continue;
            }
            val subChunk = (SubChunkBlockHook) subChunkList[i];
            val storageFlag = (storageFlags >>> (i * 2)) & 3;
            val m1Low = subChunk.getM1Low();
            buffer.get(m1Low.data);
            if (storageFlag == 0b01) {
                subChunk.setM1High(null);
                subChunk.setM2(null);
                continue;
            }
            var m1High = subChunk.getM1High();
            if (m1High == null) {
                m1High = subChunk.createM1High();
            }
            buffer.get(m1High.data);
            if (storageFlag == 0b10) {
                subChunk.setM2(null);
                continue;
            }
            var m2 = subChunk.getM2();
            if (m2 == null) {
                m2 = subChunk.createM2();
            }
            buffer.get(m2);
        }
    }

    @Override
    public boolean subChunkPrivilegedAccess() {
        return true;
    }

    @Override
    public void writeSubChunkToNBT(Chunk chunk, ExtendedBlockStorage subChunkVanilla, NBTTagCompound nbt) {
        val subChunk = (SubChunkBlockHook) subChunkVanilla;
        val m1Low = subChunk.getM1Low();
        val m1High = subChunk.getM1High();
        val m2 = subChunk.getM2();
        nbt.setByteArray("Data", m1Low.data);
        if (m1High != null) {
            nbt.setByteArray("Data1High", m1High.data);
        }
        if (m2 != null) {
            nbt.setByteArray("Data2", m2);
        }
    }

    @Override
    public void readSubChunkFromNBT(Chunk chunk, ExtendedBlockStorage subChunkVanilla, NBTTagCompound nbt) {
        val subChunk = (SubChunkBlockHook) subChunkVanilla;
        assert nbt.hasKey("Data");
        val m1Low = nbt.getByteArray("Data");
        final byte[] m1High = nbt.hasKey("Data1High") ? nbt.getByteArray("Data1High") : null;
        final byte[] m2 = nbt.hasKey("Data2") ? nbt.getByteArray("Data2") : null;

        subChunk.setM1Low(new NibbleArray(m1Low, 4));
        subChunk.setM1High(m1High == null ? null : new NibbleArray(m1High, 4));
        subChunk.setM2(m2);
    }

    @Override
    public void cloneSubChunk(Chunk fromChunk, ExtendedBlockStorage fromVanilla, ExtendedBlockStorage toVanilla) {
        val from = (SubChunkBlockHook) fromVanilla;
        val to = (SubChunkBlockHook) toVanilla;

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
