package com.falsepattern.endlessids.mixin.mixins.common.biome.rtg;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import rtg.world.gen.LandscapeGenerator;

@Mixin(value = LandscapeGenerator.class,
       remap = false)
public abstract class LandscapeGeneratorMixin {
    @ModifyConstant(method = "getNewerNoise",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 1)
    private int extendIDs(int constant) {
        return ExtendedConstants.biomeIDCount;
    }
}
