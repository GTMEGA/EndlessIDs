package com.falsepattern.endlessids.mixin.mixins.common.darkworld;

import matthbo.mods.darkworld.init.ModBiomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = ModBiomes.class,
       remap = false)
public abstract class ModBiomesMixin {
    @ModifyConstant(method = "init",
                    constant = {@Constant(intValue = 100),
                                @Constant(intValue = 101),
                                @Constant(intValue = 102),
                                @Constant(intValue = 103),
                                @Constant(intValue = 104),
                                @Constant(intValue = 105),
                                @Constant(intValue = 106)},
                    require = 7)
    private static int shiftBiomeIDsUp(int id) {
        return id + 10000;
    }
}
