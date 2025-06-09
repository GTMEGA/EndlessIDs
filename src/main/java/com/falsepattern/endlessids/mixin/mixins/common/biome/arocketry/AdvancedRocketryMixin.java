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

package com.falsepattern.endlessids.mixin.mixins.common.biome.arocketry;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import zmaster587.advancedRocketry.AdvancedRocketry;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

@Mixin(value = AdvancedRocketry.class,
       remap = false)
public abstract class AdvancedRocketryMixin {
    @WrapOperation(method = "load",
                   at = @At(value = "INVOKE",
                            target = "Lnet/minecraftforge/common/config/Configuration;get(Ljava/lang/String;Ljava/lang/String;I)Lnet/minecraftforge/common/config/Property;"))
    private Property shiftBiomeIDsUp(Configuration instance, String category, String key, int defaultValue, Operation<Property> original) {
        if ("Biomes".equals(category)) {
            defaultValue += 9000;
        }
        return original.call(instance, category, key, defaultValue);
    }

    @ModifyConstant(method = "preInit",
                    constant = @Constant(intValue = -2),
                    require = 1)
    private int changeDefaultDimID(int constant) {
        return -5613;
    }
}
