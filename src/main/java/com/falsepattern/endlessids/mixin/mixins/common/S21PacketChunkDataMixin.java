package com.falsepattern.endlessids.mixin.mixins.common;

import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(S21PacketChunkData.class)
public abstract class S21PacketChunkDataMixin {
    @ModifyConstant(method = {"<clinit>", "func_149275_c"},
                    constant = @Constant(intValue = 0x30100),
                    require = 1)
    private static int increasePacketSize(int constant) {
        return constant + 0x8000;
    }

    @ModifyConstant(method = "readPacketData",
                    constant = @Constant(intValue = 0x3000),
                    require = 1)
    private int increaseReadSize(int constant) {
        return constant + 0x800;
    }

    @Redirect(method = "func_149269_a",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLSBArray()[B"),
              require = 1)
    private static byte[] hookGetBlockData(ExtendedBlockStorage instance) {
        return Hooks.getBlockData((IExtendedBlockStorageMixin) instance);
    }
}
