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
import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import lombok.val;
import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;

import java.nio.ByteBuffer;

//NOTE: Also change the save logic in MapWriter mod if changing this
public class BiomeManager implements DataManager.PacketDataManager, DataManager.ChunkDataManager {

    @Override
    public String domain() {
        return Tags.MODID;
    }

    @Override
    public String id() {
        return "biome";
    }

    @Override
    public int maxPacketSize() {
        return 2 * 16 * 16;
    }

    @Override
    public void writeToBuffer(Chunk chunk, int subChunkMask, boolean forceUpdate, ByteBuffer data) {
        data.asShortBuffer().put(((ChunkBiomeHook)chunk).getBiomeShortArray());
        data.position(data.position() + maxPacketSize());
    }

    @Override
    public void readFromBuffer(Chunk chunk, int subChunkMask, boolean forceUpdate, ByteBuffer buffer) {
        var arr = ((ChunkBiomeHook)chunk).getBiomeShortArray();
        if (arr == null) {
            arr = new short[16 * 16];
            ((ChunkBiomeHook)chunk).setBiomeShortArray(arr);
        }
        buffer.asShortBuffer().get(arr);
        buffer.position(buffer.position() + maxPacketSize());
    }

    @Override
    public boolean chunkPrivilegedAccess() {
        return true;
    }

    @Override
    public void writeChunkToNBT(Chunk chunk, NBTTagCompound nbt) {
        byte[] arr = Hooks.shortToByteArray(((ChunkBiomeHook)chunk).getBiomeShortArray());
        nbt.setByteArray("Biomes16v2", arr);
    }

    @Override
    public void readChunkFromNBT(Chunk chunk, NBTTagCompound nbt) {
        var data = ((ChunkBiomeHook) chunk).getBiomeShortArray();
        if (data == null) {
            data = new short[16 * 16];
            ((ChunkBiomeHook)chunk).setBiomeShortArray(data);
        }
        if (nbt.hasKey("Biomes16v2", 7)) {
            Hooks.byteToShortArray(nbt.getByteArray("Biomes16v2"), 0, data, 0, data.length * 2);
        } else if (nbt.hasKey("Biomes16", 7)) { //NotEnoughBiomes DFU
            Hooks.byteToShortArrayLegacy(nbt.getByteArray("Biomes16"), data);
        } else if (nbt.hasKey("Biomes", 7)) { //Vanilla DFU
            Hooks.scatter(nbt.getByteArray("Biomes"), data);
        }

    }

    @Override
    public void cloneChunk(Chunk fromVanilla, Chunk toVanilla) {
        val from = (ChunkBiomeHook) fromVanilla;
        val to = (ChunkBiomeHook) toVanilla;

        to.setBiomeShortArray(ArrayUtil.copyArray(from.getBiomeShortArray(), to.getBiomeShortArray()));
    }

    @Override
    public @NotNull String version() {
        return Tags.MODID;
    }

    @Override
    public @Nullable String newInstallDescription() {
        return "EndlessIDs extended biome data. Vanilla biome data is limited to 256 biomes. This allows for 65536 biomes.";
    }

    @Override
    public @NotNull String uninstallMessage() {
        return "EndlessIDs extended biome data has been uninstalled. Biomes are limited to 256,\n" +
               "and any biomes with IDs above 255 in the save will be corrupted.";
    }

    @Override
    public @Nullable String versionChangeMessage(String priorVersion) {
        return null;
    }
}
