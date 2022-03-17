package com.falsepattern.endlessids.asm.transformer;

import lombok.val;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.LdcInsnNode;
import com.falsepattern.endlessids.asm.AsmUtil;
import org.objectweb.asm.tree.ClassNode;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;

public class WorldEditBaseBlock implements IClassNodeTransformer
{
    public WorldEditBaseBlock() {
        super();
    }
    
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        final MethodNode method = AsmUtil.findMethod(cn, "internalSetId", true);
        if (method == null) {
            return;
        }
        AsmUtil.transformInlinedSizeMethod(cn, method, 4095, 32767);
        final InsnList code = method.instructions;
        val iter = code.iterator();
        while (iter.hasNext()) {
            val insn = iter.next();
            if (insn.getType() == 9 && ((LdcInsnNode)insn).cst instanceof String) {
                final String string = (String)((LdcInsnNode)insn).cst;
                if (string.contains("4095")) {
                    ((LdcInsnNode)insn).cst = string.replace("4095", Integer.toString(32767));
                    break;
                }
            }
        }
    }
}
