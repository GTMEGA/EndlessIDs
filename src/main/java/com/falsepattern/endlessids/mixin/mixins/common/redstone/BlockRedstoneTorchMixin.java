package com.falsepattern.endlessids.mixin.mixins.common.redstone;

import com.falsepattern.endlessids.config.GeneralConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.block.BlockRedstoneTorch;

@Mixin(BlockRedstoneTorch.class)
public abstract class BlockRedstoneTorchMixin {
    @ModifyConstant(method = "isProvidingWeakPower",
                    constant = @Constant(intValue = 15),
                    require = 1)
    private int bigRedstone(int original) {
        return GeneralConfig.maxRedstone;
    }
}
