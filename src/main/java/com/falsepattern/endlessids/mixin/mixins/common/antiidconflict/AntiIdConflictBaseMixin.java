package com.falsepattern.endlessids.mixin.mixins.common.antiidconflict;

import code.elix_x.coremods.antiidconflict.AntiIdConflictBase;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mixin(value = AntiIdConflictBase.class,
       remap = false)
public abstract class AntiIdConflictBaseMixin {
    @Shadow
    public static File mainFolder;

    @Inject(method = "preinit",
            at = @At("HEAD"),
            require = 1)
    private static void fixPath(FMLPreInitializationEvent event, CallbackInfo ci) {
        mainFolder = new File(event.getModConfigurationDirectory().getPath(), "AntiIDConflict");
    }

    @Redirect(method = "preinit",
              at = @At(value = "FIELD",
                       target = "Lcode/elix_x/coremods/antiidconflict/AntiIdConflictBase;mainFolder:Ljava/io/File;",
                       opcode = Opcodes.PUTSTATIC),
              require = 1)
    private static void removeOldPut(File value) {
    }
}
