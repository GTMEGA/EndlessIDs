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

package com.falsepattern.endlessids.asm.transformer;

import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.asm.AsmTransformException;
import com.falsepattern.endlessids.asm.AsmUtil;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;
import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import com.falsepattern.lib.turboasm.ClassNodeHandle;
import com.falsepattern.lib.turboasm.TurboClassTransformer;
import lombok.val;
import lombok.var;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class FmlRegistry implements TurboClassTransformer {
    public FmlRegistry() {
        super();
    }

    @Override
    public String owner() {
        return Tags.MODNAME;
    }

    @Override
    public String name() {
        return "FMLRegistry";
    }

    @Override
    public boolean shouldTransformClass(@NotNull String className, @NotNull ClassNodeHandle classNode) {
        return GeneralConfig.extendBlockItem && ("cpw.mods.fml.common.registry.GameData".equals(className) ||
                                                 "cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry".equals(className));
    }

    @Override
    public boolean transformClass(@NotNull String className, @NotNull ClassNodeHandle classNode) {
        val cn = classNode.getNode();
        if (cn == null)
            return false;
        var field = AsmUtil.findField(cn, "MAX_BLOCK_ID", true);
        if (field != null) {
            field.value = ExtendedConstants.maxBlockID;
        }
        field = AsmUtil.findField(cn, "MAX_ITEM_ID", true);
        if (field != null) {
            field.value = ExtendedConstants.maxItemID;
        }
        boolean found = false;
        for (final MethodNode method : cn.methods) {
            if (AsmUtil.transformInlinedSizeMethod(cn, method, VanillaConstants.maxBlockID,
                                                   ExtendedConstants.maxBlockID, true)) {
                found = true;
            }
            AsmUtil.transformInlinedSizeMethod(cn, method, 31999, ExtendedConstants.maxItemID, true);
        }
        if (!found) {
            throw new AsmTransformException("can't find 4095 constant in any method");
        }
        return true;
    }
}
