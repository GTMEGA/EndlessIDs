package com.falsepattern.endlessids.mixin.mixins.common.vanilla.networking;

import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
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
                    constant = @Constant(intValue = VanillaConstants.bytesPerChunk),
                    require = 1)
    private static int increasePacketSize(int constant) {
        return ExtendedConstants.bytesPerChunk;
    }

    @ModifyConstant(method = "readPacketData",
                    constant = @Constant(intValue = VanillaConstants.bytesPerEBS),
                    require = 1)
    private int increaseReadSize(int constant) {
        return ExtendedConstants.bytesPerEBS;
    }

    @Redirect(method = "func_149269_a",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLSBArray()[B"),
              require = 1)
    private static byte[] hookGetBlockData(ExtendedBlockStorage instance) {
        return Hooks.getBlockData((IExtendedBlockStorageMixin) instance);
    }
}
