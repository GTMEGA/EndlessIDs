package com.falsepattern.endlessids.mixin.mixins.common.biome.arocketry;

import com.falsepattern.endlessids.mixin.helpers.BiomePatchHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import zmaster587.advancedRocketry.api.AdvancedRocketryBiomes;
import zmaster587.advancedRocketry.world.ChunkProviderSpace;

import net.minecraft.world.chunk.Chunk;

@Mixin(value = ChunkProviderSpace.class,
       remap = false)
public abstract class ChunkProviderSpaceMixin {

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              remap = true,
              require = 1)
    private byte[] setBiomesTweaked(Chunk chunk) {
        return BiomePatchHelper.getBiomeArrayTweaked(chunk, (i) -> AdvancedRocketryBiomes.spaceBiome);
    }
}
