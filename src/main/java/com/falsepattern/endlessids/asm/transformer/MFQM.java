package com.falsepattern.endlessids.asm.transformer;

import org.objectweb.asm.tree.MethodNode;
import com.falsepattern.endlessids.asm.AsmUtil;
import com.falsepattern.endlessids.asm.Name;
import com.falsepattern.endlessids.IEConfig;
import org.objectweb.asm.tree.ClassNode;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;

public class MFQM implements IClassNodeTransformer
{
    public MFQM() {
        super();
    }
    
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        if (IEConfig.extendDataWatcher) {
            final MethodNode method = AsmUtil.findMethod(cn, Name.MFQM_preInit);
            AsmUtil.transformInlinedSizeMethod(cn, method, 31, 127);
        }
    }
}
