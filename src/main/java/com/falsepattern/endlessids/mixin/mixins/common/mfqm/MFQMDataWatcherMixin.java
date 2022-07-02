package com.falsepattern.endlessids.mixin.mixins.common.mfqm;

import MoreFunQuicksandMod.main.MFQM;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = MFQM.class,
       remap = false)
public abstract class MFQMDataWatcherMixin {
    @ModifyConstant(method = "preInit",
                    constant = {@Constant(intValue = VanillaConstants.maxWatchableID,
                                          ordinal = 0), @Constant(intValue = VanillaConstants.maxWatchableID,
                                                                  ordinal = 1),
                                @Constant(intValue = VanillaConstants.maxWatchableID,
                                          ordinal = 2)},
                    require = 3)
    private int extendWatchableIDLimit(int constant) {
        return ExtendedConstants.maxWatchableID;
    }
}
