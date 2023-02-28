package com.falsepattern.endlessids.mixin.mixins.common.biome.dragonapi;

import Reika.DragonAPI.Extras.IDType;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = IDType.class,
       remap = false)
public abstract class IDTypeMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = 254),
                    require = 1)
    private static int extendBiomeIDs(int constant) {
        return ExtendedConstants.biomeIDCount - 2;
    }
}
