package com.falsepattern.endlessids.asm.transformer;

import org.objectweb.asm.tree.MethodInsnNode;
import java.util.ListIterator;
import org.objectweb.asm.tree.InsnList;
import com.falsepattern.endlessids.asm.AsmTransformException;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;
import com.falsepattern.endlessids.asm.AsmUtil;
import com.falsepattern.endlessids.asm.Name;
import org.objectweb.asm.tree.ClassNode;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;

public class VanillaAnvilChunkLoader implements IClassNodeTransformer
{
    public VanillaAnvilChunkLoader() {
        super();
    }
    
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        MethodNode method = AsmUtil.findMethod(cn, Name.acl_writeChunkToNBT);
        this.transformWriteChunkToNBT(cn, method, obfuscated);
        method = AsmUtil.findMethod(cn, Name.acl_readChunkFromNBT);
        this.transformReadChunkFromNBT(cn, method, obfuscated);
    }
    
    private void transformWriteChunkToNBT(final ClassNode cn, final MethodNode method, final boolean obfuscated) {
        final InsnList code = method.instructions;
        final ListIterator<AbstractInsnNode> iterator = (ListIterator<AbstractInsnNode>)code.iterator();
        while (iterator.hasNext()) {
            final AbstractInsnNode insn = (AbstractInsnNode)iterator.next();
            if (insn.getOpcode() == 18 && ((LdcInsnNode)insn).cst.equals("Blocks")) {
                iterator.remove();
                iterator.next();
                iterator.next();
                iterator.remove();
                iterator.next();
                iterator.remove();
                iterator.add((AbstractInsnNode)Name.hooks_writeChunkToNbt.staticInvocation(obfuscated));
                return;
            }
        }
        throw new AsmTransformException("can't find Blocks LDC");
    }
    
    private void transformReadChunkFromNBT(final ClassNode cn, final MethodNode method, final boolean obfuscated) {
        final InsnList code = method.instructions;
        int part = 0;
        final ListIterator<AbstractInsnNode> iterator = (ListIterator<AbstractInsnNode>)code.iterator();
        while (iterator.hasNext()) {
            final AbstractInsnNode insn = (AbstractInsnNode)iterator.next();
            if (part == 0) {
                if (insn.getOpcode() != 18 || !((LdcInsnNode)insn).cst.equals("Blocks")) {
                    continue;
                }
                iterator.set((AbstractInsnNode)Name.hooks_readChunkFromNbt.staticInvocation(obfuscated));
                ++part;
            }
            else if (part == 1) {
                iterator.remove();
                if (insn.getOpcode() != 182) {
                    continue;
                }
                final MethodInsnNode node = (MethodInsnNode)insn;
                if (!Name.ebs_setBlockMSBArray.matches(node, obfuscated)) {
                    continue;
                }
                ++part;
            }
            else {
                if (insn.getType() == 14) {
                    iterator.remove();
                    ++part;
                    break;
                }
                continue;
            }
        }
        if (part != 3) {
            throw new AsmTransformException("no match for part " + part);
        }
    }
}
