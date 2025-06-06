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

package com.falsepattern.endlessids.mixin.helpers;

import lombok.val;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.function.IntFunction;

import static com.falsepattern.endlessids.EndlessIDs.FAKE_BIOME_ARRAY_PLACEHOLDER;
import static com.falsepattern.endlessids.EndlessIDs.ZERO_LENGTH_BIOME_ARRAY_PLACEHOLDER;

public class BiomePatchHelper {
    public static byte[] getBiomeArrayTweaked(Chunk chunk, IntFunction<BiomeGenBase> biomesForGeneration) {
        return getBiomeArrayTweaked(chunk, false, biomesForGeneration);
    }
    public static byte[] getBiomeArrayTweaked(Chunk chunk, boolean fake, IntFunction<BiomeGenBase> biomesForGeneration) {
        val chunkBiomes = ((ChunkBiomeHook) chunk).getBiomeShortArray();

        for (int i = 0; i < chunkBiomes.length; ++i) {
            chunkBiomes[i] = (short) biomesForGeneration.apply(i).biomeID;
        }
        return fake ? FAKE_BIOME_ARRAY_PLACEHOLDER : ZERO_LENGTH_BIOME_ARRAY_PLACEHOLDER;
    }

    public static byte[] getBiomeArrayTweaked(Chunk chunk, BiomeGenBase[] biomesForGeneration) {
        val chunkBiomes = ((ChunkBiomeHook) chunk).getBiomeShortArray();

        for (int i = 0; i < chunkBiomes.length; ++i) {
            chunkBiomes[i] = (short) biomesForGeneration[i].biomeID;
        }
        return ZERO_LENGTH_BIOME_ARRAY_PLACEHOLDER;
    }
}
