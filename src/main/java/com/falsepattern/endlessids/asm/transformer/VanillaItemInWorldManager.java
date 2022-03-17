package com.falsepattern.endlessids.asm.transformer;

import org.objectweb.asm.tree.MethodNode;
import com.falsepattern.endlessids.asm.AsmUtil;
import com.falsepattern.endlessids.asm.Name;
import org.objectweb.asm.tree.ClassNode;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;

public class VanillaItemInWorldManager implements IClassNodeTransformer
{
    public VanillaItemInWorldManager() {
        super();
    }
    
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        final MethodNode method = AsmUtil.findMethod(cn, Name.itemInWorldManager_tryHarvestBlock);
        AsmUtil.transformInlinedSizeMethod(cn, method, 12, 16);
    }
}
