package com.falsepattern.endlessids.mixin.mixins.common.biometweaker;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import me.superckl.biometweaker.common.handler.BiomeEventHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(value = BiomeEventHandler.class,
       remap = false)
public abstract class BiomeEventHandlerMixin {
    @Mutable
    @Shadow
    @Final
    private int[] colorCache;

    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 1)
    private static int extendIDs1(int constant) {
        return ExtendedConstants.biomeIDCount;
    }

    @Inject(method = "<init>",
            at = @At("RETURN"),
            require = 1)
    private void extendIDs2(CallbackInfo ci) {
        colorCache = new int[ExtendedConstants.biomeIDCount * 3];
        Arrays.fill(colorCache, -2);
    }

    @ModifyConstant(method = "onGetFoliageColor",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 1)
    private static int extendIDs3(int constant) {
        return ExtendedConstants.biomeIDCount;
    }

    @ModifyConstant(method = "onGetWaterColor",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount * 2),
                    require = 1)
    private static int extendIDs4(int constant) {
        return ExtendedConstants.biomeIDCount * 2;
    }
}
