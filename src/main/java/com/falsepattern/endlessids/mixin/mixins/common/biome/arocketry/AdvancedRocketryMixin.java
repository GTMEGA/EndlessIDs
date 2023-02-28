package com.falsepattern.endlessids.mixin.mixins.common.biome.arocketry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import zmaster587.advancedRocketry.AdvancedRocketry;

@Mixin(value = AdvancedRocketry.class,
       remap = false)
public abstract class AdvancedRocketryMixin {
    @ModifyConstant(method = "load",
                    constant = {@Constant(intValue = 110, ordinal = 0),
                                @Constant(intValue = 111, ordinal = 0),
                                @Constant(intValue = 112, ordinal = 0),
                                @Constant(intValue = 113, ordinal = 0),
                                @Constant(intValue = 114, ordinal = 0),
                                @Constant(intValue = 115, ordinal = 0),
                                @Constant(intValue = 116, ordinal = 0),
                                @Constant(intValue = 117, ordinal = 0),
                                @Constant(intValue = 118, ordinal = 0),
                                @Constant(intValue = 119, ordinal = 0)},
                    require = 10)
    private static int shiftBiomeIDsUp(int constant) {
        return constant + 9000;
    }

    @ModifyConstant(method = "preInit",
                    constant = @Constant(intValue = -2),
                    require = 1)
    private static int changeDefaultDimID(int constant) {
        return -5613;
    }
}
