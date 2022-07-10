package com.falsepattern.endlessids.mixin.helpers;

import lombok.val;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.function.IntFunction;

import static com.falsepattern.endlessids.EndlessIDs.BIOME_ARRAY_PLACEHOLDER;

public class BiomePatchHelper {
    public static byte[] getBiomeArrayTweaked(Chunk chunk, IntFunction<BiomeGenBase> biomesForGeneration) {
        val chunkBiomes = ((IChunkMixin) chunk).getBiomeShortArray();

        for (int i = 0; i < chunkBiomes.length; ++i) {
            chunkBiomes[i] = (short) biomesForGeneration.apply(i).biomeID;
        }
        return BIOME_ARRAY_PLACEHOLDER;
    }

    public static byte[] getBiomeArrayTweaked(Chunk chunk, BiomeGenBase[] biomesForGeneration) {
        val chunkBiomes = ((IChunkMixin) chunk).getBiomeShortArray();

        for (int i = 0; i < chunkBiomes.length; ++i) {
            chunkBiomes[i] = (short) biomesForGeneration[i].biomeID;
        }
        return BIOME_ARRAY_PLACEHOLDER;
    }
}
