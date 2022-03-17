package com.falsepattern.endlessids.mixin.mixins.common;

import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(S26PacketMapChunkBulk.class)
public abstract class S26PacketMapChunkBulkMixin {
    @ModifyConstant(method = "readPacketData",
                    constant = @Constant(intValue = 0x2000),
                    require = 1)
    private int extend1(int constant) {
        return constant + 0x1000;
    }
}
