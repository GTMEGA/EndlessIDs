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

package com.falsepattern.endlessids.asm.transformer.chunk;

import com.falsepattern.endlessids.Tags;
import com.falsepattern.lib.turboasm.ClassNodeHandle;
import com.falsepattern.lib.turboasm.TurboClassTransformer;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;

public class LOTRFieldExposer implements TurboClassTransformer {
    @Override
    public String owner() {
        return Tags.MODNAME;
    }

    @Override
    public String name() {
        return "LOTRFieldExposer";
    }

    @Override
    public boolean shouldTransformClass(@NotNull String className, @NotNull ClassNodeHandle classNode) {
        return "lotr.common.network.LOTRPacketBiomeVariantsWatch".equals(className);
    }

    @Override
    public boolean transformClass(@NotNull String className, @NotNull ClassNodeHandle classNode) {
        val cn = classNode.getNode();
        if (cn == null)
            return false;
        for (val field: cn.fields) {
            if ("chunkX".equals(field.name) ||
                "chunkZ".equals(field.name) ||
                "variants".equals(field.name)) {
                field.access &= ~Opcodes.ACC_PRIVATE;
                field.access |= Opcodes.ACC_PUBLIC;
            }
        }
        return true;
    }
}
