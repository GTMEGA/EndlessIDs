package com.falsepattern.endlessids.mixin.mixins.common.biome.tropicraft;

import com.falsepattern.endlessids.constants.VanillaConstants;
import net.tropicraft.world.genlayer.GenLayerTropiVoronoiZoom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = GenLayerTropiVoronoiZoom.class,
       remap = false)
public abstract class GenLayerTropiVoronoiZoomMixin {
    @ModifyConstant(method = "getInts",
                    constant = @Constant(intValue = VanillaConstants.biomeIDMask),
                    remap = true,
                    require = 1)
    private int fixStupidBitMask(int constant) {
        return 0xFFFFFFFF;
    }
}
