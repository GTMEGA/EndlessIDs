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

package com.falsepattern.endlessids.asm;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ListIterator;

public class AsmUtil {
    public AsmUtil() {
        super();
    }

    public static FieldNode findField(final ClassNode cn, final String name, final boolean optional) {
        for (final FieldNode ret : cn.fields) {
            if (name.equals(ret.name)) {
                return ret;
            }
        }
        if (optional) {
            return null;
        }
        throw new FieldNotFoundException(name);
    }

    public static boolean transformInlinedSizeMethod(final ClassNode cn, final MethodNode method, final int oldValue, final int newValue, final boolean optional) {
        boolean found = false;
        boolean foundOnce = false;
        final int i = 0;
        final ListIterator<AbstractInsnNode> it = method.instructions.iterator();
        while (it.hasNext()) {
            final AbstractInsnNode insn = it.next();
            if (insn.getOpcode() == 3 && oldValue == 0) {
                found = true;
            } else if (insn.getOpcode() == 4 && oldValue == 1) {
                found = true;
            } else if (insn.getOpcode() == 5 && oldValue == 2) {
                found = true;
            } else if (insn.getOpcode() == 6 && oldValue == 3) {
                found = true;
            } else if (insn.getOpcode() == 7 && oldValue == 4) {
                found = true;
            } else if (insn.getOpcode() == 8 && oldValue == 5) {
                found = true;
            } else if (insn.getOpcode() == 18) {
                final LdcInsnNode node = (LdcInsnNode) insn;
                if (node.cst instanceof Integer && (int) node.cst == oldValue) {
                    found = true;
                }
            } else if (insn.getOpcode() == 17 || insn.getOpcode() == 16) {
                final IntInsnNode node2 = (IntInsnNode) insn;
                if (node2.operand == oldValue) {
                    found = true;
                }
            }
            if (found) {
                foundOnce = true;
                if (newValue == 0) {
                    it.set(new InsnNode(3));
                } else if (newValue == 1) {
                    it.set(new InsnNode(4));
                } else if (newValue == 2) {
                    it.set(new InsnNode(5));
                } else if (newValue == 3) {
                    it.set(new InsnNode(6));
                } else if (newValue == 4) {
                    it.set(new InsnNode(7));
                } else if (newValue == 5) {
                    it.set(new InsnNode(8));
                } else if (newValue >= -128 && newValue <= 127) {
                    it.set(new IntInsnNode(16, newValue));
                } else if (newValue >= -32768 && newValue <= 32767) {
                    it.set(new IntInsnNode(17, newValue));
                } else {
                    it.set(new LdcInsnNode(newValue));
                }
                found = false;
            }
        }
        if (!foundOnce && !optional) {
            throw new AsmTransformException("can't find constant value " + oldValue + " in method " + method.name);
        }
        return foundOnce;
    }

}
