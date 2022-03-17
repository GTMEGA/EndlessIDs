package com.falsepattern.endlessids.asm.transformer;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.objectweb.asm.tree.MethodNode;
import com.falsepattern.endlessids.asm.AsmUtil;
import org.objectweb.asm.tree.ClassNode;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;

public class CofhBlockHelper implements IClassNodeTransformer
{
    public CofhBlockHelper() {
        super();
    }
    
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        final MethodNode method = AsmUtil.findMethod(cn, "<clinit>");
        if (!AsmUtil.transformInlinedSizeMethod(cn, method, VanillaConstants.blockIDCount, ExtendedConstants.blockIDCount, true)) {
            AsmUtil.transformInlinedSizeMethod(cn, method, VanillaConstants.blockIDCount / 4, ExtendedConstants.blockIDCount);
        }
    }
}
