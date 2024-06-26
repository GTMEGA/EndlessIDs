package com.falsepattern.endlessids.asm.transformer;

import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.asm.EndlessIDsCore;
import com.falsepattern.endlessids.asm.EndlessIDsTransformer;
import com.falsepattern.lib.turboasm.ClassNodeHandle;
import com.falsepattern.lib.turboasm.TurboClassTransformer;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnNode;

public class SpaceCoreModInfoGenerator implements TurboClassTransformer {
    @Override
    public String owner() {
        return Tags.MODNAME;
    }

    @Override
    public String name() {
        return "SpaceCoreModInfoGenerator";
    }

    @Override
    public boolean shouldTransformClass(@NotNull String className, @NotNull ClassNodeHandle classNode) {
        return EndlessIDsCore.deobfuscated && "com.spacechase0.minecraft.spacecore.mcp.ModInfoGenerator".equals(className);
    }

    @Override
    public boolean transformClass(@NotNull String className, @NotNull ClassNodeHandle classNode) {
        val cn = classNode.getNode();
        if (cn == null)
            return false;
        for (val method : cn.methods) {
            if (method.name.equals("generate")) {
                val iterator = method.instructions.iterator();
                iterator.add(new InsnNode(Opcodes.RETURN));
                return true;
            }
        }
        return false;
    }
}
