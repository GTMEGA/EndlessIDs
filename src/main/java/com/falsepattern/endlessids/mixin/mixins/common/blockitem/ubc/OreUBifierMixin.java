package com.falsepattern.endlessids.mixin.mixins.common.blockitem.ubc;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import exterminatorJeff.undergroundBiomes.worldGen.OreUBifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = OreUBifier.class,
       remap = false)
public abstract class OreUBifierMixin {
    @ModifyConstant(method = "renewBlockReplacers",
                    constant = @Constant(intValue = VanillaConstants.blockIDCount),
                    require = 1)
    private int extendReplacerArraySize(int constant) {
        return ExtendedConstants.blockIDCount;
    }
}
