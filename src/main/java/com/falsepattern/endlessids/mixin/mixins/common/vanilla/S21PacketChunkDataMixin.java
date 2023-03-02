package com.falsepattern.endlessids.mixin.mixins.common.vanilla;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import io.netty.buffer.ByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S21PacketChunkData;

import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@Mixin(S21PacketChunkData.class)
public abstract class S21PacketChunkDataMixin {
    @Shadow
    private static byte[] field_149286_i;
    @Shadow
    private int field_149280_d;
    @Shadow
    private byte[] field_149278_f;
    @Shadow
    private int field_149284_a;
    @Shadow
    private int field_149282_b;
    @Shadow
    private boolean field_149279_g;
    @Shadow
    private int field_149283_c;
    @Shadow
    private int field_149285_h;

    @ModifyConstant(method = {"<clinit>", "func_149275_c"},
                    constant = @Constant(intValue = VanillaConstants.bytesPerChunk),
                    require = 1)
    private static int increasePacketSize(int constant) {
        return ExtendedConstants.bytesPerChunk;
    }


    @Inject(method = "writePacketData",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/network/PacketBuffer;writeShort(I)Lio/netty/buffer/ByteBuf;",
                     ordinal = 1),
            require = 1)
    private void extendWrite(PacketBuffer p_148840_1_, CallbackInfo ci) {
        p_148840_1_.writeInt(this.field_149280_d);
        p_148840_1_.writeInt(this.field_149278_f.length);
    }

    @Redirect(method = "writePacketData",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/network/PacketBuffer;writeShort(I)Lio/netty/buffer/ByteBuf;",
                       ordinal = 1),
              require = 1)
    private ByteBuf suppressOldWrite(PacketBuffer instance, int p_writeShort_1_) {
        return null;
    }

    @Inject(method = "readPacketData",
            at = @At("HEAD"),
            require = 1,
            cancellable = true)
    private void customRead(PacketBuffer data, CallbackInfo ci) throws IOException {
        this.field_149284_a = data.readInt();
        this.field_149282_b = data.readInt();
        this.field_149279_g = data.readBoolean();
        this.field_149283_c = data.readShort();
        this.field_149280_d = data.readInt();
        int length = data.readInt();
        this.field_149285_h = data.readInt();

        if (field_149286_i.length < this.field_149285_h) {
            field_149286_i = new byte[this.field_149285_h];
        }

        data.readBytes(field_149286_i, 0, this.field_149285_h);

        this.field_149278_f = new byte[length];
        Inflater inflater = new Inflater();
        inflater.setInput(field_149286_i, 0, this.field_149285_h);

        try {
            inflater.inflate(this.field_149278_f);
        } catch (DataFormatException dataformatexception) {
            throw new IOException("Bad compressed data format");
        } finally {
            inflater.end();
        }
        ci.cancel();
    }
}
