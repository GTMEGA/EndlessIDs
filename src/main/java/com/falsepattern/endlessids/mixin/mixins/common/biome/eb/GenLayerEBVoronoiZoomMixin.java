package com.falsepattern.endlessids.mixin.mixins.common.biome.eb;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import enhancedbiomes.world.gen.layer.GenLayerEBVoronoiZoom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = GenLayerEBVoronoiZoom.class,
       remap = false)
public abstract class GenLayerEBVoronoiZoomMixin {
    @ModifyConstant(method = "getInts",
                    constant = @Constant(intValue = VanillaConstants.biomeIDMask),
                    remap = true,
                    require = 2)
    private int extendIDs(int constant) {
        return ExtendedConstants.biomeIDMask;
    }
}
