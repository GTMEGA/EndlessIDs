/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2025 FalsePattern, The MEGA Team
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the word "MEGA"
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

package com.falsepattern.endlessids.mixin.mixins.common.biome.mystcraft;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import com.xcompwiz.mystcraft.symbol.symbols.SymbolFloatingIslands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Mixin(SymbolFloatingIslands.BiomeReplacer.class)
public abstract class BiomeReplacerMixin {
    @Shadow(remap = false) private HashMap<List<Integer>, boolean[]> chunks;

    @Shadow(remap = false) private BiomeGenBase biome;

    /**
     * @author FalsePattern
     * @reason Control flow, hard to fix with mixins
     */
    @Overwrite(remap = false)
    public void finalizeChunk(Chunk chunk, int chunkX, int chunkZ) {
        boolean[] modified = this.chunks.remove(Arrays.asList(chunkX, chunkZ));
        if (modified != null) {
            short[] biomes = ((ChunkBiomeHook)chunk).getBiomeShortArray();

            for(int coords = 0; coords < modified.length; ++coords) {
                if (modified[coords]) {
                    biomes[coords] = (short)(this.biome.biomeID & ExtendedConstants.biomeIDMask);
                }
            }

        }
    }
}
