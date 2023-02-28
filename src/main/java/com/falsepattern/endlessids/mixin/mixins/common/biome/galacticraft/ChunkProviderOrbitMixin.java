package com.falsepattern.endlessids.mixin.mixins.common.biome.galacticraft;

import com.falsepattern.endlessids.mixin.helpers.BiomePatchHelper;
import micdoodle8.mods.galacticraft.core.world.gen.BiomeGenBaseOrbit;
import micdoodle8.mods.galacticraft.core.world.gen.ChunkProviderOrbit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.chunk.Chunk;

@Mixin(value = ChunkProviderOrbit.class,
       remap = false)
public abstract class ChunkProviderOrbitMixin {

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              remap = true,
              require = 1)
    private byte[] setBiomesTweaked(Chunk chunk) {
        return BiomePatchHelper.getBiomeArrayTweaked(chunk, (i) -> BiomeGenBaseOrbit.space);
    }
}
