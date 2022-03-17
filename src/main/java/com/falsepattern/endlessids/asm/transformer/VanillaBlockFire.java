package com.falsepattern.endlessids.asm.transformer;

import com.falsepattern.endlessids.asm.AsmTransformException;
import com.falsepattern.endlessids.asm.AsmUtil;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ClassNode;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;

public class VanillaBlockFire implements IClassNodeTransformer
{
    public VanillaBlockFire() {
        super();
    }
    
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        boolean found = false;
        for (final MethodNode method : cn.methods) {
            if (AsmUtil.transformInlinedSizeMethod(cn, method, 4096, 32768, true)) {
                found = true;
            }
        }
        if (!found) {
            throw new AsmTransformException("can't find 4096 constant in any method");
        }
    }
}
