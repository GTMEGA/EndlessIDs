package com.falsepattern.endlessids.mixin.mixins.common.biome.rtg;

import com.falsepattern.endlessids.mixin.helpers.BiomePatchHelper;
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
    private byte[] setBiomesTweaked(Chunk chunk) {
        return BiomePatchHelper.getBiomeArrayTweaked(chunk, (i) -> baseBiomesList[xyinverted[i]]);
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
