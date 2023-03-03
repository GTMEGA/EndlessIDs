package com.falsepattern.endlessids.mixin.mixins.client.blockitem.vanilla;

import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.util.Iterator;

@Mixin(Chunk.class)
public abstract class ChunkMixin {
    //This is needed because the array is not a field, but a local, and local arraylength overriding does not exist
    private static byte[][] fakeArrays;
    private static NibbleArray[] fakeNarrays;
    @Shadow
    private ExtendedBlockStorage[] storageArrays;

    private int ebsData;

    @ModifyVariable(method = "fillChunk",
                    at = @At("HEAD"),
                    ordinal = 0,
                    argsOnly = true)
    private int extractEBSData(int ebs) {
        ebsData = ebs;
        int result = 0;
        for (int i = 0; i < 16; i++) {
            result |= ((ebs >>> (i << 1)) & 0b11) > 0 ? 1 << i : 0;
        }
        return result;
    }

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
        val storageFlag = (p_76607_3_ >>> (l << 1)) & 0b11;
        Hooks.setBlockData((IExtendedBlockStorageMixin) this.storageArrays[l], p_76607_1_, k, storageFlag);
    }

    @Redirect(method = "fillChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLSBArray()[B"),
              require = 1)
    private byte[] fakeLSBArray(ExtendedBlockStorage instance) {
        if (fakeArrays == null) {
            fakeArrays = new byte[4][];
            fakeArrays[0] = new byte[16 * 16 * 16 * 2 / 2];
            fakeArrays[1] = new byte[16 * 16 * 16 * 3 / 2];
            fakeArrays[2] = new byte[16 * 16 * 16 * 4 / 2];
            fakeArrays[3] = new byte[16 * 16 * 16 * 6 / 2];
        }
        return fakeArrays[((IExtendedBlockStorageMixin) instance).getEBSMSBMask()];
    }

    @Redirect(method = "fillChunk",
              at = @At(value = "INVOKE",
                       target = "Ljava/lang/System;arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V",
                       ordinal = 0),
              slice = @Slice(from = @At(value = "INVOKE",
                                        target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLSBArray()[B")),
              require = 1)
    private void noCopy(Object src, int srcPos, Object dest, int destPos, int length) {
    }

    @Inject(method = "fillChunk",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getMetadataArray()Lnet/minecraft/world/chunk/NibbleArray;"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1)
    private void hookSetBlockMeta(byte[] p_76607_1_, int p_76607_2_, int p_76607_3_, boolean p_76607_4_, CallbackInfo ci, Iterator iterator, int k, boolean flag1, int l) {
        val storageFlag = (ebsData >>> (l << 1)) & 0b11;
        Hooks.setBlockMeta((IExtendedBlockStorageMixin) this.storageArrays[l], p_76607_1_, k, storageFlag);
    }

    @Redirect(method = "fillChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getMetadataArray()Lnet/minecraft/world/chunk/NibbleArray;"),
              require = 1)
    private NibbleArray hookSetBlockMetaNoArray(ExtendedBlockStorage instance) {
        if (fakeNarrays == null) {
            fakeNarrays = new NibbleArray[3];
            fakeNarrays[0] = new NibbleArray(new byte[]{(byte)0b01}, 0);
            fakeNarrays[1] = new NibbleArray(new byte[]{(byte)0b10}, 0);
            fakeNarrays[2] = new NibbleArray(new byte[]{(byte)0b100}, 0);
        }
        return fakeNarrays[((IExtendedBlockStorageMixin)instance).getEBSMask() - 1];
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
        return 16 * 16 * 16 * (array[0] & 0xFF) / 2;
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
