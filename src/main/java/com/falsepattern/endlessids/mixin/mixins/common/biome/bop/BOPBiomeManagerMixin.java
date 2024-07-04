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

package com.falsepattern.endlessids.mixin.mixins.common.biome.bop;

import biomesoplenty.common.world.BOPBiomeManager;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = BOPBiomeManager.class,
       remap = false)
public abstract class BOPBiomeManagerMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = 40),
                    require = 1)
    private static int shiftBiomeIDsUp(int id) {
        return id + 5000;
    }

    @ModifyConstant(method = "getNextFreeBiomeId",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 1)
    private static int extendIDs1(int constant) {
        return ExtendedConstants.biomeIDCount;
    }

    @ModifyConstant(method = "getNextFreeBiomeId",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount - 1),
                    require = 1)
    private static int extendIDs2(int constant) {
        return ExtendedConstants.biomeIDCount - 1;
    }
}
