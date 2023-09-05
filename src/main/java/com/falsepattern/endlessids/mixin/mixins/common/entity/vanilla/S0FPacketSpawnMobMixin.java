package com.falsepattern.endlessids.mixin.mixins.common.entity.vanilla;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import io.netty.buffer.ByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S0FPacketSpawnMob;

@Mixin(S0FPacketSpawnMob.class)
public abstract class S0FPacketSpawnMobMixin {
    @Shadow private int field_149040_b;

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityLivingBase;)V",
            at = @At("RETURN"),
            require = 1)
    private void unClampType(EntityLivingBase entity, CallbackInfo ci) {
        field_149040_b = EntityList.getEntityID(entity);
    }

    @Redirect(method = "readPacketData",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/network/PacketBuffer;readByte()B",
                       ordinal = 0),
              require = 1)
    private byte noReadByte(PacketBuffer data) {
        return 0;
    }

    @Inject(method = "readPacketData",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/network/PacketBuffer;readByte()B",
                     ordinal = 0),
            require = 1)
    private void readType(PacketBuffer data, CallbackInfo ci) {
        field_149040_b = data.readShort() & ExtendedConstants.entityIDMask;
    }

    @Redirect(method = "writePacketData",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/network/PacketBuffer;writeByte(I)Lio/netty/buffer/ByteBuf;",
                       ordinal = 0),
              require = 1)
    private ByteBuf noWriteByte(PacketBuffer data, int theByte) {
        return null;
    }

    @Inject(method = "writePacketData",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/network/PacketBuffer;writeByte(I)Lio/netty/buffer/ByteBuf;",
                       ordinal = 0),
            require = 1)
    private void writeType(PacketBuffer data, CallbackInfo ci) {
        data.writeShort(field_149040_b & ExtendedConstants.entityIDMask);
    }
}
