package com.falsepattern.endlessids.asm.transformer;

import java.util.ListIterator;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import com.falsepattern.endlessids.asm.AsmTransformException;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import com.falsepattern.endlessids.asm.AsmUtil;
import com.falsepattern.endlessids.asm.Name;
import org.objectweb.asm.tree.ClassNode;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;

public class VanillaS22PacketMultiBlockChange implements IClassNodeTransformer
{
    public VanillaS22PacketMultiBlockChange() {
        super();
    }
    
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        final MethodNode method = AsmUtil.findMethod(cn, Name.s22_init_server);
        final InsnList code = method.instructions;
        int part = 0;
        final ListIterator<AbstractInsnNode> iterator = (ListIterator<AbstractInsnNode>)code.iterator();
        while (iterator.hasNext()) {
            final AbstractInsnNode insn = (AbstractInsnNode)iterator.next();
            if (part == 0) {
                if (insn.getOpcode() != 7) {
                    continue;
                }
                iterator.set((AbstractInsnNode)new InsnNode(8));
                ++part;
            }
            else if (part == 1) {
                if (insn.getOpcode() != 184) {
                    continue;
                }
                final MethodInsnNode node = (MethodInsnNode)insn;
                if (!Name.block_getIdFromBlock.matches(node, obfuscated)) {
                    continue;
                }
                iterator.add((AbstractInsnNode)new MethodInsnNode(182, "java/io/DataOutputStream", "writeShort", "(I)V", false));
                ++part;
            }
            else if (part == 2) {
                if (insn.getOpcode() == 25) {
                    ++part;
                }
                else {
                    iterator.remove();
                }
            }
            else if (part == 3) {
                if (insn.getOpcode() != 182) {
                    continue;
                }
                iterator.add((AbstractInsnNode)new VarInsnNode(25, 6));
                iterator.add((AbstractInsnNode)new InsnNode(95));
                iterator.add((AbstractInsnNode)new MethodInsnNode(182, "java/io/DataOutputStream", "writeByte", "(I)V", false));
                ++part;
            }
            else {
                iterator.remove();
                if (insn.getOpcode() == 182) {
                    return;
                }
                continue;
            }
        }
        throw new AsmTransformException("no match for part " + part);
    }
}
