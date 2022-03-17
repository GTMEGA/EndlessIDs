package com.falsepattern.endlessids.asm;

import java.io.PrintWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.TraceMethodVisitor;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.tree.InsnList;
import java.util.ListIterator;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ClassNode;

public class AsmUtil
{
    public AsmUtil() {
        super();
    }
    
    public static MethodNode findMethod(final ClassNode cn, final String name) {
        return findMethod(cn, name, false);
    }
    
    public static MethodNode findMethod(final ClassNode cn, final String name, final boolean optional) {
        for (final MethodNode ret : cn.methods) {
            if (ret.name.equals(name)) {
                return ret;
            }
        }
        if (optional) {
            return null;
        }
        throw new MethodNotFoundException(name);
    }
    
    public static MethodNode findMethod(final ClassNode cn, final Name name) {
        return findMethod(cn, name, false);
    }
    
    public static MethodNode findMethod(final ClassNode cn, final Name name, final boolean optional) {
        for (final MethodNode ret : cn.methods) {
            if (name.matches(ret)) {
                return ret;
            }
        }
        if (optional) {
            return null;
        }
        throw new MethodNotFoundException(name.deobf);
    }
    
    public static FieldNode findField(final ClassNode cn, final String name) {
        return findField(cn, name, false);
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
    
    public static FieldNode findField(final ClassNode cn, final Name name) {
        return findField(cn, name, false);
    }
    
    public static FieldNode findField(final ClassNode cn, final Name name, final boolean optional) {
        for (final FieldNode ret : cn.fields) {
            if (name.matches(ret)) {
                return ret;
            }
        }
        if (optional) {
            return null;
        }
        throw new FieldNotFoundException(name.deobf);
    }
    
    public static void makePublic(final MethodNode x) {
        x.access = ((x.access & 0xFFFFFFF9) | 0x1);
    }
    
    public static void makePublic(final FieldNode x) {
        x.access = ((x.access & 0xFFFFFFF9) | 0x1);
    }
    
    public static boolean transformInlinedSizeMethod(final ClassNode cn, final MethodNode method, final int oldValue, final int newValue) {
        return transformInlinedSizeMethod(cn, method, oldValue, newValue, false);
    }
    
    public static boolean transformInlinedSizeMethod(final ClassNode cn, final MethodNode method, final int oldValue, final int newValue, final boolean optional) {
        boolean found = false;
        boolean foundOnce = false;
        final int i = 0;
        final ListIterator<AbstractInsnNode> it = (ListIterator<AbstractInsnNode>)method.instructions.iterator();
        while (it.hasNext()) {
            final AbstractInsnNode insn = it.next();
            if (insn.getOpcode() == 3 && oldValue == 0) {
                found = true;
            }
            else if (insn.getOpcode() == 4 && oldValue == 1) {
                found = true;
            }
            else if (insn.getOpcode() == 5 && oldValue == 2) {
                found = true;
            }
            else if (insn.getOpcode() == 6 && oldValue == 3) {
                found = true;
            }
            else if (insn.getOpcode() == 7 && oldValue == 4) {
                found = true;
            }
            else if (insn.getOpcode() == 8 && oldValue == 5) {
                found = true;
            }
            else if (insn.getOpcode() == 18) {
                final LdcInsnNode node = (LdcInsnNode)insn;
                if (node.cst instanceof Integer && (int)node.cst == oldValue) {
                    found = true;
                }
            }
            else if (insn.getOpcode() == 17 || insn.getOpcode() == 16) {
                final IntInsnNode node2 = (IntInsnNode)insn;
                if (node2.operand == oldValue) {
                    found = true;
                }
            }
            if (found) {
                foundOnce = true;
                if (newValue == 0) {
                    it.set(new InsnNode(3));
                }
                else if (newValue == 1) {
                    it.set(new InsnNode(4));
                }
                else if (newValue == 2) {
                    it.set(new InsnNode(5));
                }
                else if (newValue == 3) {
                    it.set(new InsnNode(6));
                }
                else if (newValue == 4) {
                    it.set(new InsnNode(7));
                }
                else if (newValue == 5) {
                    it.set(new InsnNode(8));
                }
                else if (newValue >= -128 && newValue <= 127) {
                    it.set(new IntInsnNode(16, newValue));
                }
                else if (newValue >= -32768 && newValue <= 32767) {
                    it.set(new IntInsnNode(17, newValue));
                }
                else {
                    it.set(new LdcInsnNode((Object)Integer.valueOf(newValue)));
                }
                found = false;
            }
        }
        if (!foundOnce && !optional) {
            throw new AsmTransformException("can't find constant value " + oldValue + " in method " + method.name);
        }
        return foundOnce;
    }
    
    public static void dump(final InsnList list) {
        final Textifier textifier = new Textifier();
        final TraceMethodVisitor visitor = new TraceMethodVisitor((Printer)textifier);
        list.accept((MethodVisitor)visitor);
        final PrintWriter writer = new PrintWriter(System.out);
        textifier.print(writer);
        writer.flush();
    }
}
