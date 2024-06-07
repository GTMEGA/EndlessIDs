package com.falsepattern.endlessids.asm.transformer;

import com.falsepattern.endlessids.asm.IClassNodeTransformer;
import lombok.val;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

public class LOTRFieldExposer implements IClassNodeTransformer {
    @Override
    public void transform(ClassNode classNode, boolean obfuscated) {
        for (val field: classNode.fields) {
            if ("chunkX".equals(field.name) ||
                "chunkZ".equals(field.name) ||
                "variants".equals(field.name)) {
                field.access &= ~Opcodes.ACC_PRIVATE;
                field.access |= Opcodes.ACC_PUBLIC;
            }
        }
    }
}
