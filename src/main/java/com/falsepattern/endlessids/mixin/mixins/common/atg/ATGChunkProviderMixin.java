package com.falsepattern.endlessids.mixin.mixins.common.atg;

import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import ttftcuts.atg.ATGChunkProvider;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

@Mixin(value = ATGChunkProvider.class,
       remap = false)
public abstract class ATGChunkProviderMixin {
    @Shadow private BiomeGenBase[] biomesForGeneration;

    @Inject(method = "provideChunk",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                     shift = At.Shift.BEFORE),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1)
    private void hijackChunkBiomeSetup(int chunkX, int chunkY, CallbackInfoReturnable<Chunk> cir, Block[] data, byte[] abyte, Chunk chunk) {
        val chunkBiomes = ((IChunkMixin)chunk).getBiomeShortArray();
        for (int i = 0; i < chunkBiomes.length; i++) {
            chunkBiomes[i] = (short) biomesForGeneration[i].biomeID;
        }
        chunk.generateSkylightMap();
        cir.setReturnValue(chunk);
    }
}
