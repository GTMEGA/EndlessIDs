package com.falsepattern.endlessids.asm.transformer;

import java.util.ListIterator;
import org.objectweb.asm.tree.MethodNode;
import com.falsepattern.endlessids.asm.AsmTransformException;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import com.falsepattern.endlessids.asm.AsmUtil;
import com.falsepattern.endlessids.asm.Name;
import org.objectweb.asm.tree.ClassNode;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;

public class VanillaChunk implements IClassNodeTransformer
{
    public VanillaChunk() {
        super();
    }
    
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        final MethodNode method = AsmUtil.findMethod(cn, Name.chunk_fillChunk, true);
        if (method == null) {
            return;
        }
        method.localVariables = null;
        int part = 0;
        final ListIterator<AbstractInsnNode> it = (ListIterator<AbstractInsnNode>)method.instructions.iterator();
        while (it.hasNext()) {
            AbstractInsnNode insn = (AbstractInsnNode)it.next();
            if (part == 0) {
                if (insn.getOpcode() != 182 || !Name.ebs_getBlockLSBArray.matches((MethodInsnNode)insn, obfuscated)) {
                    continue;
                }
                it.set((AbstractInsnNode)new VarInsnNode(25, 1));
                it.add((AbstractInsnNode)new VarInsnNode(21, 6));
                it.add((AbstractInsnNode)Name.hooks_setBlockData.staticInvocation(obfuscated));
                ++part;
            }
            else if (part == 1) {
                if (insn.getOpcode() == 96) {
                    it.set((AbstractInsnNode)new VarInsnNode(21, 6));
                    it.add((AbstractInsnNode)new LdcInsnNode((Object)Integer.valueOf(8192)));
                    it.add((AbstractInsnNode)new InsnNode(96));
                    ++part;
                }
                else {
                    it.remove();
                }
            }
            else {
                if (part == 2 && insn.getOpcode() == 182 && Name.ebs_getBlockMSBArray.matches((MethodInsnNode)insn, obfuscated)) {
                    while (((AbstractInsnNode)it.previous()).getOpcode() != 3) {}
                    while (((AbstractInsnNode)it.previous()).getType() != 8) {}
                    do {
                        insn = it.next();
                    } while (insn.getOpcode() != 161 && insn.getOpcode() != 162);
                    it.set((AbstractInsnNode)new JumpInsnNode((insn.getOpcode() == 161) ? 163 : 161, ((JumpInsnNode)insn).label));
                    return;
                }
                continue;
            }
        }
        throw new AsmTransformException("no match for part " + part);
    }
}
