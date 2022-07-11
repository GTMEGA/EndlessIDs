package com.falsepattern.endlessids.mixin.mixins.common.extrautilities;

import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import com.rwtema.extrautils.worldgen.endoftime.ChunkProviderEndOfTime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.Arrays;

@Mixin(value = ChunkProviderEndOfTime.class,
       remap = false)
public abstract class ChunkProviderEndOfTimeMixin {
    private int id;
    private Chunk chunk;

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              remap = true,
              require = 1)
    private byte[] setBiomesTweaked1(Chunk instance) {
        chunk = instance;
        return EndlessIDs.BIOME_ARRAY_PLACEHOLDER;
    }

    @Redirect(method = "provideChunk",
              at = @At(value = "FIELD",
                       target = "Lnet/minecraft/world/biome/BiomeGenBase;biomeID:I",
                       remap = true),
              remap = true)
    private int setBiomesTweaked2(BiomeGenBase instance) {
        return id = instance.biomeID;
    }

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Ljava/util/Arrays;fill([BB)V"),
              remap = true,
              require = 1)
    private void setBiomesTweaked3(byte[] bytes, byte b) {
        try {
            Arrays.fill(((IChunkMixin) chunk).getBiomeShortArray(), (short) id);
        } finally {
            chunk = null;
            id = -1;
        }
    }
}
