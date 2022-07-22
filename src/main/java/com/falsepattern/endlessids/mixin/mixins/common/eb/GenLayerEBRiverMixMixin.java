package com.falsepattern.endlessids.mixin.mixins.common.eb;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import enhancedbiomes.world.gen.layer.GenLayerEBRiverMix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = GenLayerEBRiverMix.class,
       remap = false)
public abstract class GenLayerEBRiverMixMixin {
    @ModifyConstant(method = "getInts",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    remap = true,
                    require = 1)
    private int extendIDs1(int constant) {
        return ExtendedConstants.biomeIDCount;
    }

    @ModifyConstant(method = "getInts",
                    constant = @Constant(intValue = VanillaConstants.biomeIDMask),
                    remap = true,
                    require = 1)
    private int extendIDs2(int constant) {
        return ExtendedConstants.biomeIDMask;
    }
}
