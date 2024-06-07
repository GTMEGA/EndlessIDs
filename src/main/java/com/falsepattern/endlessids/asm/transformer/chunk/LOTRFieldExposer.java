package com.falsepattern.endlessids.asm.transformer.chunk;

import com.falsepattern.endlessids.Tags;
import com.falsepattern.lib.turboasm.ClassNodeHandle;
import com.falsepattern.lib.turboasm.TurboClassTransformer;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;

public class LOTRFieldExposer implements TurboClassTransformer {
    @Override
    public String owner() {
        return Tags.MODNAME;
    }

    @Override
    public String name() {
        return "LOTRFieldExposer";
    }

    @Override
    public boolean shouldTransformClass(@NotNull String className, @NotNull ClassNodeHandle classNode) {
        return "lotr.common.network.LOTRPacketBiomeVariantsWatch".equals(className);
    }

    @Override
    public boolean transformClass(@NotNull String className, @NotNull ClassNodeHandle classNode) {
        val cn = classNode.getNode();
        if (cn == null)
            return false;
        for (val field: cn.fields) {
            if ("chunkX".equals(field.name) ||
                "chunkZ".equals(field.name) ||
                "variants".equals(field.name)) {
                field.access &= ~Opcodes.ACC_PRIVATE;
                field.access |= Opcodes.ACC_PUBLIC;
            }
        }
        return true;
    }
}
