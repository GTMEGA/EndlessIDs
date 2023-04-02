package com.falsepattern.endlessids.mixin.mixins.common.biome.eb;

import enhancedbiomes.world.biomestats.BiomeIDs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = BiomeIDs.class,
       remap = false)
public abstract class BiomeIDsMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = 39),
                    require = 1)
    private static int extendIDs(int id) {
        return id + 6000;
    }
}
