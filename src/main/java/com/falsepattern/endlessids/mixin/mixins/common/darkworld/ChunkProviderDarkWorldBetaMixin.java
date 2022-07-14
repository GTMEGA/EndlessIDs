package com.falsepattern.endlessids.mixin.mixins.common.darkworld;

import com.falsepattern.endlessids.mixin.helpers.BiomePatchHelper;
import matthbo.mods.darkworld.world.ChunkProviderDarkWorldBeta;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

@Mixin(value = ChunkProviderDarkWorldBeta.class,
       remap = false)
public abstract class ChunkProviderDarkWorldBetaMixin {
    @Shadow
    private BiomeGenBase[] biomesForGeneration;

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              remap = true,
              require = 1)
    private byte[] setBiomesTweaked(Chunk chunk) {
        return BiomePatchHelper.getBiomeArrayTweaked(chunk, biomesForGeneration);
    }
}
