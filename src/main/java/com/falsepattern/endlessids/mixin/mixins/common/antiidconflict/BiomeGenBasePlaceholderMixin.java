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

package com.falsepattern.endlessids.mixin.mixins.common.antiidconflict;

import code.elix_x.coremods.antiidconflict.core.AsmHooks;
import com.falsepattern.endlessids.PlaceholderBiome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(BiomeGenBase.class)
public abstract class BiomeGenBasePlaceholderMixin {
    @Shadow
    @Final
    private static BiomeGenBase[] biomeList;

    @SuppressWarnings({"InvalidInjectorMethodSignature", "UnresolvedMixinReference", "MixinAnnotationTarget"})
    @Redirect(method = "<init>(IZ)V",
              at = @At(value = "INVOKE",
                       target = "Lcode/elix_x/coremods/antiidconflict/core/AsmHooks;getBiomeID(IZ)I",
                       remap = false),
              require = 1)
    private int removePlaceholders(int id, boolean register) {
        if (register) {
            if (biomeList[id] instanceof PlaceholderBiome) {
                biomeList[id] = null;
            }
        }
        return AsmHooks.getBiomeID(id, register);
    }
}
