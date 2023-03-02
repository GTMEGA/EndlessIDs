package com.falsepattern.endlessids.mixin.mixins.client.redstone;

import com.falsepattern.endlessids.config.GeneralConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.client.renderer.RenderBlocks;

@Mixin(RenderBlocks.class)
public abstract class RenderBlocksMixin {
    @ModifyConstant(method = "renderBlockRedstoneWire",
                    constant = @Constant(floatValue = 15.0F),
                    require = 1)
    private float bigRedstone(float constant) {
        return GeneralConfig.maxRedstone;
    }
}
