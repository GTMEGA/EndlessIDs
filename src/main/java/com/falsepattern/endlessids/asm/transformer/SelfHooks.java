package com.falsepattern.endlessids.asm.transformer;

import com.falsepattern.endlessids.asm.Name;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.MethodNode;
import com.falsepattern.endlessids.asm.AsmUtil;
import org.objectweb.asm.tree.ClassNode;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;

public class SelfHooks implements IClassNodeTransformer
{
    public SelfHooks() {
        super();
    }
    
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        MethodNode method = AsmUtil.findMethod(cn, "get");
        this.transformGet(cn, method);
        method = AsmUtil.findMethod(cn, "setBlockRefCount");
        this.transformSetBlockRefCount(cn, method, obfuscated);
        method = AsmUtil.findMethod(cn, "setTickRefCount");
        this.transformSetTickRefCount(cn, method, obfuscated);
    }
    
    private void transformGet(final ClassNode cn, final MethodNode method) {
        final InsnList code = method.instructions;
        code.clear();
        code.add((AbstractInsnNode)new VarInsnNode(25, 0));
        code.add((AbstractInsnNode)new FieldInsnNode(180, Type.getArgumentTypes(method.desc)[0].getInternalName(), "block16BArray", "[S"));
        code.add((AbstractInsnNode)new InsnNode(176));
        method.localVariables = null;
        method.maxStack = 1;
    }
    
    private void transformSetBlockRefCount(final ClassNode cn, final MethodNode method, final boolean isObf) {
        final InsnList code = method.instructions;
        code.clear();
        code.add((AbstractInsnNode)new VarInsnNode(25, 0));
        code.add((AbstractInsnNode)new VarInsnNode(21, 1));
        code.add((AbstractInsnNode)Name.ebs_blockRefCount.virtualSet(isObf));
        code.add((AbstractInsnNode)new InsnNode(177));
        method.localVariables = null;
        method.maxStack = 2;
    }
    
    private void transformSetTickRefCount(final ClassNode cn, final MethodNode method, final boolean isObf) {
        final InsnList code = method.instructions;
        code.clear();
        code.add((AbstractInsnNode)new VarInsnNode(25, 0));
        code.add((AbstractInsnNode)new VarInsnNode(21, 1));
        code.add((AbstractInsnNode)Name.ebs_tickRefCount.virtualSet(isObf));
        code.add((AbstractInsnNode)new InsnNode(177));
        method.localVariables = null;
        method.maxStack = 2;
    }
}
