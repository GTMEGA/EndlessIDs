package com.falsepattern.endlessids.mixin.mixins.common.vanilla.worldgen;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;

@Mixin(GenLayerVoronoiZoom.class)
public abstract class GenLayerVoronoiZoomMixin {
    @ModifyConstant(method = "getInts",
                    constant = @Constant(intValue = VanillaConstants.biomeIDMask),
                    require = 2)
    private int extendTheID(int constant) {
        return ExtendedConstants.biomeIDMask;
    }
}
