package com.falsepattern.endlessids.mixin.mixins.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@SideOnly(Side.CLIENT)
@Mixin(RenderGlobal.class)
public abstract class RenderGlobalMixin {
    @ModifyConstant(method = "playAuxSFX",
                    constant = @Constant(intValue = 4095),
                    require = 1)
    private int extend1(int constant) {
        return 65535;
    }

    @ModifyConstant(method = "playAuxSFX",
                    constant = @Constant(intValue = 12),
                    require = 1)
    private int extend2(int constant) {
        return 16;
    }
}
