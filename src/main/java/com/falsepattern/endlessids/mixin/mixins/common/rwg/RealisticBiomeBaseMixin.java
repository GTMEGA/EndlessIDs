package com.falsepattern.endlessids.mixin.mixins.common.rwg;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Pseudo
@Mixin(targets = "rwg.biomes.realistic.RealisticBiomeBase",
       remap = false)
public abstract class RealisticBiomeBaseMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 1)
    private static int extendBiomeIDCount(int constant) {
        return ExtendedConstants.biomeIDCount;
    }
}
