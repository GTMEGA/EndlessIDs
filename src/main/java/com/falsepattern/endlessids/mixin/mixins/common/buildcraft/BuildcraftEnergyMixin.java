package com.falsepattern.endlessids.mixin.mixins.common.buildcraft;

import buildcraft.BuildCraftEnergy;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = BuildCraftEnergy.class,
       remap = false)
public abstract class BuildcraftEnergyMixin {
    @ModifyConstant(method = "findUnusedBiomeID",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 1)
    private int extendIDs(int constant) {
        return ExtendedConstants.biomeIDCount;
    }
}
