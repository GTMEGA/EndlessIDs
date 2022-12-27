package com.falsepattern.endlessids.mixin.mixins.common.enderlicious;

import DelirusCrux.Enderlicious.Dimension.EndChunkProvider;
import com.falsepattern.endlessids.mixin.helpers.BiomePatchHelper;
import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import static com.falsepattern.endlessids.EndlessIDs.ZERO_LENGTH_BIOME_ARRAY_PLACEHOLDER;

@Mixin(value = EndChunkProvider.class,
       remap = false)
public abstract class EndChunkProviderMixin {
    @Shadow
    private BiomeGenBase[] biomesForGeneration;

    private int xx;
    private int zz;

    @Inject(method = "provideChunk",
            at = @At("HEAD"),
            require = 1)
    private void grabXZ(int X, int Y, CallbackInfoReturnable<Chunk> cir) {
        xx = X * 16;
        zz = Y * 16;
    }

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              remap = true,
              require = 1)
    private byte[] setBiomesTweaked(Chunk chunk) {
        val chunkBiomes = ((IChunkMixin) chunk).getBiomeShortArray();
        for(int k = 0; k < chunkBiomes.length; ++k) {
            if (Math.abs(xx) < 160 && Math.abs(zz) < 160) {
                chunkBiomes[k] = 9;
            } else {
                chunkBiomes[k] = (short)this.biomesForGeneration[k].biomeID;
            }
        }
        return ZERO_LENGTH_BIOME_ARRAY_PLACEHOLDER;
    }
}
