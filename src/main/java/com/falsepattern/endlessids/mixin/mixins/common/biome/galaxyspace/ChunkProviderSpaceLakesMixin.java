package com.falsepattern.endlessids.mixin.mixins.common.biome.galaxyspace;

import com.falsepattern.endlessids.mixin.helpers.BiomePatchHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

@Pseudo
@Mixin(targets = {"galaxyspace.core.world.gen.ChunkProviderSpaceLakes",
                  "galaxyspace.core.world.ChunkProviderSpaceLakes"},
       remap = false)
public abstract class ChunkProviderSpaceLakesMixin {
    @Shadow
    private BiomeGenBase[] biomesForGeneration;

    @SuppressWarnings("UnresolvedMixinReference")
    @Redirect(method = "func_73154_d",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;func_76605_m()[B"),
              require = 0,
              expect = 0)
    private byte[] setBiomesTweaked(Chunk chunk) {
        return BiomePatchHelper.getBiomeArrayTweaked(chunk, biomesForGeneration);
    }
}
