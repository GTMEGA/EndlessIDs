package com.falsepattern.endlessids.mixin.mixins.common.potion.vanilla;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import net.minecraft.potion.Potion;

import java.util.Arrays;

@Mixin(Potion.class)
public abstract class PotionMixin {

    @Shadow(aliases = "field_76425_a")
    @Final
    @Mutable
    public static Potion[] potionTypes;


    @Inject(method = "<clinit>",
            at = @At(value = "RETURN"),
            require = 1)
    private static void clinit(CallbackInfo ci) {
        potionTypes = Arrays.copyOf(potionTypes, ExtendedConstants.maximumPotionIDs);
    }
}
