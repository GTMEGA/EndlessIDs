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

package com.falsepattern.endlessids.asm.transformer;

import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.asm.EndlessIDsCore;
import com.falsepattern.lib.turboasm.ClassNodeHandle;
import com.falsepattern.lib.turboasm.TurboClassTransformer;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;

import net.minecraft.launchwrapper.Launch;

import java.io.IOException;

public class DevFixer implements TurboClassTransformer {
    private static final ClassNode blocks;

    static {
        if (!EndlessIDsCore.deobfuscated) {
            blocks = null;
        } else {
            try {
                byte[] blox = Launch.classLoader.getClassBytes("net/minecraft/init/Blocks");
                blocks = new ClassNode(Opcodes.ASM5);
                final ClassReader reader = new ClassReader(blox);
                reader.accept(blocks, 0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static boolean biomeTweakerCompat(final ClassNode cn) {
        boolean modified = false;
        for (val field : cn.fields) {
            if (field.name.startsWith("spawnable")) {
                field.access &= ~Opcodes.ACC_PROTECTED;
                field.access |= Opcodes.ACC_PUBLIC;
                modified = true;
            }
        }
        return modified;
    }

    private static boolean remapBlocks(final ClassNode cn) {
        boolean modified = false;
        for (val method : cn.methods) {
            val instructions = method.instructions;
            int insnCount = instructions.size();
            for (int i = 0; i < insnCount; i++) {
                val insn = instructions.get(i);
                if (insn instanceof FieldInsnNode) {
                    val field = (FieldInsnNode) insn;
                    if (field.getOpcode() == Opcodes.GETSTATIC && field.owner.equals("net/minecraft/init/Blocks")) {
                        for (val blockField : blocks.fields) {
                            if (blockField.name.equals(field.name) && !blockField.desc.equals(field.desc)) {
                                field.desc = blockField.desc;
                                modified = true;
                            }
                        }
                    }
                }
            }
        }
        return modified;
    }

    @Override
    public String owner() {
        return Tags.MODNAME;
    }

    @Override
    public String name() {
        return "DevFixer";
    }

    @Override
    public boolean shouldTransformClass(@NotNull String className, @NotNull ClassNodeHandle classNode) {
        return EndlessIDsCore.deobfuscated && !"net.minecraft.world.chunk.storage.AnvilChunkLoader".equals(className);
    }

    @Override
    public boolean transformClass(@NotNull String className, @NotNull ClassNodeHandle classNode) {
        val cn = classNode.getNode();
        if (cn == null)
            return false;
        boolean modified = false;
        modified |= remapBlocks(cn);
        if (className.equals("net.minecraft.world.biome.BiomeGenBase")) {
            modified |= biomeTweakerCompat(cn);
        }
        return modified;
    }
}
