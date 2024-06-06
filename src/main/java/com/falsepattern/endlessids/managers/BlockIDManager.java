package com.falsepattern.endlessids.managers;

import com.falsepattern.chunk.api.ArrayUtil;
import com.falsepattern.chunk.api.DataManager;
import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.mixin.helpers.SubChunkBlockHook;
import com.falsepattern.endlessids.util.DataUtil;
import lombok.val;
import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.nio.ByteBuffer;

import static com.falsepattern.endlessids.constants.ExtendedConstants.blocksPerSubChunk;
import static com.falsepattern.endlessids.util.DataUtil.*;

public class BlockIDManager implements DataManager.PacketDataManager, DataManager.SubChunkDataManager {

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

            storageFlags |= subChunk.getBlockMask() << (i * 2);

            val b1 = subChunk.getB1();
            data.put(b1);

            val b2Low = subChunk.getB2Low();
            if (b2Low == null) {
                continue;
            }
            data.put(b2Low.data);

            val b2High = subChunk.getB2High();
            if (b2High == null) {
                continue;
            }
            data.put(b2High.data);

            val b3 = subChunk.getB3();
            if (b3 != null) {
                data.put(b3);
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
            val b1 = subChunk.getB1();
            buffer.get(b1);
            if (storageFlag == 0b00) {
                subChunk.setB2Low(null);
                subChunk.setB2High(null);
                subChunk.setB3(null);
                continue;
            }
            var b2Low = subChunk.getB2Low();
            if (b2Low == null) {
                b2Low = subChunk.createB2Low();
            }
            buffer.get(b2Low.data);
            if (storageFlag == 0b01) {
                subChunk.setB2High(null);
                subChunk.setB3(null);
                continue;
            }
            var b2High = subChunk.getB2High();
            if (b2High == null) {
                b2High = subChunk.createB2High();
            }
            buffer.get(b2High.data);
            if (storageFlag == 0b10) {
                subChunk.setB3(null);
                continue;
            }
            var b3 = subChunk.getB3();
            if (b3 == null) {
                b3 = subChunk.createB3();
            }
            buffer.get(b3);
        }
    }

    @Override
    public boolean subChunkPrivilegedAccess() {
        return true;
    }


    @Override
    public void writeSubChunkToNBT(Chunk chunk, ExtendedBlockStorage subChunkVanilla, NBTTagCompound nbt) {
        val subChunk = (SubChunkBlockHook) subChunkVanilla;
        val b1 = subChunk.getB1();
        val b2Low = subChunk.getB2Low();
        val b2High = subChunk.getB2High();
        val b3 = subChunk.getB3();
        nbt.setByteArray("Blocks", b1);
        if (b2Low != null) {
            nbt.setByteArray("Add", b2Low.data);
        }
        if (b2High != null) {
            nbt.setByteArray("BlocksB2Hi", b2High.data);
        }
        if (b3 != null) {
            nbt.setByteArray("BlocksB3", b3);
        }
    }

    //NotEnoughIDs world compatibility
    public static void readSubChunkFromNBTNotEnoughIDsDFU(SubChunkBlockHook subChunk, NBTTagCompound nbt) {
        val data = nbt.getByteArray("Blocks16");
        val dataShort = new short[data.length >>> 1];
        ByteBuffer.wrap(data).asShortBuffer().get(dataShort);
        val b1 = new byte[blocksPerSubChunk];
        val b2L = new byte[blocksPerSubChunk >>> 1];
        val b2H = new byte[blocksPerSubChunk >>> 1];
        for (int i = 0; i < dataShort.length; i++) {
            val s = dataShort[i];
            val nI = i >>> 1;
            val mI = (i & 1) * 4;
            b1[i] = (byte) (s & 0x00FF);
            b2L[nI] |= (byte) (((s & 0x0F00) >>> 8) << mI);
            b2H[nI] |= (byte) (((s & 0xF000) >>> 12) << mI);
        }
        subChunk.setB1(b1);
        subChunk.setB2Low(new NibbleArray(b2L, 4));
        subChunk.setB2High(new NibbleArray(b2H, 4));
    }

    @Override
    public void readSubChunkFromNBT(Chunk chunk, ExtendedBlockStorage subChunkVanilla, NBTTagCompound nbt) {
        val subChunk = (SubChunkBlockHook) subChunkVanilla;
        if (nbt.hasKey("Blocks16")) {
            readSubChunkFromNBTNotEnoughIDsDFU(subChunk, nbt);
            return;
        }
        assert nbt.hasKey("Blocks");
        val b1 = nbt.getByteArray("Blocks");
        final byte[] b2Low = nbt.hasKey("Add") ? nbt.getByteArray("Add") : null;
        final byte[] b2High = nbt.hasKey("BlocksB2Hi") ? nbt.getByteArray("BlocksB2Hi") : null;
        final byte[] b3 = nbt.hasKey("BlocksB3") ? nbt.getByteArray("BlocksB3") : null;

        subChunk.setB1(ensureSubChunkByteArray(b1));
        subChunk.setB2Low(ensureSubChunkNibbleArray(b2Low));
        subChunk.setB2High(ensureSubChunkNibbleArray(b2High));
        subChunk.setB3(ensureSubChunkByteArray(b3));
    }

    @Override
    public void cloneSubChunk(Chunk fromChunk, ExtendedBlockStorage fromVanilla, ExtendedBlockStorage toVanilla) {
        val from = (SubChunkBlockHook) fromVanilla;
        val to = (SubChunkBlockHook) toVanilla;

        to.setB1(ArrayUtil.copyArray(from.getB1(), to.getB1()));
        to.setB2Low(ArrayUtil.copyArray(from.getB2Low(), to.getB2Low()));
        to.setB2High(ArrayUtil.copyArray(from.getB2High(), to.getB2High()));
        to.setB3(ArrayUtil.copyArray(from.getB3(), to.getB3()));
    }

    @Override
    public @NotNull String version() {
        return Tags.VERSION;
    }

    @Override
    public @Nullable String newInstallDescription() {
        return "EndlessIDs extended block IDs. Vanilla worlds can be converted to EndlessIDs worlds safely.";
    }

    @Override
    public @NotNull String uninstallMessage() {
        return "EndlessIDs extended block IDs removed. Any blocks with IDs above 4095 will get corrupted.";
    }

    @Override
    public @Nullable String versionChangeMessage(String priorVersion) {
        return null;
    }
}
