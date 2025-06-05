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

package com.falsepattern.endlessids.mixin.mixins.common.biome.dimdoors;

import StevenDimDoors.mod_pocketDim.world.DDBiomeGenBase;
import com.falsepattern.endlessids.PlaceholderBiome;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(value = DDBiomeGenBase.class,
       priority = 999,
       remap = false)
public abstract class DDBiomeGenBaseMixin extends BiomeGenBase {
    private DDBiomeGenBaseMixin(int p_i1971_1_) {
        super(p_i1971_1_);
    }

    /**
     * @author FalsePattern
     * @reason Compat patch
     */
    @Overwrite
    public static void checkBiomes(int[] biomes) {
        val arr = getBiomeGenArray();
        for(int k = 0; k < biomes.length; ++k) {
            val b = arr[biomes[k]];
            if (b != null && !(b instanceof DDBiomeGenBase || b instanceof PlaceholderBiome)) {
                throw new IllegalStateException("There is a biome ID conflict between a biome from Dimensional Doors and another biome type. Fix your configuration!");
            }
        }
    }
}
