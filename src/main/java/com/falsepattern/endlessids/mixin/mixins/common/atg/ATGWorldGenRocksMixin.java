package com.falsepattern.endlessids.mixin.mixins.common.atg;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import ttftcuts.atg.feature.ATGWorldGenRocks;

@Mixin(value = ATGWorldGenRocks.class,
       remap = false)
public abstract class ATGWorldGenRocksMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 3)
    private static int extendIDs(int constant) {
        return ExtendedConstants.biomeIDCount;
    }
}
