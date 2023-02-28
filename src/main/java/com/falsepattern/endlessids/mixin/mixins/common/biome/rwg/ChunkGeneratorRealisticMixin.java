package com.falsepattern.endlessids.mixin.mixins.common.biome.rwg;

import com.falsepattern.endlessids.mixin.helpers.BiomePatchHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

@Pseudo
@Mixin(targets = "rwg.world.ChunkGeneratorRealistic",
       remap = false)
public abstract class ChunkGeneratorRealisticMixin implements IChunkProvider {
    @Shadow
    private BiomeGenBase[] baseBiomesList;

    @SuppressWarnings("UnresolvedMixinReference")
    @Redirect(method = "func_73154_d",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;func_76605_m()[B"),
              require = 0,
              expect = 0)
    private byte[] setBiomesTweaked(Chunk chunk) {
        return BiomePatchHelper.getBiomeArrayTweaked(chunk, baseBiomesList);
    }
}
