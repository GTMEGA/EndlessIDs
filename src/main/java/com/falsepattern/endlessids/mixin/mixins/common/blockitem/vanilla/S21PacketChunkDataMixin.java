package com.falsepattern.endlessids.mixin.mixins.common.blockitem.vanilla;

import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

@Mixin(S21PacketChunkData.class)
public abstract class S21PacketChunkDataMixin {
    @Redirect(method = "func_149269_a",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockMSBArray()Lnet/minecraft/world/chunk/NibbleArray;",
                       ordinal = 0),
              require = 1)
    private static NibbleArray noMSB(ExtendedBlockStorage instance) {
        return null;
    }

    @Inject(method = "func_149269_a",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLSBArray()[B"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1)
    private static void hookGetBlockData(Chunk p_149269_0_, boolean p_149269_1_, int p_149269_2_, CallbackInfoReturnable<S21PacketChunkData.Extracted> cir, int j, ExtendedBlockStorage[] aextendedblockstorage, int k, S21PacketChunkData.Extracted extracted, byte[] abyte, int l) {
        Hooks.getBlockData((IExtendedBlockStorageMixin) aextendedblockstorage[l], abyte, j);
    }

    private static byte[] fakeArray;
    @Redirect(method = "func_149269_a",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLSBArray()[B"),
              require = 1)
    private static byte[] hookGetBlockDataFakeArray(ExtendedBlockStorage instance) {
        return fakeArray != null ? fakeArray : (fakeArray = new byte[16 * 16 * 16 * ExtendedConstants.bytesPerID]);
    }

    @Redirect(method = "func_149269_a",
              at = @At(value = "INVOKE",
                       target = "Ljava/lang/System;arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V",
                       ordinal = 0),
              slice = @Slice(from = @At(value = "INVOKE",
                                        target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLSBArray()[B")),
              require = 1)
    private static void hookGetBlockDataNoCopy(Object src, int srcPos, Object dest, int destPos, int length) {

    }

    @Inject(method = "func_149269_a",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getMetadataArray()Lnet/minecraft/world/chunk/NibbleArray;"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1)
    private static void hookGetBlockMeta(Chunk p_149269_0_, boolean p_149269_1_, int p_149269_2_, CallbackInfoReturnable<S21PacketChunkData.Extracted> cir, int j, ExtendedBlockStorage[] aextendedblockstorage, int k, S21PacketChunkData.Extracted extracted, byte[] abyte, int l) {
        Hooks.getBlockMeta((IExtendedBlockStorageMixin) aextendedblockstorage[l], abyte, j);
    }

    private static NibbleArray fakeNarray;

    @Redirect(method = "func_149269_a",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getMetadataArray()Lnet/minecraft/world/chunk/NibbleArray;"),
              require = 1)
    private static NibbleArray hookGetBlockMetaNoArray(ExtendedBlockStorage instance) {
        return fakeNarray != null ? fakeNarray : (fakeNarray = new NibbleArray(null, 0));
    }

    @Redirect(method = "func_149269_a",
              at = @At(value = "FIELD",
                       target = "Lnet/minecraft/world/chunk/NibbleArray;data:[B",
                       args = "array=length"),
              slice = @Slice(from = @At(value = "INVOKE",
                                        target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getMetadataArray()Lnet/minecraft/world/chunk/NibbleArray;"),
                             to = @At(value = "INVOKE",
                                      target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlocklightArray()Lnet/minecraft/world/chunk/NibbleArray;")),
              expect = 2,
              require = 2)
    private static int hookGetBlockMetaFakeBytesLength(byte[] array) {
        return 16 * 16 * 16 * (ExtendedConstants.nibblesPerMetadata >>> 1);
    }

    @Redirect(method = "func_149269_a",
              at = @At(value = "INVOKE",
                       target = "Ljava/lang/System;arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V",
                       ordinal = 0),
              slice = @Slice(from = @At(value = "INVOKE",
                                        target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getMetadataArray()Lnet/minecraft/world/chunk/NibbleArray;")),
              require = 1)
    private static void hookGetBlockMetaNoCopy(Object src, int srcPos, Object dest, int destPos, int length) {

    }

    @ModifyConstant(method = "readPacketData",
                    constant = @Constant(intValue = VanillaConstants.bytesPerEBS),
                    require = 1)
    private int increaseReadSize(int constant) {
        return ExtendedConstants.bytesPerEBS;
    }
}
