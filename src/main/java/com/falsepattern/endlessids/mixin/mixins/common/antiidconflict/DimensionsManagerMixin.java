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

import code.elix_x.coremods.antiidconflict.managers.DimensionsManager;
import com.falsepattern.endlessids.mixin.helpers.AIDCStringFixer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = DimensionsManager.class,
       remap = false)
public abstract class DimensionsManagerMixin {

    @ModifyConstant(method = "*",
                    constant = {@Constant(stringValue = "\\dimensions"),
                                @Constant(stringValue = "\\main.cfg"),
                                @Constant(stringValue = "\\avaibleIDs.txt"),
                                @Constant(stringValue = "\\occupiedIDs.txt"),
                                @Constant(stringValue = "\\AllIDs.txt")})
    private static String fixPaths(String original) {
        return AIDCStringFixer.fixString(original);
    }
}
