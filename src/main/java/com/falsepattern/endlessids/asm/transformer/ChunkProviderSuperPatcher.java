package com.falsepattern.endlessids.asm.transformer;

import com.falsepattern.endlessids.asm.IClassNodeTransformer;
import com.falsepattern.endlessids.asm.IETransformer;
import lombok.Data;
import lombok.val;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * <p>This class is the bread and butter of the patching logic from now on. It covers about 90% of cases where manual patching
 * was required, and so with a heavy heart, I (finally) implemented a universal patch.</p>
 *
 * <pre>
 *     Java form:
 *         ...
 *         byte[] abyte = chunk.getBiomeArray(); //replace byte[] with short[] and getBiomeArray with getBiomeShortArray
 *
 *         for (int k = 0; k < abyte.length; ++k) {
 *             //Possibility A:
 *             abyte[k] = (byte)abiomegenbase[k].biomeID; //from local variable //replace byte cast with short cast
 *
 *             //Possibility B:
 *             abyte[k] = (byte)this.biomesToGenerate[k].biomeID; //from arbitrary field //replace byte cast with short cast
 *         }
 *         ...
 *     In ASM form:
 *
 *        L1
 *         ALOAD chunk
 *         INVOKEVIRTUAL net/minecraft/world/chunk/Chunk.getBiomeArray ()[B //Replace with INVOKEVIRTUAL net/minecraft/world/chunk/Chunk.getBiomeShortArray ()[S
 *         ASTORE abyte
 *        L2
 *         ICONST_0
 *         ISTORE k
 *        L3
 *        FRAME <snip>
 *         ILOAD k
 *         ALOAD abyte
 *         ARRAYLENGTH
 *         IF_ICMPGE L6
 *        L4
 *         ALOAD abyte
 *         ILOAD k
 *
 *         //Possibility A:
 *         ALOAD abiomegenbase
 *         //Possibility B:
 *         ALOAD <something else, usually 0, so only check for 0>
 *         GETFIELD classname.fieldname : [Lnet/minecraft/world/biome/BiomeGenBase;
 *
 *         ILOAD k
 *         AALOAD
 *         GETFIELD net/minecraft/world/biome/BiomeGenBase.biomeID : I
 *         I2B                                                            //Replace with I2S
 *         BASTORE                                                        //Replace with SASTORE
 *        L5
 *         IINC k 1
 *         GOTO L3
 *        L6
 * </pre>
 */
public class ChunkProviderSuperPatcher implements IClassNodeTransformer {
    public static final String CLASS_BiomeGenBase = IETransformer.isObfuscated ? "ahu" : "net/minecraft/world/biome/BiomeGenBase";
    public static final String CLASS_Chunk = IETransformer.isObfuscated ? "apx" : "net/minecraft/world/chunk/Chunk";
    public static final String FIELD_biomeID = IETransformer.isObfuscated ? "ay" : "biomeID";
    public static final String METHOD_getBiomeArray = IETransformer.isObfuscated ? "m" : "getBiomeArray";
    public static final Map<Integer, BiFunction<AbstractInsnNode, Memory, Integer>> STATES = new HashMap<>();
    static {
        STATES.put(0, casting(VarInsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.ALOAD) {
                memory.chunkIndex = insn.var;
                memory.startingInsn = memory.currentInsn;
                return 1;
            }
            return 0;
        }));
        STATES.put(1, casting(MethodInsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.INVOKEVIRTUAL &&
                insn.owner.equals(CLASS_Chunk) &&
                insn.name.equals(METHOD_getBiomeArray) &&
                insn.desc.equals("()[B")) {
                memory.getBiomeArrayInsn = memory.currentInsn;
                return 2;
            }
            return 0;
        }));
        STATES.put(2, casting(VarInsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.ASTORE) {
                memory.biomeArrayIndex = insn.var;
                return 3;
            }
            return 0;
        }));
        STATES.put(3, casting(LabelNode.class, 4));
        STATES.put(4, casting(InsnNode.class, (insn) ->
                insn.getOpcode() == Opcodes.ICONST_0
                ? 5 : 0));
        STATES.put(5, casting(VarInsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.ISTORE) {
                memory.iteratorIndex = insn.var;
                return 6;
            }
            return 0;
        }));
        STATES.put(6, casting(LabelNode.class, 7));
        STATES.put(7, casting(FrameNode.class, (insn, memory) ->
                memory.locals.get(memory.chunkIndex).equals(CLASS_Chunk) &&
                memory.locals.get(memory.biomeArrayIndex).equals("[B") &&
                memory.locals.get(memory.iteratorIndex).equals(1)
                ? 8 : 0));
        STATES.put(8, casting(VarInsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.ILOAD &&
                insn.var == memory.iteratorIndex) {
                memory.tmp = 1;
                return 9;
            } else if (insn.getOpcode() == Opcodes.ALOAD &&
                       insn.var == memory.biomeArrayIndex) {
                memory.tmp = 2;
                return 10;
            }
            memory.tmp = 0;
            return 0;
        }));
        STATES.put(9, casting(VarInsnNode.class, (insn, memory) -> {
            if (memory.tmp == 1) {
                if (insn.getOpcode() == Opcodes.ALOAD &&
                    insn.var == memory.biomeArrayIndex) {
                    return 10;
                }
            } else if (memory.tmp == 2) {
                if (insn.getOpcode() == Opcodes.ILOAD &&
                    insn.var == memory.iteratorIndex) {
                    return 11;
                }
            }
            memory.tmp = 0;
            return 0;
        }));
        STATES.put(10, casting(InsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.ARRAYLENGTH) {
                if (memory.tmp == 1) {
                    return 11;
                } else if (memory.tmp == 2) {
                    return 9;
                }
            }
            memory.tmp = 0;
            return 0;
        }));
        STATES.put(11, casting(JumpInsnNode.class, (insn, memory) -> {
            if ((memory.tmp == 1 && insn.getOpcode() == Opcodes.IF_ICMPGE) ||
                (memory.tmp == 2 && insn.getOpcode() == Opcodes.IF_ICMPLE)) {
                memory.tmp = 0;
                return 12;
            }
            memory.tmp = 0;
            return 0;
        }));
        STATES.put(12, casting(LabelNode.class, 13));
        STATES.put(13, casting(VarInsnNode.class, (insn, memory) ->
                insn.getOpcode() == Opcodes.ALOAD &&
                insn.var == memory.biomeArrayIndex
                ? 14 : 0));
        STATES.put(14, casting(VarInsnNode.class, (insn, memory) ->
                insn.getOpcode() == Opcodes.ILOAD &&
                insn.var == memory.iteratorIndex
                ? 15 : 0));
        STATES.put(15, casting(VarInsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.ALOAD) {
                if (insn.var == 0) {
                    memory.altFlags = 1;
                    return 16;
                } else if (memory.locals.get(insn.var).equals("[L" + CLASS_BiomeGenBase + ";")) {
                    memory.altFlags = 0;
                    memory.biomesForGenerationIndex = insn.var;
                    return 17;
                }
            }
            return 0;
        }));
        STATES.put(16, casting(FieldInsnNode.class, (insn, memory) -> {
            if ((insn.getOpcode() == Opcodes.GETFIELD || insn.getOpcode() == Opcodes.GETSTATIC) &&
                insn.desc.equals("[L" + CLASS_BiomeGenBase + ";")) {
                memory.biomesForGenerationFieldOwner = insn.owner;
                memory.biomesForGenerationFieldName = insn.name;
                return 17;
            }
            return 0;
        }));
        STATES.put(17, casting(VarInsnNode.class, (insn, memory) ->
                insn.getOpcode() == Opcodes.ILOAD &&
                insn.var == memory.iteratorIndex
                ? 18 : 0));
        STATES.put(18, casting(InsnNode.class, (insn) ->
                insn.getOpcode() == Opcodes.AALOAD
                ? 19 : 0));
        STATES.put(19, casting(FieldInsnNode.class, (insn) ->
                insn.getOpcode() == Opcodes.GETFIELD &&
                insn.owner.equals(CLASS_BiomeGenBase) &&
                insn.name.equals(FIELD_biomeID) &
                insn.desc.equals("I")
                ? 20 : 0));
        STATES.put(20, casting(InsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.I2B) {
                memory.castInsn = memory.currentInsn;
                return 21;
            }
            return 0;
        }));
        STATES.put(21, casting(InsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.BASTORE) {
                memory.arrayStoreInsn = memory.currentInsn;
                return -2;
            }
            return 0;
        }));
    }
    private static <T> BiFunction<AbstractInsnNode, Memory, Integer> casting(Class<T> clazz, int okState) {
        return casting(clazz, (insn, memory) -> okState);
    }

    private static <T> BiFunction<AbstractInsnNode, Memory, Integer> casting(Class<T> clazz, Function<T, Integer> okCode) {
        return casting(clazz, (insn, memory) -> okCode.apply(insn));
    }

    private static <T> BiFunction<AbstractInsnNode, Memory, Integer> casting(Class<T> clazz, BiFunction<T, Memory, Integer> okCode) {
        return (insn, memory) -> {
            if (insn instanceof LineNumberNode) {
                return -1;
            } else if (clazz.isInstance(insn)) {
                return okCode.apply(clazz.cast(insn), memory);
            } else {
                return 0;
            }
        };
    }

    @Override
    public void transform(final ClassNode cn, final boolean obfuscated) {
        for (val method: cn.methods) {
                val memory = findTheTarget(method);
                if (memory != null) {
                    val insnList = method.instructions;
                    for (val node: method.localVariables) {
                        if (node.desc.equals("[B") && node.index == memory.biomeArrayIndex) {
                            node.desc = "[S";
                        }
                    }
                    insnList.set(insnList.get(memory.getBiomeArrayInsn), new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/world/chunk/Chunk", "getBiomeShortArray", "()[S", false));
                    insnList.set(insnList.get(memory.castInsn), new InsnNode(Opcodes.I2S));
                    insnList.set(insnList.get(memory.arrayStoreInsn), new InsnNode(Opcodes.SASTORE));
                }
        }
    }


    private static Memory findTheTarget(MethodNode method) {
        val insnList = method.instructions;
        int state = 0;
        val memory = new Memory();
        for (int insnIndex = 0; insnIndex < insnList.size(); insnIndex++) {
            memory.currentInsn = insnIndex;
            val insn = insnList.get(insnIndex);
            if (insn instanceof FrameNode) {
                val frame = (FrameNode) insn;
                if (frame.type == Opcodes.F_NEW) {
                    memory.locals.clear();
                    memory.locals.addAll(frame.local);
                }
            }
            val newState = STATES.get(state).apply(insn, memory);
            switch (newState) {
                case -1:
                    continue;
                case -2:
                    return memory;
                default:
                    state = newState;
            }
        }
        return null;
    }

    @Data
    private static class Memory {
        int currentInsn;

        int startingInsn;
        int getBiomeArrayInsn;
        int castInsn;
        int arrayStoreInsn;

        int chunkIndex;
        int biomeArrayIndex;
        int iteratorIndex;

        long altFlags;
        String biomesForGenerationFieldOwner;
        String biomesForGenerationFieldName;
        int biomesForGenerationIndex;

        int tmp;

        final List<Object> locals = new ArrayList<>();
    }
}