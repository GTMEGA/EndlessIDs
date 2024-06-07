package com.falsepattern.endlessids.asm.transformer;

import com.falsepattern.endlessids.asm.AsmTransformException;
import com.falsepattern.endlessids.asm.IClassNodeTransformer;
import com.falsepattern.endlessids.asm.IETransformer;
import lombok.val;
import lombok.var;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class GameDataAccelerator implements IClassNodeTransformer {
    private static final int MASK_REGISTER_BLOCK = 0b1;
    private static final int MASK_REGISTER_ITEM = 0b10;
    @Override
    public void transform(ClassNode classNode, boolean obfuscated) {
        var cacheField = new FieldNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_SYNTHETIC, "endlessIDsItemBlockCache", "Lcom/falsepattern/endlessids/util/WeakIdentityHashMap;", null, null);
        classNode.fields.add(cacheField);
        int tweaksLeft = MASK_REGISTER_BLOCK | MASK_REGISTER_ITEM;
        for (val method: classNode.methods) {
            if ((tweaksLeft & MASK_REGISTER_BLOCK) != 0 &&
                method.name.equals("registerBlock") &&
                method.desc.equals("(Lnet/minecraft/block/Block;Ljava/lang/String;I)I")) {
                transformRegisterBlock(method);
                tweaksLeft &= ~MASK_REGISTER_BLOCK;
            }
            if ((tweaksLeft & MASK_REGISTER_ITEM) != 0 &&
                method.name.equals("registerItem") &&
                method.desc.equals("(Lnet/minecraft/item/Item;Ljava/lang/String;I)I")) {
                transformRegisterItem(method);
                tweaksLeft &= ~MASK_REGISTER_ITEM;
            }
            if (tweaksLeft == 0) {
                break;
            }
        }
        if (tweaksLeft != 0) {
            IETransformer.logger.error("Could not accelerate block registration!");
        }
    }

    private void transformRegisterItem(MethodNode method) {
        var iter = method.instructions.iterator();
        while (iter.hasNext()) {
            var insn = iter.next();
            if (!(insn instanceof FieldInsnNode))
                continue;
            var typeInsn = (FieldInsnNode) insn;
            if (!(typeInsn.owner.equals("net/minecraft/item/ItemBlock") &&
                  typeInsn.name.equals("field_150939_a") &&
                  typeInsn.desc.equals("Lnet/minecraft/block/Block;")))
                continue;
            iter.previous();
            iter.add(new InsnNode(Opcodes.DUP));
            iter.add(new VarInsnNode(Opcodes.ALOAD, 0));
            iter.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/falsepattern/endlessids/asm/GameDataHooks", "cacheItemBlock", "(Lnet/minecraft/item/ItemBlock;Lcpw/mods/fml/common/registry/GameData;)V", false));
            return;
        }
        transformError(method);
    }

    private static void transformError(MethodNode method) {
        IETransformer.logger.error("Could not add caching code to GameData." + method.name + method.desc);
    }

    private void transformRegisterBlock(MethodNode method) {
        var iter = method.instructions.iterator();
        while (iter.hasNext()) {
            var insn = iter.next();
            if ((insn.getOpcode() != Opcodes.GETFIELD) && !(insn instanceof FieldInsnNode))
                continue;
            var fieldInsn = (FieldInsnNode) insn;
            if (!(fieldInsn.owner.equals("cpw/mods/fml/common/registry/GameData") &&
                  fieldInsn.name.equals("iItemRegistry") &&
                  fieldInsn.desc.equals("Lcpw/mods/fml/common/registry/FMLControlledNamespacedRegistry;")))
                continue;
            iter.remove();
            {
                val testInsn = iter.previous();
                if (testInsn.getOpcode() != Opcodes.ALOAD || ((VarInsnNode)testInsn).var != 0)
                    throw new AsmTransformException("Opcode assertion 1 failed!");
            }
            iter.remove();
            {
                val testInsn = iter.next();
                if (testInsn.getOpcode() != Opcodes.INVOKEVIRTUAL)
                    throw new AsmTransformException("Opcodes assertion 2 failed!");

                val testInvoke = (MethodInsnNode)testInsn;
                if (!testInvoke.owner.equals("cpw/mods/fml/common/registry/FMLControlledNamespacedRegistry") ||
                    !testInvoke.name.equals("typeSafeIterable"))
                    throw new AsmTransformException("Opcodes assertion 3 failed!");
            }
            iter.remove();
            iter.add(new VarInsnNode(Opcodes.ALOAD, 1));
            iter.add(new VarInsnNode(Opcodes.ALOAD, 0));
            iter.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/falsepattern/endlessids/asm/GameDataHooks", "fetchCachedItemBlock", "(Lnet/minecraft/block/Block;Lcpw/mods/fml/common/registry/GameData;)Lnet/minecraft/item/ItemBlock;", false));
            iter.add(new VarInsnNode(Opcodes.ASTORE, 4));
            iter.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/falsepattern/endlessids/asm/GameDataHooks", "fakeIterable", "()Ljava/lang/Iterable;", false));
            return;
        }
        transformError(method);
    }
}
