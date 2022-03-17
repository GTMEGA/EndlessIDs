package com.falsepattern.endlessids.asm.transformer;

import org.objectweb.asm.tree.FieldNode;
import com.falsepattern.endlessids.asm.AsmTransformException;
import org.objectweb.asm.tree.MethodNode;
import com.falsepattern.endlessids.asm.AsmUtil;
import org.objectweb.asm.tree.ClassNode;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;

public class FmlRegistry implements IClassNodeTransformer
{
    public FmlRegistry() {
        super();
    }
    
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        final FieldNode field = AsmUtil.findField(cn, "MAX_BLOCK_ID", true);
        if (field != null) {
            field.value = Integer.valueOf(32767);
        }
        boolean found = false;
        for (final MethodNode method : cn.methods) {
            if (AsmUtil.transformInlinedSizeMethod(cn, method, 4095, 32767, true)) {
                found = true;
            }
        }
        if (!found) {
            throw new AsmTransformException("can't find 4095 constant in any method");
        }
    }
}
