package com.falsepattern.endlessids.mixin.mixins.common.vanilla.enchantment;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.enchantment.Enchantment;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = 256),
                    require = 1)
    private static int extendEnchantmentIDRange(int original) {
        return ExtendedConstants.maximumEnchantmentIDs;
    }
}
