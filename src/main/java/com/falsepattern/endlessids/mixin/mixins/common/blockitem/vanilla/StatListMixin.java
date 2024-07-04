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

package com.falsepattern.endlessids.mixin.mixins.common.blockitem.vanilla;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.stats.StatList;

@Mixin(StatList.class)
public abstract class StatListMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = {@Constant(intValue = VanillaConstants.blockIDCount)},
                    require = 1)
    private static int modifyMineBlockStatArraySize(int constant) {
        return ExtendedConstants.blockIDCount;
    }

    @ModifyConstant(method = "<clinit>",
                    constant = {@Constant(intValue = VanillaConstants.itemIDCount)},
                    require = 3)
    private static int modifyItemStatArraysSize(int constant) {
        return ExtendedConstants.itemIDCount;
    }

}
