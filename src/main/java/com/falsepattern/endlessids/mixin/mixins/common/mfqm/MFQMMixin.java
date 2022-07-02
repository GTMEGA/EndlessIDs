package com.falsepattern.endlessids.mixin.mixins.common.mfqm;

import MoreFunQuicksandMod.main.MFQM;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = MFQM.class,
       remap = false)
public abstract class MFQMMixin {

    @ModifyConstant(method = "preInit",
                    constant = @Constant(stringValue = "Must be different in range between 20 and 31"),
                    require = 1)
    private String changeConfigText(String constant) {
        return constant +
               " (Notice from EndlessIDs: If you enable the ExtendDataWatcher config option in the endlessids config file, the maximum value will be 127 instead, same for the other datawatcher ids in this file)";
    }

    @ModifyConstant(method = "postInit",
                    constant = {@Constant(intValue = VanillaConstants.maxBlockID), @Constant(intValue = 31999)},
                    require = 2)
    private int extendIDs(int constant) {
        return ExtendedConstants.maxBlockID;
    }
}
