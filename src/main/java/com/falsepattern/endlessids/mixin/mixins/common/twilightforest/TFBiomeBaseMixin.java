package com.falsepattern.endlessids.mixin.mixins.common.twilightforest;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import twilightforest.biomes.TFBiomeBase;

@Mixin(value = TFBiomeBase.class,
       remap = false)
public abstract class TFBiomeBaseMixin {
    @ModifyConstant(method = "findNextOpenBiomeId",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 1)
    private static int extendIDs(int constant) {
        return ExtendedConstants.biomeIDCount;
    }
}
