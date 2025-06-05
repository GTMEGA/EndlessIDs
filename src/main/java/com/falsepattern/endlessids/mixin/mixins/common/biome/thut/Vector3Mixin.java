/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2025 FalsePattern, The MEGA Team
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, only version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.falsepattern.endlessids.mixin.mixins.common.biome.thut;

import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import thut.api.maths.Vector3;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

@Mixin(value = Vector3.class,
       remap = false)
public abstract class Vector3Mixin {

    @Shadow public abstract int intX();

    @Shadow public abstract int intZ();

    /**
     * @author FalsePattern
     * @reason ID Extension
     */
    @Overwrite
    public void setBiome(BiomeGenBase biome, World worldObj) {
        int x = this.intX();
        int z = this.intZ();
        Chunk chunk = worldObj.getChunkFromBlockCoords(x, z);
        val chunkHook = (ChunkBiomeHook) chunk;
        short[] biomes = chunkHook.getBiomeShortArray();
        short newBiome = (short)biome.biomeID;
        int chunkX = Math.abs(x & 15);
        int chunkZ = Math.abs(z & 15);
        int point = chunkX + 16 * chunkZ;
        if (biomes[point] != newBiome) {
            biomes[point] = newBiome;
            chunkHook.setBiomeShortArray(biomes);
            chunk.setChunkModified();
        }

    }
}
