package com.falsepattern.endlessids.mixin.mixins.common.biome.lotr;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import lotr.common.LOTRDimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = LOTRDimension.class,
       remap = false)
public abstract class LOTRDimensionMixin {
    @ModifyConstant(method = "<init>",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 1)
    private int extendBiomes(int constant) {
        return ExtendedConstants.biomeIDCount;
    }
}
