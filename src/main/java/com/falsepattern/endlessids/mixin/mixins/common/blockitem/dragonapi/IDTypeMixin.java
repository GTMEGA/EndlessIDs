package com.falsepattern.endlessids.mixin.mixins.common.blockitem.dragonapi;

import Reika.DragonAPI.Extras.IDType;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = IDType.class,
       remap = false)
public abstract class IDTypeMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = {@Constant(intValue = 4095), @Constant(intValue = 32767)},
                    require = 2)
    private static int extendBlockItemIDs(int constant) {
        return ExtendedConstants.maxBlockID;
    }
}
