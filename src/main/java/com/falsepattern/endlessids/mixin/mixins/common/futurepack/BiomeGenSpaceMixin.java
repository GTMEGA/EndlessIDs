package com.falsepattern.endlessids.mixin.mixins.common.futurepack;

import futurepack.common.dim.BiomeGenSpace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BiomeGenSpace.class)
public abstract class BiomeGenSpaceMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = 97),
                    require = 1)
    private static int shiftBiomeIDsUp(int id) {
        return id + 11000;
    }
}
