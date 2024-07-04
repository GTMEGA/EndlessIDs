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

package com.falsepattern.endlessids.mixin.mixins.common.biome.twilightforest;

import com.falsepattern.endlessids.PlaceholderBiome;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import twilightforest.biomes.TFBiomeBase;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(value = TFBiomeBase.class,
       remap = false)
public abstract class TFBiomeBaseMixin {
    @ModifyConstant(method = "findNextOpenBiomeId",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 1)
    private static int extendIDs(int constant) {
        return ExtendedConstants.biomeIDCount;
    }

    @Redirect(method = "isConflictAtBiomeID",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/biome/BiomeGenBase;getBiome(I)Lnet/minecraft/world/biome/BiomeGenBase;",
                       remap = true),
              require = 1)
    private static BiomeGenBase ignorePlaceholders(int id) {
        val biome = BiomeGenBase.getBiome(id);
        if (biome instanceof PlaceholderBiome) {
            BiomeGenBase.getBiomeGenArray()[id] = null;
            return null;
        } else {
            return biome;
        }
    }
}
