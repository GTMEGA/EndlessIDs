package com.falsepattern.endlessids.mixin.mixins.common.biome.eb;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import enhancedbiomes.world.gen.layer.GenLayerArchipelagoEdge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = GenLayerArchipelagoEdge.class,
       remap = false)
public abstract class GenLayerArchipelagoEdgeMixin {
    @ModifyConstant(method = "getInts",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    remap = true,
                    require = 5)
    private int extendIDs(int constant) {
        return ExtendedConstants.biomeIDCount;
    }
}
