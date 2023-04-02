package com.falsepattern.endlessids.mixin.mixins.common.biome.restructured;

import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import org.blockartistry.mod.Restructured.world.BiomeHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

@Mixin(value = BiomeHelper.class,
       priority = 999,
       remap = false)
public abstract class BiomeHelperMixin {
    /**
     * @author FalsePattern
     * @reason Compat
     */
    @Overwrite
    public static BiomeGenBase chunkBiomeSurvey(World world, Chunk chunk, Random rand) {
        short[] biomes = ((IChunkMixin)chunk).getBiomeShortArray();
        int[] counts = new int[biomes.length];
        int highIndex = BiomeGenBase.plains.biomeID;
        int highCount = -1;

        for(int i = 0; i < 32; ++i) {
            int id = biomes[rand.nextInt(biomes.length)] & 65535;
            if (id != 65535 && ++counts[id] > highCount) {
                highIndex = id;
                highCount = counts[id];
            }
        }

        return BiomeGenBase.getBiome(highIndex);
    }
}
