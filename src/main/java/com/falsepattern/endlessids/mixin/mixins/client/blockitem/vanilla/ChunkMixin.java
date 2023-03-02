package com.falsepattern.endlessids.mixin.mixins.client.blockitem.vanilla;

import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.util.Iterator;

@Mixin(Chunk.class)
public abstract class ChunkMixin {
    @Shadow
    private ExtendedBlockStorage[] storageArrays;

    //This is needed because the array is not a field, but a local, and local arraylength overriding does not exist
    private static byte[] fakeArray;

    @Redirect(method = "fillChunk",
              at = @At(value = "FIELD",
                       target = "Lnet/minecraft/world/chunk/Chunk;storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;",
                       args = "array=length",
                       ordinal = 0),
              slice = @Slice(from = @At(value = "INVOKE",
                                target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getSkylightArray()Lnet/minecraft/world/chunk/NibbleArray;")),
              require = 1)
    private int noMSBLoop(ExtendedBlockStorage[] array) {
        return 0;
    }

    @SuppressWarnings("rawtypes")
    @Inject(method = "fillChunk",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLSBArray()[B"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1)
    private void blockData(byte[] p_76607_1_, int p_76607_2_, int p_76607_3_, boolean p_76607_4_, CallbackInfo ci, Iterator iterator, int k, boolean flag1, int l) {
        Hooks.setBlockData((IExtendedBlockStorageMixin) this.storageArrays[l], p_76607_1_, k);
    }

    @Redirect(method = "fillChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLSBArray()[B"),
              require = 1)
    private byte[] fakeLSBArray(ExtendedBlockStorage instance) {
        return fakeArray != null ? fakeArray : (fakeArray = new byte[16 * 16 * 16 * ExtendedConstants.bytesPerID]);
    }

    @Redirect(method = "fillChunk",
              at = @At(value = "INVOKE",
                       target = "Ljava/lang/System;arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V",
                       ordinal = 0),
              slice = @Slice(from = @At(value = "INVOKE",
                                        target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLSBArray()[B")),
              require = 1)
    private void noCopy(Object src, int srcPos, Object dest, int destPos, int length) {}



    @Inject(method = "fillChunk",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getMetadataArray()Lnet/minecraft/world/chunk/NibbleArray;"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1)
    private void hookSetBlockMeta(byte[] p_76607_1_, int p_76607_2_, int p_76607_3_, boolean p_76607_4_, CallbackInfo ci, Iterator iterator, int k, boolean flag1, int l) {
        Hooks.setBlockMeta((IExtendedBlockStorageMixin) this.storageArrays[l], p_76607_1_, k);
    }

    private static NibbleArray fakeNarray;

    @Redirect(method = "fillChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getMetadataArray()Lnet/minecraft/world/chunk/NibbleArray;"),
              require = 1)
    private NibbleArray hookSetBlockMetaNoArray(ExtendedBlockStorage instance) {
        return fakeNarray != null ? fakeNarray : (fakeNarray = new NibbleArray(null, 0));
    }

    @Redirect(method = "fillChunk",
              at = @At(value = "FIELD",
                       target = "Lnet/minecraft/world/chunk/NibbleArray;data:[B",
                       args = "array=length"),
              slice = @Slice(from = @At(value = "INVOKE",
                                        target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getMetadataArray()Lnet/minecraft/world/chunk/NibbleArray;"),
                             to = @At(value = "INVOKE",
                                      target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlocklightArray()Lnet/minecraft/world/chunk/NibbleArray;")),
              expect = 2,
              require = 2)
    private int hookSetBlockMetaFakeBytesLength(byte[] array) {
        return 16 * 16 * 16 * (ExtendedConstants.nibblesPerMetadata >>> 1);
    }

    @Redirect(method = "fillChunk",
              at = @At(value = "INVOKE",
                       target = "Ljava/lang/System;arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V",
                       ordinal = 0),
              slice = @Slice(from = @At(value = "INVOKE",
                                        target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getMetadataArray()Lnet/minecraft/world/chunk/NibbleArray;")),
              require = 1)
    private void hookSetBlockMetaNoCopy(Object src, int srcPos, Object dest, int destPos, int length) {

    }
}
