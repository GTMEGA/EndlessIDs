package com.falsepattern.endlessids.mixin.mixins.common.vanilla.worldgen;

import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderEnd;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;
import java.util.Random;

@Mixin(ChunkProviderEnd.class)
public abstract class ChunkProviderEndMixin {

    @Shadow private Random endRNG;

    @Shadow private BiomeGenBase[] biomesForGeneration;

    @Shadow private World endWorld;

    @Shadow public abstract void func_147420_a(int p_147420_1_, int p_147420_2_, Block[] p_147420_3_, BiomeGenBase[] p_147420_4_);

    @Shadow public abstract void replaceBiomeBlocks(int p_147421_1_, int p_147421_2_, Block[] p_147421_3_, BiomeGenBase[] p_147421_4_, byte[] meta);

    /**
     * @author FalsePattern
     * @reason Direct port from decompiled code
     */
    @Overwrite
    public Chunk provideChunk(int x, int z) {
        this.endRNG.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        Block[] blocks = new Block[32768];
        byte[] metadatas = new byte[blocks.length];
        this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.func_147420_a(x, z, blocks, this.biomesForGeneration);
        this.replaceBiomeBlocks(x, z, blocks, this.biomesForGeneration, metadatas);
        Chunk chunk = new Chunk(this.endWorld, blocks, metadatas, x, z);
        short[] biomes = ((IChunkMixin)chunk).getBiomeShortArray();

        for(int i = 0; i < biomes.length; ++i) {
            biomes[i] = (short)this.biomesForGeneration[i].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }
}
