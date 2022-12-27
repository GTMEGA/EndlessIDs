package com.falsepattern.endlessids.mixin.mixins.common.netherlicious;

import DelirusCrux.Netherlicious.Utility.Configuration.BiomeConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = BiomeConfiguration.class,
       remap = false)
public abstract class BiomeConfigurationMixin {
    @ModifyConstant(method = "init",
                    constant = {@Constant(intValue = 170),
                                @Constant(intValue = 175)},
                    require = 2)
    private static int shiftBiomeIDsUp(int id) {
        return id + 3000;
    }
}
