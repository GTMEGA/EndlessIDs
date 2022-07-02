package com.falsepattern.endlessids.mixin.mixins.common.dragonapi;

import Reika.DragonAPI.Extras.IDType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(IDType.class)
public abstract class IDTypeMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = 254),
                    require = 1)
    private static int extendIDs
}
