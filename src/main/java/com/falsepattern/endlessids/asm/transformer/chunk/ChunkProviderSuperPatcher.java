package com.falsepattern.endlessids.asm.transformer.chunk;

import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.asm.EndlessIDsCore;
import com.falsepattern.endlessids.asm.EndlessIDsTransformer;
import com.falsepattern.lib.turboasm.ClassNodeHandle;
import com.falsepattern.lib.turboasm.TurboClassTransformer;
import lombok.Data;
import lombok.val;
import lombok.var;
import org.jetbrains.annotations.NotNull;
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
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

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
 *
 *             //Possibility C:
 *             abyte[k] = (byte)SomeClass.biome.biomeID; //usually galacticraft and derivatives do this for space stations.
 *
 *             //Possibility D:
 *             abyte[k] = (byte)object.biome.biomeID; //could also happen.
 *         }
 *         ...
 *     In ASM form:
 *
 *        L1
 *         //state 0
 *         ALOAD chunk
 *         //state 1
 *         INVOKEVIRTUAL net/minecraft/world/chunk/Chunk.getBiomeArray ()[B //Replace with INVOKEVIRTUAL net/minecraft/world/chunk/Chunk.getBiomeShortArray ()[S
 *         //state 2
 *         ASTORE abyte
 *         //state 3
 *        L2
 *         //state 4
 *         ICONST_0
 *         //state 5
 *         ISTORE k
 *         //state 6
 *        L3
 *         //state 7
 *        FRAME <snip>
 *         //state 8
 *         ILOAD k
 *         //state 9
 *         ALOAD abyte
 *         //state 10
 *         ARRAYLENGTH
 *         //state 11
 *         IF_ICMPGE L6
 *         //state 12
 *        L4
 *         //state 13
 *         ALOAD abyte
 *         //state 14
 *         ILOAD k
 *
 *         //Branch A | B | C | D
 *         //A:
 *         ALOAD abiomegenbase
 *
 *         //B:
 *         ALOAD <something else>
 *         GETFIELD classname.fieldname : [Lnet/minecraft/world/biome/BiomeGenBase;
 *
 *         //C:
 *         GETSTATIC classname.fieldname : Lnet/minecraft/world/biome/BiomeGenBase;
 *
 *         //D:
 *         ALOAD <something else>
 *         GETFIELD classname.fieldname : Lnet/minecraft/world/biome/BiomeGenBase;
 *
 *         //Join A | B
 *         ILOAD k
 *         AALOAD
 *
 *         //Join A,B | C | D
 *
 *         //state 15
 *         GETFIELD net/minecraft/world/biome/BiomeGenBase.biomeID : I
 *         //state 16
 *         I2B                                                            //Replace with I2S
 *         //state 17
 *         BASTORE                                                        //Replace with SASTORE
 *         //state final, matched.
 *        L5
 *         IINC k 1
 *         GOTO L3
 *        L6
 * </pre>
 */
public class ChunkProviderSuperPatcher implements TurboClassTransformer {
    public static final String[] CLASS_BiomeGenBase;
    public static final String[] CLASS_IChunkProvider;
    public static final String[] CLASS_ChunkProviderGenerate;
    public static final String[] CLASS_Chunk;
    public static final String[] FIELD_biomeID;
    public static final String[] METHOD_getBiomeArray;
    private static State<AbstractInsnNode> STATE0 = null;
    private static State<AbstractInsnNode> STATE1 = null;
    private static State<AbstractInsnNode> STATE2 = null;
    private static State<AbstractInsnNode> STATE3 = null;
    private static State<AbstractInsnNode> STATE4 = null;
    private static State<AbstractInsnNode> STATE5 = null;
    private static State<AbstractInsnNode> STATE6 = null;
    private static State<AbstractInsnNode> STATE7 = null;
    private static State<AbstractInsnNode> STATE8 = null;
    private static State<AbstractInsnNode> STATE9 = null;
    private static State<AbstractInsnNode> STATE10 = null;
    private static State<AbstractInsnNode> STATE11 = null;
    private static State<AbstractInsnNode> STATE12 = null;
    private static State<AbstractInsnNode> STATE13 = null;
    private static State<AbstractInsnNode> STATE14 = null;
    private static State<AbstractInsnNode> STATE14_ABD1 = null;
    private static State<AbstractInsnNode> STATE14_ABD1_BD1 = null;
    private static State<AbstractInsnNode> STATE14_ABD1_BA2 = null;
    private static State<AbstractInsnNode> STATE14_ABD1_BA3 = null;
    private static State<AbstractInsnNode> STATE14_C1 = null;
    private static State<AbstractInsnNode> STATE15 = null;
    private static State<AbstractInsnNode> STATE16 = null;
    private static State<AbstractInsnNode> STATE17 = null;
    private static State<AbstractInsnNode> STATE_FINAL = null;

    static {
        if (EndlessIDsCore.deobfuscated) {
            CLASS_BiomeGenBase = new String[]{"net/minecraft/world/biome/BiomeGenBase"};
            CLASS_IChunkProvider = new String[]{"net/minecraft/world/chunk/IChunkProvider"};
            CLASS_ChunkProviderGenerate = new String[]{"net/minecraft/world/gen/ChunkProviderGenerate"};
            CLASS_Chunk = new String[]{"net/minecraft/world/chunk/Chunk"};
            FIELD_biomeID = new String[]{"biomeID"};
            METHOD_getBiomeArray = new String[]{"getBiomeArray"};
        } else {
            CLASS_BiomeGenBase = new String[]{"ahu", "net/minecraft/world/biome/BiomeGenBase"};
            CLASS_IChunkProvider = new String[]{"apu", "net/minecraft/world/chunk/IChunkProvider"};
            CLASS_ChunkProviderGenerate = new String[]{"aqz", "net/minecraft/world/gen/ChunkProviderGenerate"};
            CLASS_Chunk = new String[]{"apx", "net/minecraft/world/chunk/Chunk"};
            FIELD_biomeID = new String[]{"ay", "field_76756_M"};
            METHOD_getBiomeArray = new String[]{"m", "func_76605_m"};
        }
    }

    static {
        STATE_FINAL = (insn, memory) -> STATE_FINAL;

        STATE0 = casting(VarInsnNode.class, (insn, memory) -> {
            memory.reset();
            if (insn.getOpcode() == Opcodes.ALOAD) {
                memory.chunkIndex = insn.var;
                memory.startingInsn = insn;
                return STATE1;
            }
            return STATE0;
        });

        STATE1 = casting(MethodInsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.INVOKEVIRTUAL &&
                anyMatch(insn.owner, CLASS_Chunk) &&
                anyMatch(insn.name, METHOD_getBiomeArray) &&
                insn.desc.equals("()[B")) {
                memory.getBiomeArrayInsn = insn;
                return STATE2;
            }
            return STATE0;
        });

        STATE2 = casting(VarInsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.ASTORE) {
                memory.biomeArrayIndex = insn.var;
                memory.startPatchingFrames();
                return STATE3;
            }
            return STATE0;
        });

        STATE3 = typeCheck(LabelNode.class, () -> STATE4);

        STATE4 = casting(InsnNode.class, (insn) ->
                insn.getOpcode() == Opcodes.ICONST_0
                ? STATE5 : STATE0);

        STATE5 = casting(VarInsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.ISTORE) {
                memory.iteratorIndex = insn.var;
                return STATE6;
            }
            return STATE0;
        });

        STATE6 = typeCheck(LabelNode.class, () -> STATE7);

        STATE7 = casting(FrameNode.class, (insn, memory) ->
                anyMatch((String) memory.locals.get(memory.chunkIndex), CLASS_Chunk) &&
                memory.locals.get(memory.biomeArrayIndex).equals("[B") &&
                memory.locals.get(memory.iteratorIndex).equals(1)
                ? STATE8 : STATE0);

        STATE8 = casting(VarInsnNode.class, (insn, memory) ->
                insn.getOpcode() == Opcodes.ILOAD &&
                insn.var == memory.iteratorIndex
                ? STATE9 : STATE0);

        STATE9 = casting(VarInsnNode.class, (insn, memory) ->
                insn.getOpcode() == Opcodes.ALOAD
                && insn.var == memory.biomeArrayIndex
                ? STATE10 : STATE0);

        STATE10 = casting(InsnNode.class, (insn) -> insn.getOpcode() == Opcodes.ARRAYLENGTH ? STATE11 : STATE0);

        STATE11 = casting(JumpInsnNode.class, (insn) -> insn.getOpcode() == Opcodes.IF_ICMPGE ? STATE12 : STATE0);

        STATE12 = typeCheck(LabelNode.class, () -> STATE13);

        STATE13 = casting(VarInsnNode.class, (insn, memory) ->
                insn.getOpcode() == Opcodes.ALOAD &&
                insn.var == memory.biomeArrayIndex
                ? STATE14 : STATE0);

        STATE14 = casting(VarInsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.ILOAD && insn.var == memory.iteratorIndex) {
                if (insn.getNext() instanceof FieldInsnNode) {
                    return STATE14_C1;
                } else {
                    return STATE14_ABD1;
                }
            }
            return STATE0;
        });

        STATE14_ABD1 = casting(VarInsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.ALOAD) {
                if (anyMatch((String) memory.locals.get(insn.var), CLASS_BiomeGenBase, "[L", ";")) {
                    return STATE14_ABD1_BA2;
                } else {
                    return STATE14_ABD1_BD1;
                }
            }
            return STATE0;
        });

        STATE14_ABD1_BD1 = casting(FieldInsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.GETFIELD) {
                if (anyMatch(insn.desc, CLASS_BiomeGenBase, "[L", ";")) {
                    return STATE14_ABD1_BA2;
                } else if (anyMatch(insn.desc, CLASS_BiomeGenBase, "L", ";")) {
                    return STATE15;
                }
            }
            return STATE0;
        });

        STATE14_ABD1_BA2 = casting(VarInsnNode.class, (insn, memory) ->
                insn.getOpcode() == Opcodes.ILOAD && insn.var == memory.iteratorIndex ? STATE14_ABD1_BA3 : STATE0);

        STATE14_ABD1_BA3 = casting(InsnNode.class, (insn) -> insn.getOpcode() == Opcodes.AALOAD ? STATE15 : STATE0);

        STATE14_C1 = casting(FieldInsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.GETSTATIC && anyMatch(insn.desc, CLASS_BiomeGenBase, "L", ";")) {
                return STATE15;
            }
            return STATE0;
        });

        STATE15 = casting(FieldInsnNode.class, (insn) ->
                insn.getOpcode() == Opcodes.GETFIELD &&
                anyMatch(insn.owner, CLASS_BiomeGenBase) &&
                anyMatch(insn.name, FIELD_biomeID) &
                insn.desc.equals("I")
                ? STATE16 : STATE0);

        STATE16 = casting(InsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.I2B) {
                memory.castInsn = insn;
                return STATE17;
            }
            return STATE0;
        });

        STATE17 = casting(InsnNode.class, (insn, memory) -> {
            if (insn.getOpcode() == Opcodes.BASTORE) {
                memory.arrayStoreInsn = insn;
                return STATE_FINAL;
            }
            return STATE0;
        });
    }

    private static boolean scanForBrokenCall(MethodNode method) {
        val instructions = method.instructions;
        val insnCount = instructions.size();
        for (int i = 0; i < insnCount; i++) {
            val insn = instructions.get(i);
            if (insn.getOpcode() != Opcodes.INVOKEVIRTUAL) {
                continue;
            }
            val invoke = (MethodInsnNode) insn;

            if (anyMatch(invoke.owner, CLASS_Chunk) && anyMatch(invoke.name, METHOD_getBiomeArray) &&
                invoke.desc.equals("()[B")) {
                return true;
            }
        }
        return false;
    }

    private static boolean anyMatch(List<String> str, String[] candidates) {
        for (val s : str) {
            for (val candidate : candidates) {
                if (s.equals(candidate)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean anyMatch(String str, String[] candidates) {
        for (val candidate : candidates) {
            if (str.equals(candidate)) {
                return true;
            }
        }
        return false;
    }

    private static boolean anyMatch(String str, String[] candidates, String prefix, String suffix) {
        for (val candidate : candidates) {
            if (str.equals(prefix + candidate + suffix)) {
                return true;
            }
        }
        return false;
    }

    private static <T extends AbstractInsnNode> State<AbstractInsnNode> typeCheck(Class<T> clazz, Supplier<State<AbstractInsnNode>> okState) {
        return casting(clazz, (insn, memory) -> okState.get());
    }

    private static <T extends AbstractInsnNode> State<AbstractInsnNode> casting(Class<T> clazz, Function<T, State<AbstractInsnNode>> okState) {
        return casting(clazz, (insn, memory) -> okState.apply(insn));
    }

    private static <T extends AbstractInsnNode> State<AbstractInsnNode> casting(Class<T> clazz, State<T> okState) {
        return (insn, memory) -> {
            if (insn instanceof LineNumberNode) {
                return null;
            } else if (clazz.isInstance(insn)) {
                return okState.apply(clazz.cast(insn), memory);
            } else {
                return STATE0;
            }
        };
    }

    private static Memory findTheTarget(MethodNode method, ClassNode cn) {
        val insnList = method.instructions;
        var currentState = STATE0;
        val memory = new Memory();
        memory.initLocals(method, cn);
        val iter = insnList.iterator();
        while (iter.hasNext()) {
            val insn = iter.next();
            if (insn instanceof FrameNode) {
                val frame = (FrameNode) insn;
                memory.recordFrame(frame);
                memory.updateLocalsState(frame);
            }
            val newState = currentState.apply(insn, memory);
            if (newState != null) {
                if (newState == STATE_FINAL) {
                    while (iter.hasNext()) {
                        val insn2 = iter.next();
                        if (insn2 instanceof FrameNode) {
                            memory.recordFrame((FrameNode) insn2);
                        }
                    }
                    return memory;
                }
                currentState = newState;
            }
        }
        return null;
    }

    @Override
    public String owner() {
        return Tags.MODNAME;
    }

    @Override
    public String name() {
        return "ChunkProviderSuperPatcher";
    }

    @Override
    public boolean shouldTransformClass(@NotNull String className, @NotNull ClassNodeHandle classNode) {
        if ("net.minecraft.world.chunk.storage.AnvilChunkLoader".equals(className))
            return false;
        val cn = classNode.getNode();
        if (cn == null)
            return false;
        for (MethodNode method : cn.methods) {
            if (scanForBrokenCall(method)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean transformClass(@NotNull String className, @NotNull ClassNodeHandle classNode) {
        val cn = classNode.getNode();
        if (cn == null)
            return false;
        boolean patched = false;
        for (val method : cn.methods) {
            val memory = findTheTarget(method, cn);
            if (memory != null) {
                patched = true;
                EndlessIDsTransformer.logger.debug("[ChunkProvider patcher]: Match found in method " + method.name);
                val insnList = method.instructions;
                for (val node : method.localVariables) {
                    if (node.desc.equals("[B") && node.index == memory.biomeArrayIndex) {
                        node.desc = "[S";
                    }
                }
                val getBiomeArray = memory.getBiomeArrayInsn;
                insnList.set(getBiomeArray,
                             new MethodInsnNode(Opcodes.INVOKEVIRTUAL, getBiomeArray.owner, "getBiomeShortArray",
                                                "()[S", false));
                insnList.set(memory.castInsn, new InsnNode(Opcodes.I2S));
                insnList.set(memory.arrayStoreInsn, new InsnNode(Opcodes.SASTORE));
                patchStackFrames(memory, method, cn);
            }
        }
        if (!patched) {
            EndlessIDsTransformer.logger.debug("[ChunkProvider patcher]: No matches found in class! This is not explicitly " +
                                               "an error, because the patch might be applied by a mixin instead!");
        }
        return patched;
    }


    private static void patchStackFrames(Memory memory, MethodNode method, ClassNode owner) {
        memory.locals.clear();
        memory.initLocals(method, owner);
        for (val frame: memory.framesLeaveUnpatched)
            memory.updateLocalsState(frame);

        int localToPatch = memory.biomeArrayIndex;
        for (val frame: memory.framesToPatch) {
            int currentLocalIndex = memory.locals.size() - 1;
            if (frame.local != null) {
                switch (frame.type) {
                    case Opcodes.F_NEW:
                    case Opcodes.F_FULL:
                        if (frame.local.size() > localToPatch &&
                            "[B".equals(frame.local.get(localToPatch))) {
                            frame.local.set(localToPatch, "[S");
                        }
                        break;
                    case Opcodes.F_APPEND:
                        if (currentLocalIndex < localToPatch &&
                            (currentLocalIndex + frame.local.size()) >= localToPatch) {
                            int indexInFrame = localToPatch - currentLocalIndex - 1;
                            if ("[B".equals(frame.local.get(indexInFrame))) {
                                frame.local.set(indexInFrame, "[S");
                            }
                        }
                }
            }
            memory.updateLocalsState(frame);
        }
    }


    public interface State<T extends AbstractInsnNode> extends BiFunction<T, Memory, State<AbstractInsnNode>> {
    }

    @Data
    private static class Memory {

        final List<Object> locals = new ArrayList<>();
        List<Object> localsAtPatchStart;
        List<FrameNode> framesLeaveUnpatched = new ArrayList<>();
        List<FrameNode> framesToPatch = new ArrayList<>();
        boolean patchingFrames;
        VarInsnNode startingInsn;
        MethodInsnNode getBiomeArrayInsn;
        InsnNode castInsn;
        InsnNode arrayStoreInsn;
        int chunkIndex;
        int biomeArrayIndex;
        int iteratorIndex;

        public void startPatchingFrames() {
            localsAtPatchStart = new ArrayList<>(locals);
            patchingFrames = true;
        }

        public void recordFrame(FrameNode frame) {
            if (patchingFrames)
                framesToPatch.add(frame);
            else
                framesLeaveUnpatched.add(frame);
        }

        public void reset() {
            localsAtPatchStart = null;
            framesLeaveUnpatched.addAll(framesToPatch);
            framesToPatch.clear();
            patchingFrames = false;
            startingInsn = null;
            getBiomeArrayInsn = null;
            castInsn = null;
            arrayStoreInsn = null;
            chunkIndex = 0;
            biomeArrayIndex = 0;
            iteratorIndex = 0;
        }

        private static int getDescriptorNameEnd(String desc, int index) {
            val c = desc.charAt(index);
            switch (c) {
                case '[':
                    return getDescriptorNameEnd(desc, index + 1);
                case 'L':
                    do {
                        index++;
                    } while (desc.charAt(index) != ';');
                default:
                    return index + 1;
            }
        }

        private static List<Object> parseDesc(String desc) {
            val result = new ArrayList<Object>();
            desc = desc.substring(1, desc.indexOf(')'));
            for (int i = 0; i < desc.length(); i++) {
                val end = getDescriptorNameEnd(desc, i);
                val name = desc.substring(i, end);
                switch (name) {
                    case "I":
                    case "S":
                    case "B":
                    case "Z":
                        result.add(Opcodes.INTEGER);
                        break;
                    case "F":
                        result.add(Opcodes.FLOAT);
                        break;
                    case "D":
                        result.add(Opcodes.DOUBLE);
                        result.add(Opcodes.DOUBLE);
                        break;
                    case "J":
                        result.add(Opcodes.LONG);
                        result.add(Opcodes.LONG);
                        break;
                    default:
                        result.add(name);
                }
                i = end - 1;
            }
            return result;
        }

        public void initLocals(MethodNode method, ClassNode owner) {
            locals.clear();
            if ((method.access & Opcodes.ACC_STATIC) == 0) {
                locals.add("L" + owner.name + ";");
            }
            locals.addAll(parseDesc(method.desc));
        }

        public void updateLocalsState(FrameNode frame) {
            switch (frame.type) {
                case Opcodes.F_NEW:
                case Opcodes.F_FULL:
                    locals.clear();
                case Opcodes.F_APPEND:
                    if (frame.local != null) {
                        for (val local : frame.local) {
                            if (Objects.equals(local, Opcodes.DOUBLE) || Objects.equals(local, Opcodes.LONG))
                                locals.add(local);
                            locals.add(local);
                        }
                    }
                    break;
                case Opcodes.F_CHOP: {
                    int removeCount = frame.local.size();
                    for (int i = removeCount - 1; i >= 0 && !locals.isEmpty(); i--) {
                        val removed = locals.remove(locals.size() - 1);
                        if (Objects.equals(removed, Opcodes.DOUBLE) || Objects.equals(removed, Opcodes.LONG))
                            locals.remove(locals.size() - 1);
                    }
                    break;
                }
                case Opcodes.F_SAME:
                case Opcodes.F_SAME1:
                    break;
            }
        }
    }
}