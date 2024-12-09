/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2024 FalsePattern
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.falsepattern.endlessids.managers;

import com.falsepattern.chunk.api.ArrayUtil;
import com.falsepattern.chunk.api.DataManager;
import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.mixin.helpers.SubChunkBlockHook;
import lombok.val;
import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.nio.ByteBuffer;

import static com.falsepattern.endlessids.constants.ExtendedConstants.blocksPerSubChunk;
import static com.falsepattern.endlessids.util.DataUtil.*;

//NOTE: Also change the save logic in MapWriter mod if changing this
public class BlockIDManager implements DataManager.PacketDataManager, DataManager.SubChunkDataManager, DataManager.BlockPacketDataManager {

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

            storageFlags |= subChunk.eid$getBlockMask() << (i * 2);

            val b1 = subChunk.eid$getB1();
            data.put(b1);

            val b2Low = subChunk.eid$getB2Low();
            if (b2Low == null) {
                continue;
            }
            data.put(b2Low.data);

            val b2High = subChunk.eid$getB2High();
            if (b2High == null) {
                continue;
            }
            data.put(b2High.data);

            val b3 = subChunk.eid$getB3();
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
            val b1 = subChunk.eid$getB1();
            buffer.get(b1);
            if (storageFlag == 0b00) {
                subChunk.eid$setB2Low(null);
                subChunk.eid$setB2High(null);
                subChunk.eid$setB3(null);
                continue;
            }
            var b2Low = subChunk.eid$getB2Low();
            if (b2Low == null) {
                b2Low = subChunk.eid$createB2Low();
            }
            buffer.get(b2Low.data);
            if (storageFlag == 0b01) {
                subChunk.eid$setB2High(null);
                subChunk.eid$setB3(null);
                continue;
            }
            var b2High = subChunk.eid$getB2High();
            if (b2High == null) {
                b2High = subChunk.eid$createB2High();
            }
            buffer.get(b2High.data);
            if (storageFlag == 0b10) {
                subChunk.eid$setB3(null);
                continue;
            }
            var b3 = subChunk.eid$getB3();
            if (b3 == null) {
                b3 = subChunk.eid$createB3();
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
        val b1 = subChunk.eid$getB1();
        val b2Low = subChunk.eid$getB2Low();
        val b2High = subChunk.eid$getB2High();
        val b3 = subChunk.eid$getB3();
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
        subChunk.eid$setB1(b1);
        subChunk.eid$setB2Low(new NibbleArray(b2L, 4));
        subChunk.eid$setB2High(new NibbleArray(b2H, 4));
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

        subChunk.eid$setB1(ensureSubChunkByteArray(b1));
        subChunk.eid$setB2Low(ensureSubChunkNibbleArray(b2Low));
        subChunk.eid$setB2High(ensureSubChunkNibbleArray(b2High));
        subChunk.eid$setB3(ensureSubChunkByteArray(b3));
    }

    @Override
    public void cloneSubChunk(Chunk fromChunk, ExtendedBlockStorage fromVanilla, ExtendedBlockStorage toVanilla) {
        val from = (SubChunkBlockHook) fromVanilla;
        val to = (SubChunkBlockHook) toVanilla;

        to.eid$setB1(ArrayUtil.copyArray(from.eid$getB1(), to.eid$getB1()));
        to.eid$setB2Low(ArrayUtil.copyArray(from.eid$getB2Low(), to.eid$getB2Low()));
        to.eid$setB2High(ArrayUtil.copyArray(from.eid$getB2High(), to.eid$getB2High()));
        to.eid$setB3(ArrayUtil.copyArray(from.eid$getB3(), to.eid$getB3()));
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

    @Override
    public void writeBlockToPacket(Chunk chunk, int x, int y, int z, S23PacketBlockChange packet) {

    }

    @Override
    public void readBlockFromPacket(Chunk chunk, int x, int y, int z, S23PacketBlockChange packet) {

    }

    @Override
    public void writeBlockPacketToBuffer(S23PacketBlockChange packet, PacketBuffer buffer) {
        buffer.writeInt(Block.getIdFromBlock(packet.field_148883_d));
    }

    @Override
    public void readBlockPacketFromBuffer(S23PacketBlockChange packet, PacketBuffer buffer) {
        packet.field_148883_d = Block.getBlockById(buffer.readInt());
    }
}
