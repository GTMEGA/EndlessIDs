package com.falsepattern.endlessids.mixin.mixins.common.redstone;

import com.falsepattern.endlessids.config.GeneralConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.world.IBlockAccess;

@Mixin(BlockRedstoneDiode.class)
public abstract class BlockRedstoneDiodeMixin {
    @Inject(method = "func_149904_f",
            at = @At("HEAD"),
            cancellable = true)
    private void bigRedstone(IBlockAccess p_149904_1_, int p_149904_2_, int p_149904_3_, int p_149904_4_, int p_149904_5_, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(GeneralConfig.maxRedstone);
    }
}
