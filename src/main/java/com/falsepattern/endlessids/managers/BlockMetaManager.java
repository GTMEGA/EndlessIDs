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
import com.falsepattern.endlessids.util.DataUtil;
import lombok.val;
import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.nio.ByteBuffer;

import static com.falsepattern.endlessids.constants.ExtendedConstants.blocksPerSubChunk;

//NOTE: Also change the save logic in MapWriter mod if changing this
public class BlockMetaManager implements DataManager.PacketDataManager, DataManager.SubChunkDataManager, DataManager.BlockPacketDataManager {

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

            storageFlags |= subChunk.eid$getMetadataMask() << (i * 2);

            val m1Low = subChunk.eid$getM1Low();
            data.put(m1Low.data);

            val m1High = subChunk.eid$getM1High();
            if (m1High == null) {
                continue;
            }
            data.put(m1High.data);

            val m2 = subChunk.eid$getM2();
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
            val m1Low = subChunk.eid$getM1Low();
            buffer.get(m1Low.data);
            if (storageFlag == 0b01) {
                subChunk.eid$setM1High(null);
                subChunk.eid$setM2(null);
                continue;
            }
            var m1High = subChunk.eid$getM1High();
            if (m1High == null) {
                m1High = subChunk.eid$createM1High();
            }
            buffer.get(m1High.data);
            if (storageFlag == 0b10) {
                subChunk.eid$setM2(null);
                continue;
            }
            var m2 = subChunk.eid$getM2();
            if (m2 == null) {
                m2 = subChunk.eid$createM2();
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
        val m1Low = subChunk.eid$getM1Low();
        val m1High = subChunk.eid$getM1High();
        val m2 = subChunk.eid$getM2();
        nbt.setByteArray("Data", m1Low.data);
        if (m1High != null) {
            nbt.setByteArray("Data1High", m1High.data);
        }
        if (m2 != null) {
            nbt.setByteArray("Data2", m2);
        }
    }

    //NotEnoughIDs world compatibility
    private void readSubChunkFromNBTNotEnoughIDsDFU(SubChunkBlockHook subChunk, NBTTagCompound nbt) {
        val data = nbt.getByteArray("Data16");
        val dataShort = new short[data.length >>> 1];
        ByteBuffer.wrap(data).asShortBuffer().get(dataShort);
        val m1Low = new byte[blocksPerSubChunk >>> 1];
        val m1High = new byte[blocksPerSubChunk >>> 1];
        val m2 = new byte[blocksPerSubChunk];
        for (int i = 0; i < dataShort.length; i++) {
            val s = dataShort[i];
            val nI = i >>> 1;
            val mI = (i & 1) * 4;
            m1Low[nI] |= (byte) ((s & 0x000F) << mI);
            m1High[nI] |= (byte) (((s & 0x00F0) >>> 4) << mI);
            m2[i] = (byte) ((s & 0xFF00) >>> 8);
        }
        subChunk.eid$setM1Low(new NibbleArray(m1Low, 4));
        subChunk.eid$setM1High(new NibbleArray(m1High, 4));
        subChunk.eid$setM2(m2);
    }

    @Override
    public void readSubChunkFromNBT(Chunk chunk, ExtendedBlockStorage subChunkVanilla, NBTTagCompound nbt) {
        val subChunk = (SubChunkBlockHook) subChunkVanilla;
        if (nbt.hasKey("Data16")) {
            readSubChunkFromNBTNotEnoughIDsDFU(subChunk, nbt);
            return;
        }
        assert nbt.hasKey("Data");
        val m1Low = nbt.getByteArray("Data");
        final byte[] m1High = nbt.hasKey("Data1High") ? nbt.getByteArray("Data1High") : null;
        final byte[] m2 = nbt.hasKey("Data2") ? nbt.getByteArray("Data2") : null;

        subChunk.eid$setM1Low(DataUtil.ensureSubChunkNibbleArray(m1Low));
        subChunk.eid$setM1High(DataUtil.ensureSubChunkNibbleArray(m1High));
        subChunk.eid$setM2(DataUtil.ensureSubChunkByteArray(m2));
    }

    @Override
    public void cloneSubChunk(Chunk fromChunk, ExtendedBlockStorage fromVanilla, ExtendedBlockStorage toVanilla) {
        val from = (SubChunkBlockHook) fromVanilla;
        val to = (SubChunkBlockHook) toVanilla;

        to.eid$setM1Low(ArrayUtil.copyArray(from.eid$getM1Low(), to.eid$getM1Low()));
        to.eid$setM1High(ArrayUtil.copyArray(from.eid$getM1High(), to.eid$getM1High()));
        to.eid$setM2(ArrayUtil.copyArray(from.eid$getM2(), to.eid$getM2()));
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

    @Override
    public void writeBlockToPacket(Chunk chunk, int x, int y, int z, S23PacketBlockChange packet) {

    }

    @Override
    public void readBlockFromPacket(Chunk chunk, int x, int y, int z, S23PacketBlockChange packet) {

    }

    @Override
    public void writeBlockPacketToBuffer(S23PacketBlockChange packet, PacketBuffer buffer) {
        buffer.writeShort(packet.field_148884_e & 0xFFFF);
    }

    @Override
    public void readBlockPacketFromBuffer(S23PacketBlockChange packet, PacketBuffer buffer) {
        packet.field_148884_e = buffer.readUnsignedShort();
    }
}
