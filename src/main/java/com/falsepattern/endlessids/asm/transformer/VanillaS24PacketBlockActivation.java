package com.falsepattern.endlessids.asm.transformer;

import org.objectweb.asm.tree.MethodNode;
import com.falsepattern.endlessids.asm.AsmUtil;
import com.falsepattern.endlessids.asm.Name;
import org.objectweb.asm.tree.ClassNode;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;

public class VanillaS24PacketBlockActivation implements IClassNodeTransformer
{
    public VanillaS24PacketBlockActivation() {
        super();
    }
    
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        MethodNode method = AsmUtil.findMethod(cn, Name.packet_readPacketData);
        AsmUtil.transformInlinedSizeMethod(cn, method, 4095, 65535);
        method = AsmUtil.findMethod(cn, Name.packet_writePacketData);
        AsmUtil.transformInlinedSizeMethod(cn, method, 4095, 65535);
    }
}
