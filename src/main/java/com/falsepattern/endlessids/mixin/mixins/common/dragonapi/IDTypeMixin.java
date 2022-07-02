package com.falsepattern.endlessids.mixin.mixins.common.dragonapi;

import Reika.DragonAPI.Extras.IDType;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(IDType.class)
public abstract class IDTypeMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = 254),
                    require = 1)
    private static int extendBiomeIDs(int constant) {
        return ExtendedConstants.biomeIDCount - 2;
    }
    @ModifyConstant(method = "<clinit>",
                    constant = {@Constant(intValue = 4095), @Constant(intValue = 32767)},
                    require = 2)
    private static int extendBlockItemIDs(int constant) {
        return ExtendedConstants.maxBlockID;
    }
}
