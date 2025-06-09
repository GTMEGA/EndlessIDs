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

package com.falsepattern.endlessids.mixin.mixins.common.blockitem.blockphysics;

import com.bloodnbonesgaming.blockphysics.util.DefinitionMaps;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;

@Mixin(value = DefinitionMaps.class,
       remap = false)
public abstract class DefinitionMapsMixin {

    @Shadow private static HashMap<String, String[]> blockToBlockDefMap;

    /**
     * @author FalsePattern
     * @reason Extra null safety and stuff. Hard to do with normal mixins.
     */
    @Overwrite
    public static String getBlockToBlockDef(String blockName, int meta) {
        val arr = blockToBlockDefMap.get(blockName);
        if (arr == null) {
            return "default";
        }
        if (arr.length <= meta) {
            return "default";
        }
        val elem = arr[meta];
        if (elem == null) {
            return "default";
        }
        return elem;
    }
}
