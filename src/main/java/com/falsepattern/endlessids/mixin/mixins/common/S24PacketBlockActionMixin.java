package com.falsepattern.endlessids.mixin.mixins.common;

import net.minecraft.network.play.server.S24PacketBlockAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(S24PacketBlockAction.class)
public abstract class S24PacketBlockActionMixin {
    @ModifyConstant(method = {"readPacketData", "writePacketData"},
                    constant = @Constant(intValue = 4095),
                    require = 2)
    private int extend1(int constant) {
        return 65535;
    }
}
