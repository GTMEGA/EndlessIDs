package com.falsepattern.endlessids.mixin.mixins.common.vanilla.worldgen;

import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(ChunkProviderHell.class)
public abstract class ChunkProviderHellMixin implements IChunkProvider {

    @Shadow private Random hellRNG;

    @Shadow private World worldObj;

    @Shadow public abstract void func_147419_a(int p_147419_1_, int p_147419_2_, Block[] p_147419_3_);

    @Shadow public abstract void replaceBiomeBlocks(int p_147418_1_, int p_147418_2_, Block[] p_147418_3_, byte[] meta, BiomeGenBase[] biomes);

    @Shadow private MapGenBase netherCaveGenerator;

    @Shadow public MapGenNetherBridge genNetherBridge;

    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    public Chunk provideChunk(int x, int z) {
        this.hellRNG.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        Block[] blocks = new Block[32768];
        byte[] metadata = new byte[blocks.length];
        BiomeGenBase[] bgb = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, x * 16, z * 16, 16, 16);
        this.func_147419_a(x, z, blocks);
        this.replaceBiomeBlocks(x, z, blocks, metadata, bgb);
        this.netherCaveGenerator.func_151539_a(this, this.worldObj, x, z, blocks);
        this.genNetherBridge.func_151539_a(this, this.worldObj, x, z, blocks);
        Chunk chunk = new Chunk(this.worldObj, blocks, metadata, x, z);
        short[] biomes = ((IChunkMixin)chunk).getBiomeShortArray();

        for(int i = 0; i < biomes.length; ++i) {
            biomes[i] = (short)bgb[i].biomeID;
        }

        chunk.resetRelightChecks();
        return chunk;
    }
}
