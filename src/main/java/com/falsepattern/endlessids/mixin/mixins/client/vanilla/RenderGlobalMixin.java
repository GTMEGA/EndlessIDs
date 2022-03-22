package com.falsepattern.endlessids.mixin.mixins.client.vanilla;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
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
                    constant = @Constant(intValue = VanillaConstants.blockIDMask),
                    require = 1)
    private int extend1(int constant) {
        return ExtendedConstants.blockIDMask;
    }

    @ModifyConstant(method = "playAuxSFX",
                    constant = @Constant(intValue = VanillaConstants.bitsPerID),
                    require = 1)
    private int extend2(int constant) {
        return ExtendedConstants.bitsPerID;
    }
}
