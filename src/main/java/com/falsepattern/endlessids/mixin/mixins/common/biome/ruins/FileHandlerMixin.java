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

package com.falsepattern.endlessids.mixin.mixins.common.biome.ruins;

import atomicstryker.ruins.common.FileHandler;
import atomicstryker.ruins.common.RuinsMod;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FileHandler.class)
public class FileHandlerMixin {
    @ModifyConstant(method = "useGeneric",
                    constant = {@Constant(intValue = RuinsMod.BIOME_NONE)},
                    remap = false)
    private int extendBiomeNone(int constant) {
        return constant - RuinsMod.BIOME_NONE + ExtendedConstants.biomeIDCount;
    }
}
