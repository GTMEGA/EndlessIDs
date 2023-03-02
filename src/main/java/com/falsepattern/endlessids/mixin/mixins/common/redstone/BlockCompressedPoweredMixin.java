package com.falsepattern.endlessids.mixin.mixins.common.redstone;

import com.falsepattern.endlessids.config.GeneralConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockCompressedPowered;
import net.minecraft.world.IBlockAccess;

@Mixin(BlockCompressedPowered.class)
public abstract class BlockCompressedPoweredMixin {
    @Inject(method = "isProvidingWeakPower",
            at = @At("HEAD"),
            cancellable = true,
            require = 1)
    private void bigRedstone(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(GeneralConfig.maxRedstone);
    }
}
