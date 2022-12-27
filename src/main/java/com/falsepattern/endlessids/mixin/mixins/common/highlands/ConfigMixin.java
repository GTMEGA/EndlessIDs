package com.falsepattern.endlessids.mixin.mixins.common.highlands;

import highlands.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = Config.class,
       remap = false)
public abstract class ConfigMixin {
    @ModifyArg(method = "addBiomeEntries",
               at = @At(value = "INVOKE",
                        target = "Lnet/minecraftforge/common/config/Configuration;get(Ljava/lang/String;Ljava/lang/String;I)Lnet/minecraftforge/common/config/Property;"),
               index = 2,
               require = 44)
    private static int shiftBiomeIDsUp(int id) {
        return id + 7000;
    }
}
