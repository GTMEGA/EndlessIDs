package com.falsepattern.endlessids.asm;

import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.asm.transformer.ChunkProviderSuperPatcher;
import com.falsepattern.endlessids.asm.transformer.DevFixer;
import lombok.val;
import lombok.var;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import net.minecraft.launchwrapper.IClassTransformer;

import java.util.ArrayList;
import java.util.List;

public class IETransformer implements IClassTransformer {
    public static final Logger logger;
    private static final List<String> blacklist = new ArrayList<>();
    public static boolean isObfuscated;

    static {
        logger = LogManager.getLogger(Tags.MODNAME + " ASM");
        blacklist.add("net.minecraft.world.chunk.storage.AnvilChunkLoader");
    }

    public IETransformer() {
        super();
    }

    public byte[] transform(final String name, final String transformedName, final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        if (blacklist.contains(transformedName)) {
            return bytes;
        }
        var edits = new ArrayList<ClassEdit>(ClassEdit.get(transformedName));
        ClassNode cn = new ClassNode(Opcodes.ASM5);
        {
            val reader = new ClassReader(bytes);
            reader.accept(cn, 0);
        }
        boolean expanded = false;
        if (ChunkProviderSuperPatcher.shouldPatch(cn)) {
            expanded = true;
            {
                cn = new ClassNode(Opcodes.ASM5);
                val reader = new ClassReader(bytes);
                reader.accept(cn, ClassReader.EXPAND_FRAMES);
            }
            edits.add(ClassEdit.ChunkProviderSuperPatcher);
        }
        if (edits.size() == 0) {
            return isObfuscated ? bytes : DevFixer.fixDev(bytes);
        }
        if (!isObfuscated) {
            DevFixer.transform(cn);
        }
        for (final ClassEdit edit : edits) {
            if (edit == null) {
                continue;
            }
            IETransformer.logger.debug("Patching {} with {}...", transformedName, edit.getName());
            try {
                edit.getTransformer().transform(cn, IETransformer.isObfuscated);
            } catch (AsmTransformException t) {
                IETransformer.logger.error("Error transforming {} with {}: {}", transformedName, edit.getName(),
                                           t.getMessage());
                throw t;
            } catch (Throwable t2) {
                IETransformer.logger.error("Error transforming {} with {}: {}", transformedName, edit.getName(),
                                           t2.getMessage());
                throw new RuntimeException(t2);
            }
        }
        final ClassWriter writer = new ClassWriter(expanded ? ClassWriter.COMPUTE_FRAMES : 0);
        cn.accept(writer);
        IETransformer.logger.debug("Patched {} successfully.", transformedName);
        return writer.toByteArray();
    }
}
