package com.falsepattern.endlessids.mixin.mixins.common.tf;

import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import twilightforest.world.ChunkProviderTwilightForest;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import static com.falsepattern.endlessids.EndlessIDs.BIOME_ARRAY_PLACEHOLDER;

@Mixin(value = ChunkProviderTwilightForest.class,
       remap = false)
public abstract class ChunkProviderTwilightForestMixin {
    @Shadow private BiomeGenBase[] biomesForGeneration;

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B"),
              remap = true,
              require = 1)
    private byte[] bypassBiomeProcessing(Chunk chunk) {
        val chunkBiomes = ((IChunkMixin)chunk).getBiomeShortArray();

        for(int i = 0; i < chunkBiomes.length; ++i) {
            chunkBiomes[i] = (short) this.biomesForGeneration[i].biomeID;
        }
        return BIOME_ARRAY_PLACEHOLDER;
    }
}
