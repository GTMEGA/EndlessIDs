package com.falsepattern.endlessids.mixin.mixins.common.eb;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import enhancedbiomes.world.gen.layer.GenLayerEBHills;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GenLayerEBHills.class,
       remap = false)
public abstract class GenLayerEBHillsMixin {
    @ModifyConstant(method = "getInts",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    remap = true,
                    require = 8)
    private int extendIDs1(int constant) {
        return ExtendedConstants.biomeIDCount;
    }

    @ModifyConstant(method = "getInts",
                    constant = @Constant(intValue = 128),
                    remap = true,
                    require = 1)
    private int extendIDs2(int constant) {
        return ExtendedConstants.biomeIDCount;
    }

    @Redirect(method = "getInts",
              at = @At(value = "INVOKE",
                       target = "Lorg/apache/logging/log4j/Logger;debug(Ljava/lang/String;)V"),
              remap = true,
              require = 1)
    private void noLog(Logger instance, String s) {

    }
}
