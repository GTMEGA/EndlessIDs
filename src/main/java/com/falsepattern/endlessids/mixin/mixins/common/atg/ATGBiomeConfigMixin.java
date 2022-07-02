package com.falsepattern.endlessids.mixin.mixins.common.atg;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import ttftcuts.atg.config.configfiles.ATGBiomeConfig;

@Mixin(value = ATGBiomeConfig.class,
       remap = false)
public abstract class ATGBiomeConfigMixin {
    @ModifyConstant(method = {"<clinit>", "postInit"},
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 4)
    private static int extendIDs(int constant) {
        return ExtendedConstants.biomeIDCount;
    }
}
