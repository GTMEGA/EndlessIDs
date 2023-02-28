package com.falsepattern.endlessids.mixin.mixins.common.biome.dragonapi;

import Reika.DragonAPI.Instantiable.Event.GenLayerRiverEvent;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = GenLayerRiverEvent.class,
       remap = false)
public abstract class GenLayerRiverEventMixin {
    @ModifyConstant(method = "fire_1614",
                    constant = @Constant(intValue = VanillaConstants.biomeIDMask),
                    require = 2)
    private static int increaseBiomeMask(int constant) {
        return ExtendedConstants.biomeIDMask;
    }
}
