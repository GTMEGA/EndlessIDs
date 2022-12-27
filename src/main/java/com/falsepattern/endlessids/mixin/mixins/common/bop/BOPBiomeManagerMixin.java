package com.falsepattern.endlessids.mixin.mixins.common.bop;

import biomesoplenty.common.world.BOPBiomeManager;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = BOPBiomeManager.class,
       remap = false)
public abstract class BOPBiomeManagerMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = 40),
                    require = 1)
    private static int shiftBiomeIDsUp(int id) {
        return id + 5000;
    }

    @ModifyConstant(method = "getNextFreeBiomeId",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 1)
    private static int extendIDs1(int constant) {
        return ExtendedConstants.biomeIDCount;
    }

    @ModifyConstant(method = "getNextFreeBiomeId",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount - 1),
                    require = 1)
    private static int extendIDs2(int constant) {
        return ExtendedConstants.biomeIDCount - 1;
    }
}
