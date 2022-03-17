package com.falsepattern.endlessids.mixin.mixins.common;

import com.falsepattern.endlessids.asm.Constants;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PlayerControllerMP.class)
public abstract class PlayerControllerMPMixin {
    @ModifyConstant(method = "onPlayerDestroyBlock",
                    constant = @Constant(intValue = VanillaConstants.bitsPerID, ordinal = 0),
                    require = 1)
    private int extend1(int constant) {
        return ExtendedConstants.bitsPerID;
    }
}
