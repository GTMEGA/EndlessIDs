package com.falsepattern.endlessids.mixin.mixins.common.cofhlib;

import cofh.lib.util.helpers.BlockHelper;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlockHelper.class)
public abstract class BlockHelperMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = {@Constant(intValue = VanillaConstants.blockIDCount), @Constant(intValue = VanillaConstants.blockIDCount / 4)},
                    require = 1)
    private static int extend(int constant) {
        return ExtendedConstants.blockIDCount;
    }
}
