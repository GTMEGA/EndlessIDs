package com.falsepattern.endlessids.mixin.mixins.common.vanilla.potion.network;

import io.netty.buffer.ByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;

@Mixin(S1EPacketRemoveEntityEffect.class)
public abstract class S1EPacketRemoveEntityEffectMixin {
    @Shadow private int field_149078_b;

    @Redirect(method = "readPacketData",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/network/PacketBuffer;readUnsignedByte()S"),
              require = 1)
    private short dontReadByte(PacketBuffer instance) {
        return 0;
    }

    @Inject(method = "readPacketData",
            at = @At("RETURN"),
            require = 1)
    private void readNewData(PacketBuffer buf, CallbackInfo ci) {
        field_149078_b = buf.readInt();
    }

    @Redirect(method = "writePacketData",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/network/PacketBuffer;writeByte(I)Lio/netty/buffer/ByteBuf;"),
              require = 1)
    private ByteBuf dontWriteByte(PacketBuffer instance, int p_writeByte_1_) {
        return null;
    }

    @Inject(method = "writePacketData",
            at = @At(value = "RETURN"),
            require = 1)
    private void writeNewData(PacketBuffer buf, CallbackInfo ci) {
        buf.writeInt(field_149078_b);
    }
}
