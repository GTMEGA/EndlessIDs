package com.falsepattern.endlessids.mixin.mixins.common.arocketry;

import com.falsepattern.endlessids.mixin.helpers.BiomePatchHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import zmaster587.advancedRocketry.world.ChunkProviderCavePlanet;
import zmaster587.advancedRocketry.world.ChunkProviderPlanet;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

@Mixin(value = ChunkProviderCavePlanet.class,
       remap = false)
public abstract class ChunkProviderCavePlanetMixin {
    private BiomeGenBase[] bgb;

    @Inject(method = "provideChunk",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                     shift = At.Shift.BEFORE,
                     remap = true),
            remap = true,
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1)
    private void setBiomesTweaked1(int p_73154_1_, int p_73154_2_, CallbackInfoReturnable<Chunk> cir, ChunkProviderPlanet.BlockMetacoupling ablock, BiomeGenBase[] abiomegenbase, Chunk chunk) {
        bgb = abiomegenbase;
    }

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              remap = true,
              require = 1)
    private byte[] setBiomesTweaked2(Chunk chunk) {
        try {
            return BiomePatchHelper.getBiomeArrayTweaked(chunk, bgb);
        } finally {
            bgb = null;
        }
    }
}
