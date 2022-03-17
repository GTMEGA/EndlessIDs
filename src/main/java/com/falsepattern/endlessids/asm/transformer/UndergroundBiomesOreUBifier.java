package com.falsepattern.endlessids.asm.transformer;

import org.objectweb.asm.tree.MethodNode;
import com.falsepattern.endlessids.asm.AsmUtil;
import org.objectweb.asm.tree.ClassNode;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;

public class UndergroundBiomesOreUBifier implements IClassNodeTransformer
{
    public UndergroundBiomesOreUBifier() {
        super();
    }
    
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        final MethodNode method = AsmUtil.findMethod(cn, "renewBlockReplacers");
        AsmUtil.transformInlinedSizeMethod(cn, method, 4096, 32768, false);
    }
}
