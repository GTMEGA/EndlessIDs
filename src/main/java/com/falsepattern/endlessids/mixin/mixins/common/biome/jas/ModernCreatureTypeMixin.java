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

package com.falsepattern.endlessids.mixin.mixins.common.biome.jas;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import com.google.common.collect.ImmutableMap;
import jas.spawner.modern.spawner.creature.type.CreatureType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.chunk.Chunk;

@Mixin(CreatureType.class)
public class ModernCreatureTypeMixin {
    @Shadow(remap = false) @Final public int defaultBiomeCap;

    @Shadow(remap = false) @Final public ImmutableMap<Integer, Integer> biomeCaps;

    /**
     * @author FalsePattern
     * @reason Painful to do with normal injects because of control flow
     */
    @Overwrite(remap = false)
    public int getChunkCap(Chunk chunk) {
        if (chunk != null && this.defaultBiomeCap > 0) {
            int chunkCap = 0;
            int counter = 0;
            short[] biomeArray = ((ChunkBiomeHook)chunk).getBiomeShortArray();

            for (short value : biomeArray) {
                int biomeID = value & ExtendedConstants.biomeIDMask;
                int columnCap = this.biomeCaps.getOrDefault(biomeID, defaultBiomeCap);
                chunkCap += columnCap;
                ++counter;
            }

            return counter > 0 ? chunkCap / counter : -1;
        } else {
            return -1;
        }
    }
}
