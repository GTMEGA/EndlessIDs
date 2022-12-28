package com.falsepattern.endlessids.mixin.mixins.common.vanilla.potion;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import net.minecraft.potion.Potion;

@Mixin(Potion.class)
public class PotionMixin {

    @Shadow
    @Final
    @Mutable
    public static Potion[] potionTypes;


    @Inject(method = "<clinit>",
            at = @At(value = "RETURN"),
            require = 1)
    private static void clinit(CallbackInfo ci) {
        potionTypes = new Potion[32767];
    }
}
