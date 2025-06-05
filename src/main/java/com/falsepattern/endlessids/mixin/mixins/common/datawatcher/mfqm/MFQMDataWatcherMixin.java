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

package com.falsepattern.endlessids.mixin.mixins.common.datawatcher.mfqm;

import MoreFunQuicksandMod.main.MFQM;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = MFQM.class,
       remap = false)
public abstract class MFQMDataWatcherMixin {
    @ModifyConstant(method = "preInit",
                    constant = {@Constant(intValue = VanillaConstants.maxWatchableID,
                                          ordinal = 0), @Constant(intValue = VanillaConstants.maxWatchableID,
                                                                  ordinal = 1),
                                @Constant(intValue = VanillaConstants.maxWatchableID,
                                          ordinal = 2)},
                    require = 3)
    private int extendWatchableIDLimit(int constant) {
        return ExtendedConstants.maxWatchableID;
    }
}
