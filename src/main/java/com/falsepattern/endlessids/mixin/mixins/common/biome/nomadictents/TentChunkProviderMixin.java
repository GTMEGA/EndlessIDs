package com.falsepattern.endlessids.mixin.mixins.common.biome.nomadictents;

import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import com.yurtmod.dimension.TentChunkProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.Arrays;

@Mixin(value = TentChunkProvider.class,
       remap = false)
public abstract class TentChunkProviderMixin {
    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       remap = true,
                       target = "Lnet/minecraft/world/chunk/Chunk;setBiomeArray([B)V"),
              remap = true,
              require = 1)
    private void setBiomesTweaked(Chunk chunk, byte[] p_76616_1_) {
        short[] biomeMap = new short[256];
        Arrays.fill(biomeMap, (short)BiomeGenBase.ocean.biomeID);
        ((IChunkMixin)chunk).setBiomeShortArray(biomeMap);
    }
}
