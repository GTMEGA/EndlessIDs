package com.falsepattern.endlessids.mixin.mixins.common.potion.vanilla.network;

import com.falsepattern.endlessids.mixin.helpers.IS1DPacketEntityEffectMixin;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.netty.buffer.ByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;

@Mixin(value = S1DPacketEntityEffect.class)
public abstract class S1DPacketEntityEffectMixin implements IS1DPacketEntityEffectMixin {
    private int idExtended;

    @WrapOperation(method = "<init>(ILnet/minecraft/potion/PotionEffect;)V",
                   at = @At(value = "INVOKE",
                            target = "Lnet/minecraft/potion/PotionEffect;getPotionID()I"),
                   require = 1)
    private int getPotionID(PotionEffect effect, Operation<Integer> original) {
        idExtended = original.call(effect);
        return -1;
    }

    @Redirect(method = "readPacketData",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/network/PacketBuffer;readByte()B",
                       ordinal = 0),
              require = 1)
    private byte networkReadPacketData(PacketBuffer instance) {
        idExtended = instance.readInt();
        return -1;
    }

    @Redirect(method = "writePacketData",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/network/PacketBuffer;writeByte(I)Lio/netty/buffer/ByteBuf;",
                       ordinal = 0),
              require = 1)
    private ByteBuf networkWritePacketData(PacketBuffer instance, int p_writeByte_1_) {
        instance.writeInt(idExtended);
        return null;
    }

    @Override
    public int getIDExtended() {
        return this.idExtended;
    }
}
