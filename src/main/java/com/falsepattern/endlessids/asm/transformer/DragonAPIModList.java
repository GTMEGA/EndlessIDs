package com.falsepattern.endlessids.asm.transformer;

import com.falsepattern.endlessids.asm.ASMHooks;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;
import com.falsepattern.endlessids.mixin.plugin.MixinPlugin;
import lombok.val;
import lombok.var;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import java.util.Arrays;
import java.util.List;

public class DragonAPIModList implements IClassNodeTransformer {
    private static final List<String> fields = Arrays.asList("condition", "itemClasses", "blockClasses");
    private void crash() {
        throw new IllegalStateException("Tweaking failed!");
    }
    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        int state = 0;
        for (val field: cn.fields) {
            if (field.name.equals("__MARKER__FALSEPATTERN__MIXINPATCH__")) {
                return;
            }
        }
        for (val field: cn.fields) {
            if (field.name.equals("condition") && field.desc.equals("Z")) {
                field.access &= ~Opcodes.ACC_FINAL;
                state++;
            }
            if (fields.contains(field.name)) {
                field.access &= ~Opcodes.ACC_PRIVATE;
                field.access &= ~Opcodes.ACC_PROTECTED;
                field.access |= Opcodes.ACC_PUBLIC;
                state++;
            }
        }
        if (state != 4) {
            crash();
        }
        cn.fields.add(new FieldNode(Opcodes.ACC_PUBLIC, "successful", "Z", null, 0));
        cn.fields.add(new FieldNode(Opcodes.ACC_PRIVATE, "__MARKER__FALSEPATTERN__MIXINPATCH__", "Z", null, 0));

        int state2 = 0;
        for (val method : cn.methods) {
            if ((state2 & 1) == 0 &&
                method.name.equals("<init>") &&
                method.desc.equals("(Ljava/lang/String;ILjava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)V")) {
                tweakInit(method);
                state2 |= 1;
            }
            if ((state2 & 2) == 0 &&
                method.name.equals("isLoaded") &&
                method.desc.equals("()Z")) {
                tweakIsLoaded(method);
                state2 |= 2;
            }
        }
        if (state2 != 3) {
            crash();
        }
    }

    private static void tweakIsLoaded(MethodNode method) {
        val iterator = method.instructions.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.set(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(ASMHooks.class), "DragonAPIModListTryLoad", "(LReika/DragonAPI/ModList;)Z", false));
    }

    private static void tweakInit(MethodNode method) {
        val iterator = method.instructions.iterator();
        while (iterator.hasNext()) {
            var insn = iterator.next();
            if (insn instanceof LineNumberNode && ((LineNumberNode)insn).line == 154) {
                //SRC:
                //  L3
                //    LINENUMBER 154 L3
                //    ALOAD 0
                //    ALOAD 0
                //    GETFIELD Reika/DragonAPI/ModList.modLabel : Ljava/lang/String;
                //    INVOKESTATIC cpw/mods/fml/common/Loader.isModLoaded (Ljava/lang/String;)Z
                //    PUTFIELD Reika/DragonAPI/ModList.condition : Z
                //TGT:
                //  L3
                //    LINENUMBER 154 L3
                //    ALOAD 0
                //    ALOAD 5
                //    PUTFIELD Reika/DragonAPI/ModList.itemClasses : [Ljava/lang/String;
                //    ALOAD 0
                //    ALOAD 4
                //    PUTFIELD Reika/DragonAPI/ModList.blockClasses : [Ljava/lang/String;
                //    ALOAD 0
                //    INVOKESTATIC ASMHooks.isModLoaded (LReika/DragonAPI/ModList;)V
                //    FRAME FULL [Reika/DragonAPI/ModList java/lang/String I java/lang/String [Ljava/lang/String; [Ljava/lang/String;] []
                //    RETURN
                iterator.add(new VarInsnNode(Opcodes.ALOAD, 0));
                iterator.add(new VarInsnNode(Opcodes.ALOAD, 5));
                iterator.add(new FieldInsnNode(Opcodes.PUTFIELD,
                                               "Reika/DragonAPI/ModList",
                                               "itemClasses",
                                               "[Ljava/lang/String;"));
                iterator.add(new VarInsnNode(Opcodes.ALOAD, 0));
                iterator.add(new VarInsnNode(Opcodes.ALOAD, 4));
                iterator.add(new FieldInsnNode(Opcodes.PUTFIELD,
                                               "Reika/DragonAPI/ModList",
                                               "blockClasses",
                                               "[Ljava/lang/String;"));
                iterator.next();
                iterator.remove();
                iterator.next();
                iterator.next();
                iterator.remove();
                var methodInsn = (MethodInsnNode) iterator.next();
                methodInsn.owner = Type.getInternalName(ASMHooks.class);
                methodInsn.name = "DragonAPIModListConstructorTweak";
                methodInsn.desc = "(LReika/DragonAPI/ModList;)V";
                iterator.add(new FrameNode(Opcodes.F_FULL, 6, new Object[] {"Reika/DragonAPI/ModList", "java/lang/String", Opcodes.INTEGER, "java/lang/String", "[Ljava/lang/String;", "[Ljava/lang/String;"}, 0, new Object[] {}));
                iterator.add(new InsnNode(Opcodes.RETURN));
                iterator.add(new FrameNode(Opcodes.F_FULL, 6, new Object[] {"Reika/DragonAPI/ModList", "java/lang/String", Opcodes.INTEGER, "java/lang/String", "[Ljava/lang/String;", "[Ljava/lang/String;"}, 0, new Object[] {}));
                iterator.next();
                iterator.remove();
                break;
            }
        }
    }
}
