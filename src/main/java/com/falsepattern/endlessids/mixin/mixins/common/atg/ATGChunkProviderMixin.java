package com.falsepattern.endlessids.mixin.mixins.common.atg;

import com.falsepattern.endlessids.mixin.helpers.BiomePatchHelper;
import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ttftcuts.atg.ATGChunkProvider;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

@Mixin(value = ATGChunkProvider.class,
       remap = false)
public abstract class ATGChunkProviderMixin {
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
