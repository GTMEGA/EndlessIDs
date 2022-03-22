package com.falsepattern.endlessids.mixin.mixins.common.dragonapi;

import Reika.DragonAPI.Extras.BlockProperties;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = BlockProperties.class, remap = false)
public abstract class BlockPropertiesMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = VanillaConstants.blockIDCount),
                    require = 1)
    private static int extendColorArray(int constant) {
        return ExtendedConstants.blockIDCount;
    }
}
