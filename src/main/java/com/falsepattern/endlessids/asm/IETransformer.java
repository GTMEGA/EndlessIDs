package com.falsepattern.endlessids.asm;

import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.apache.logging.log4j.Logger;
import net.minecraft.launchwrapper.IClassTransformer;

public class IETransformer implements IClassTransformer
{
    private static final boolean enablePreVerification = false;
    private static final boolean enablePostVerification = true;
    public static final Logger logger;
    public static boolean isObfuscated;
    private static Boolean isClient;
    
    public IETransformer() {
        super();
    }
    
    public byte[] transform(final String name, final String transformedName, final byte[] bytes) {
        if (bytes == null) {
            return bytes;
        }
        final ClassEdit edit = ClassEdit.get(transformedName);
        if (edit == null) {
            return bytes;
        }
        IETransformer.logger.debug("Patching {} with {}...", new Object[] { transformedName, edit.getName() });
        final ClassNode cn = new ClassNode(327680);
        final ClassReader reader = new ClassReader(bytes);
        final int readFlags = 0;
        reader.accept((ClassVisitor)cn, 0);
        try {
            edit.getTransformer().transform(cn, IETransformer.isObfuscated);
        }
        catch (AsmTransformException t) {
            IETransformer.logger.error("Error transforming {} with {}: {}", new Object[] { transformedName, edit.getName(), t.getMessage() });
            throw t;
        }
        catch (Throwable t2) {
            IETransformer.logger.error("Error transforming {} with {}: {}", new Object[] { transformedName, edit.getName(), t2.getMessage() });
            throw new RuntimeException(t2);
        }
        final ClassWriter writer = new ClassWriter(0);
        try {
            final ClassVisitor check = (ClassVisitor)new CheckClassAdapter((ClassVisitor)writer);
            cn.accept(check);
        }
        catch (Throwable t3) {
            IETransformer.logger.error("Error verifying {} transformed with {}: {}", new Object[] { transformedName, edit.getName(), t3.getMessage() });
            throw new RuntimeException(t3);
        }
        IETransformer.logger.debug("Patched {} successfully.", new Object[] { edit.getName() });
        return writer.toByteArray();
    }
    
    public static boolean isClient() {
        if (IETransformer.isClient == null) {
            IETransformer.isClient = Boolean.valueOf(IETransformer.class.getResource("/net/minecraft/client/main/Main.class") != null);
        }
        return IETransformer.isClient;
    }
    
    static {
        logger = LogManager.getLogger("NEID");
        IETransformer.isClient = null;
    }
}
