package com.falsepattern.endlessids.asm.transformer;

import com.falsepattern.endlessids.asm.IETransformer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.util.CheckClassAdapter;

import net.minecraft.launchwrapper.Launch;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DevFixer {
    private static final ClassNode blocks;
    static {
        if (IETransformer.isObfuscated) {
            blocks = null;
        } else {
            try {
                byte[] blox = Launch.classLoader.getClassBytes("net/minecraft/init/Blocks");
                blocks = new ClassNode(Opcodes.ASM5);
                final ClassReader reader = new ClassReader(blox);
                reader.accept(blocks, 0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static byte[] fixDev(byte[] classBytes) {
        if (IETransformer.isObfuscated) {
            return classBytes;
        }
        val cn = new ClassNode(Opcodes.ASM5);
        val reader = new ClassReader(classBytes);
        reader.accept(cn, 0);
        transform(cn);
        val writer = new ClassWriter(0);
        val check = new CheckClassAdapter(writer);
        cn.accept(check);
        return writer.toByteArray();
    }

    @SneakyThrows
    public static void transform(final ClassNode cn) {
        if (IETransformer.isObfuscated) {
            return;
        }
        remapBlocks(cn);
        if (cn.name.equals("net/minecraft/world/biome/BiomeGenBase")) {
            biomeTweakerCompat(cn);
        }
    }

    private static void biomeTweakerCompat(final ClassNode cn) {
        for (val field: cn.fields) {
            if (field.name.startsWith("spawnable")) {
                field.access &= ~Opcodes.ACC_PROTECTED;
                field.access |= Opcodes.ACC_PUBLIC;
            }
        }
    }

    private static void remapBlocks(final ClassNode cn) {
        for (val method: cn.methods) {
            val instructions = method.instructions;
            int insnCount = instructions.size();
            for (int i = 0; i < insnCount; i++) {
                val insn = instructions.get(i);
                if (insn instanceof FieldInsnNode) {
                    val field = (FieldInsnNode) insn;
                    if (field.getOpcode() == Opcodes.GETSTATIC &&
                        field.owner.equals("net/minecraft/init/Blocks")) {
                        for (val blockField: blocks.fields) {
                            if (blockField.name.equals(field.name) &&
                                !blockField.desc.equals(field.desc)) {
                                field.desc = blockField.desc;
                            }
                        }
                    }
                }
            }
        }
    }
}
