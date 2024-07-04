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

package com.falsepattern.endlessids.mixin.mixins.common.biome.darkworld;

import com.falsepattern.endlessids.config.DarkWorldIDConfig;
import matthbo.mods.darkworld.init.ModBiomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = ModBiomes.class,
       remap = false)
public abstract class ModBiomesMixin {
    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 100),
                    require = 1)
    private static int darkOceanID(int id) {
        return DarkWorldIDConfig.darkOceanID;
    }

    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 101),
                    require = 1)
    private static int darkPlainsID(int id) {
        return DarkWorldIDConfig.darkPlainsID;
    }

    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 102),
                    require = 1)
    private static int darkDesertID(int id) {
        return DarkWorldIDConfig.darkDesertID;
    }

    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 103),
                    require = 1)
    private static int darkHillsID(int id) {
        return DarkWorldIDConfig.darkHillsID;
    }

    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 104),
                    require = 1)
    private static int darkForestID(int id) {
        return DarkWorldIDConfig.darkForestID;
    }

    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 105),
                    require = 1)
    private static int darkTaigaID(int id) {
        return DarkWorldIDConfig.darkTaigaID;
    }

    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 106),
                    require = 1)
    private static int darkSwampID(int id) {
        return DarkWorldIDConfig.darkSwampID;
    }
}
