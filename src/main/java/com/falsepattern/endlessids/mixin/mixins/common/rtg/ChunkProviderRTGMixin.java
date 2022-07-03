package com.falsepattern.endlessids.mixin.mixins.common.rtg;

import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import rtg.world.gen.ChunkProviderRTG;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

@Mixin(value = ChunkProviderRTG.class,
       remap = false)
public abstract class ChunkProviderRTGMixin {
    @Shadow
    private BiomeGenBase[] baseBiomesList;

    @Shadow
    private int[] xyinverted;

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              remap = true,
              require = 1)
    private byte[] redictBiomeArrayFillLogic(Chunk chunk) {
        short[] biomes = ((IChunkMixin) chunk).getBiomeShortArray();

        for (int i = 0; i < biomes.length; ++i) {
            biomes[i] = (short) baseBiomesList[xyinverted[i]].biomeID;
        }
        return new byte[0];
    }

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;setBiomeArray([B)V",
                       remap = true),
              remap = true,
              require = 1)
    private void removeUnnecessarySet(Chunk chunk, byte[] bytes) {

    }
}
