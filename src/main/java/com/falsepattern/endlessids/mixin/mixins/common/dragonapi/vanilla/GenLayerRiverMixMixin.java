package com.falsepattern.endlessids.mixin.mixins.common.dragonapi.vanilla;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.world.gen.layer.GenLayerRiverMix;

@Mixin(GenLayerRiverMix.class)
public abstract class GenLayerRiverMixMixin {
    @ModifyConstant(method = "getInts",
                    constant = @Constant(intValue = VanillaConstants.biomeIDMask),
                    expect = -1)
    private int extendTheID(int constant) {
        return ExtendedConstants.biomeIDMask;
    }
}
