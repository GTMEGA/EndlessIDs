package com.falsepattern.endlessids.mixin.mixins.client.biome.vanilla;

import com.falsepattern.endlessids.Hooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.world.chunk.Chunk;

import java.util.Iterator;

@Mixin(Chunk.class)
public abstract class ChunkMixin {
    @SuppressWarnings("rawtypes")
    @Inject(method = "fillChunk",
            at = @At(value = "INVOKE",
                     target = "Ljava/lang/System;arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V",
                     ordinal = 0),
            slice = @Slice(from = @At(value = "INVOKE",
                                      target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;clearMSBArray()V")),
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1)
    private void biomeData(byte[] p_76607_1_, int p_76607_2_, int p_76607_3_, boolean p_76607_4_, CallbackInfo ci, Iterator iterator, int k, boolean flag1, int l) {
        Hooks.readBiomeArrayFromPacket((Chunk) (Object) this, p_76607_1_, k);
    }

    @Redirect(method = "fillChunk",
              at = @At(value = "INVOKE",
                       target = "Ljava/lang/System;arraycopy(Ljava/lang/Object;ILjava/lang/Object;II)V",
                       ordinal = 0),
              slice = @Slice(from = @At(value = "INVOKE",
                                        target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;clearMSBArray()V")),
              require = 1)
    private void noCopyBiome(Object src, int srcPos, Object dest, int destPos, int length) {}

    @Redirect(method = "fillChunk",
              at = @At(value = "FIELD",
                       target = "Lnet/minecraft/world/chunk/Chunk;blockBiomeArray:[B",
                       args = "array=length"),
              require = 1)
    private int noBiomeArrayLength(byte[] array) {
        return 0;
    }
}
