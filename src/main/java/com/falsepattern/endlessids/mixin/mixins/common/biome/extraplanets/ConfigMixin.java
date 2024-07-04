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

package com.falsepattern.endlessids.mixin.mixins.common.biome.extraplanets;

import com.mjr.extraplanets.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

@Mixin(value = Config.class,
       remap = false)
public abstract class ConfigMixin {
    @Redirect(method = "load",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraftforge/common/config/Configuration;get(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)Lnet/minecraftforge/common/config/Property;"))
    private static Property shiftBiomeIDsUp(Configuration config, String category, String key, int defaultValue, String comment) {
        if (category.equals("biomeID")) {
            defaultValue += 17000;
            comment = "[range: 0 ~ 65535, default: " + defaultValue + "]";
        }
        return config.get(category, key, defaultValue, comment);
    }
}
