package com.falsepattern.endlessids.mixin.mixins.common.dev.spacecore;

import com.spacechase0.minecraft.spacecore.mcp.ModInfoGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ModInfoGenerator.class,
       remap = false)
public abstract class ModInfoGeneratorMixin {
    @Inject(method = "generate",
            at = @At("HEAD"),
            cancellable = true,
            require = 1)
    private static void noGenerate(CallbackInfo ci) {
        ci.cancel();
    }
}
