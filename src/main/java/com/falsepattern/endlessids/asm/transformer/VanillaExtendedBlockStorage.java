package com.falsepattern.endlessids.asm.transformer;

import org.objectweb.asm.tree.MethodInsnNode;
import com.falsepattern.endlessids.asm.AsmTransformException;
import org.objectweb.asm.tree.InsnNode;
import java.util.ListIterator;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;
import com.falsepattern.endlessids.asm.AsmUtil;
import com.falsepattern.endlessids.asm.Name;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.ClassNode;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;

public class VanillaExtendedBlockStorage implements IClassNodeTransformer
{
    public VanillaExtendedBlockStorage() {
        super();
    }
    
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        cn.fields.add(new FieldNode(1, "block16BArray", "[S", (String)null, (Object)null));
        AsmUtil.makePublic(AsmUtil.findField(cn, Name.ebs_blockRefCount));
        AsmUtil.makePublic(AsmUtil.findField(cn, Name.ebs_tickRefCount));
        MethodNode method = AsmUtil.findMethod(cn, "<init>");
        this.transformConstructor(cn, method, obfuscated);
        method = AsmUtil.findMethod(cn, Name.ebs_getBlock);
        this.transformGetBlock(cn, method, obfuscated);
        method = AsmUtil.findMethod(cn, Name.ebs_setBlock);
        this.transformSetBlock(cn, method, obfuscated);
        method = AsmUtil.findMethod(cn, Name.ebs_getBlockMSBArray);
        this.transformGetBlockMSBArray(cn, method);
        method = AsmUtil.findMethod(cn, Name.ebs_removeInvalidBlocks);
        this.transformRemoveInvalidBlocks(cn, method);
    }
    
    private void transformConstructor(final ClassNode cn, final MethodNode method, final boolean obfuscated) {
        final InsnList code = method.instructions;
        final ListIterator<AbstractInsnNode> iterator = (ListIterator<AbstractInsnNode>)code.iterator();
        if (iterator.hasNext()) {
            AbstractInsnNode insn = (AbstractInsnNode)iterator.next();
            insn = insn.getNext().getNext();
            final InsnList toInsert = new InsnList();
            toInsert.add((AbstractInsnNode)new VarInsnNode(25, 0));
            toInsert.add((AbstractInsnNode)Name.hooks_create16BArray.staticInvocation(obfuscated));
            toInsert.add((AbstractInsnNode)new FieldInsnNode(181, cn.name, "block16BArray", "[S"));
            method.instructions.insert(insn, toInsert);
        }
        method.maxStack = Math.max(method.maxStack, 2);
    }
    
    private void transformGetBlock(final ClassNode cn, final MethodNode method, final boolean obfuscated) {
        final InsnList code = method.instructions;
        code.clear();
        code.add((AbstractInsnNode)new VarInsnNode(25, 0));
        code.add((AbstractInsnNode)new VarInsnNode(21, 1));
        code.add((AbstractInsnNode)new VarInsnNode(21, 2));
        code.add((AbstractInsnNode)new VarInsnNode(21, 3));
        code.add((AbstractInsnNode)Name.hooks_getBlockById.staticInvocation(obfuscated));
        code.add((AbstractInsnNode)new InsnNode(176));
        method.localVariables = null;
        method.maxStack = 4;
    }
    
    private void transformSetBlock(final ClassNode cn, final MethodNode method, final boolean obfuscated) {
        final InsnList code = method.instructions;
        int part = 0;
        final ListIterator<AbstractInsnNode> iterator = (ListIterator<AbstractInsnNode>)code.iterator();
        while (iterator.hasNext()) {
            final AbstractInsnNode insn = (AbstractInsnNode)iterator.next();
            if (part == 0) {
                iterator.remove();
                if (insn.getOpcode() != 184) {
                    continue;
                }
                ++part;
                iterator.add((AbstractInsnNode)new VarInsnNode(25, 0));
                iterator.add((AbstractInsnNode)new VarInsnNode(21, 1));
                iterator.add((AbstractInsnNode)new VarInsnNode(21, 2));
                iterator.add((AbstractInsnNode)new VarInsnNode(21, 3));
                iterator.add((AbstractInsnNode)Name.ebs_getBlock.virtualInvocation(obfuscated));
            }
            else if (part == 1) {
                if (insn.getOpcode() != 184) {
                    continue;
                }
                iterator.set((AbstractInsnNode)new VarInsnNode(25, 6));
                iterator.add((AbstractInsnNode)Name.hooks_getIdFromBlockWithCheck.staticInvocation(obfuscated));
                ++part;
            }
            else {
                iterator.remove();
            }
        }
        if (part != 2) {
            throw new AsmTransformException("no match for part " + part);
        }
        code.add((AbstractInsnNode)new VarInsnNode(54, 5));
        code.add((AbstractInsnNode)new VarInsnNode(25, 0));
        code.add((AbstractInsnNode)new VarInsnNode(21, 1));
        code.add((AbstractInsnNode)new VarInsnNode(21, 2));
        code.add((AbstractInsnNode)new VarInsnNode(21, 3));
        code.add((AbstractInsnNode)new VarInsnNode(21, 5));
        code.add((AbstractInsnNode)Name.hooks_setBlockId.staticInvocation(obfuscated));
        code.add((AbstractInsnNode)new InsnNode(177));
        method.localVariables = null;
        --method.maxLocals;
        method.maxStack = Math.max(method.maxStack, 5);
    }
    
    private void transformGetBlockMSBArray(final ClassNode cn, final MethodNode method) {
        final InsnList code = method.instructions;
        code.clear();
        code.add((AbstractInsnNode)new InsnNode(1));
        code.add((AbstractInsnNode)new InsnNode(176));
        method.localVariables = null;
        method.maxStack = 1;
    }
    
    private void transformRemoveInvalidBlocks(final ClassNode cn, final MethodNode method) {
        final InsnList code = method.instructions;
        code.clear();
        code.add((AbstractInsnNode)new VarInsnNode(25, 0));
        code.add((AbstractInsnNode)new MethodInsnNode(184, "com/falsepattern/endlessids/Hooks", "removeInvalidBlocksHook", "(L" + cn.name + ";)V", false));
        code.add((AbstractInsnNode)new InsnNode(177));
        method.localVariables = null;
        method.maxStack = 1;
    }
}
