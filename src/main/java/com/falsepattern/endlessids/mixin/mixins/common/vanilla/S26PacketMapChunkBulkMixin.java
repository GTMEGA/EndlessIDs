package com.falsepattern.endlessids.mixin.mixins.common.vanilla;

import com.falsepattern.endlessids.config.GeneralConfig;
import io.netty.buffer.ByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;

import java.util.zip.Inflater;

@Mixin(S26PacketMapChunkBulk.class)
public abstract class S26PacketMapChunkBulkMixin {
    @Shadow
    private int[] field_149262_d;

    @Shadow
    private byte[][] field_149260_f;

    @Shadow
    private int[] field_149266_a;

    @Shadow
    private int[] field_149264_b;

    @Shadow
    private int[] field_149265_c;

    @Inject(method = "writePacketData",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/network/PacketBuffer;writeShort(I)Lio/netty/buffer/ByteBuf;",
                     ordinal = 1),
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1)
    private void extendWrite(PacketBuffer p_148840_1_, CallbackInfo ci, int i) {
        if (GeneralConfig.extendBlockItem) {
            p_148840_1_.writeInt(this.field_149265_c[i]);
            p_148840_1_.writeInt(this.field_149262_d[i]);
        } else {
            p_148840_1_.writeShort((short)(this.field_149265_c[i] & 65535));
            p_148840_1_.writeShort((short)(this.field_149262_d[i] & 65535));
        }
        p_148840_1_.writeInt(this.field_149260_f[i].length);
    }

    @Redirect(method = "writePacketData",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/network/PacketBuffer;writeShort(I)Lio/netty/buffer/ByteBuf;",
                       ordinal = 1),
              require = 1)
    private ByteBuf suppressOldWrite1(PacketBuffer instance, int p_writeShort_1_) {
        return null;
    }

    @Redirect(method = "writePacketData",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/network/PacketBuffer;writeShort(I)Lio/netty/buffer/ByteBuf;",
                       ordinal = 2),
              require = 1)
    private ByteBuf suppressOldWrite2(PacketBuffer instance, int p_writeShort_1_) {
        return null;
    }

    @Inject(method = "readPacketData",
            at = @At(value = "INVOKE",
                     target = "Ljava/util/zip/Inflater;inflate([B)I",
                     shift = At.Shift.AFTER),
            require = 1,
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true)
    private void customRead(PacketBuffer data, CallbackInfo ci, short short1, byte[] abyte, Inflater inflater) {
        int i = 0;

        for (int j = 0; j < short1; ++j) {
            this.field_149266_a[j] = data.readInt();
            this.field_149264_b[j] = data.readInt();
            if (GeneralConfig.extendBlockItem) {
                this.field_149265_c[j] = data.readInt();
                this.field_149262_d[j] = data.readInt();
            } else {
                this.field_149265_c[j] = data.readShort();
                this.field_149262_d[j] = data.readShort();
            }
            byte[] dataBytes = new byte[data.readInt()];
            this.field_149260_f[j] = dataBytes;
            System.arraycopy(abyte, i, dataBytes, 0, dataBytes.length);
            i += dataBytes.length;
        }
        ci.cancel();
    }
}
