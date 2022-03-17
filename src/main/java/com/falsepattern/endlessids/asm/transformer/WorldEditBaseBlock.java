package com.falsepattern.endlessids.asm.transformer;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
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
        AsmUtil.transformInlinedSizeMethod(cn, method, VanillaConstants.maxBlockID, ExtendedConstants.maxBlockID);
        final InsnList code = method.instructions;
        val iter = code.iterator();
        while (iter.hasNext()) {
            val insn = iter.next();
            if (insn.getType() == 9 && ((LdcInsnNode)insn).cst instanceof String) {
                final String string = (String)((LdcInsnNode)insn).cst;
                if (string.contains(Integer.toString(VanillaConstants.maxBlockID))) {
                    ((LdcInsnNode)insn).cst = string.replace(Integer.toString(VanillaConstants.maxBlockID), Integer.toString(ExtendedConstants.maxBlockID));
                    break;
                }
            }
        }
    }
}
