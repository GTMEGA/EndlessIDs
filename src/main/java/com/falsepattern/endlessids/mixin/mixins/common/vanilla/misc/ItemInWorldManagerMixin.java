package com.falsepattern.endlessids.mixin.mixins.common.vanilla.misc;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.server.management.ItemInWorldManager;

@Mixin(ItemInWorldManager.class)
public abstract class ItemInWorldManagerMixin {
    @ModifyConstant(method = "tryHarvestBlock",
                    constant = @Constant(intValue = VanillaConstants.bitsPerID,
                                         ordinal = 0),
                    require = 1)
    private int extend1(int constant) {
        return ExtendedConstants.bitsPerID;
    }
}
