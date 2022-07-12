package com.falsepattern.endlessids.asm.transformer;

import com.falsepattern.endlessids.asm.IClassNodeTransformer;
import lombok.val;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;

public class SpaceCoreModInfoGenerator implements IClassNodeTransformer {
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        for (val method: cn.methods) {
            if (method.name.equals("generate")) {
                val iterator = method.instructions.iterator();
                iterator.add(new InsnNode(Opcodes.RETURN));
            }
        }
    }
}
