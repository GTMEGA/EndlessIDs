package com.falsepattern.endlessids.mixin.mixins.common.vanilla.worldgen;

import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;

import java.util.Random;

@Mixin(ChunkProviderGenerate.class)
public abstract class ChunkProviderGenerateMixin implements IChunkProvider {
    @Shadow
    private Random rand;
    @Shadow
    private BiomeGenBase[] biomesForGeneration;
    @Shadow
    private World worldObj;
    @Shadow
    private MapGenBase caveGenerator;
    @Shadow
    private MapGenBase ravineGenerator;
    @Shadow
    @Final
    private boolean mapFeaturesEnabled;
    @Shadow
    private MapGenMineshaft mineshaftGenerator;
    @Shadow
    private MapGenVillage villageGenerator;
    @Shadow
    private MapGenStronghold strongholdGenerator;
    @Shadow
    private MapGenScatteredFeature scatteredFeatureGenerator;

    @Shadow
    public abstract void func_147424_a(int p_147424_1_, int p_147424_2_, Block[] p_147424_3_);

    @Shadow
    public abstract void replaceBlocksForBiome(int p_147422_1_, int p_147422_2_, Block[] p_147422_3_, byte[] p_147422_4_, BiomeGenBase[] p_147422_5_);

    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    public Chunk provideChunk(int x, int z) {
        this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
        Block[] blocks = new Block[65536];
        byte[] metadata = new byte[65536];
        this.func_147424_a(x, z, blocks);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager()
                                                .loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16,
                                                                        16);
        this.replaceBlocksForBiome(x, z, blocks, metadata, this.biomesForGeneration);
        this.caveGenerator.func_151539_a(this, this.worldObj, x, z, blocks);
        this.ravineGenerator.func_151539_a(this, this.worldObj, x, z, blocks);
        if (this.mapFeaturesEnabled) {
            this.mineshaftGenerator.func_151539_a(this, this.worldObj, x, z, blocks);
            this.villageGenerator.func_151539_a(this, this.worldObj, x, z, blocks);
            this.strongholdGenerator.func_151539_a(this, this.worldObj, x, z, blocks);
            this.scatteredFeatureGenerator.func_151539_a(this, this.worldObj, x, z, blocks);
        }

        Chunk chunk = new Chunk(this.worldObj, blocks, metadata, x, z);
        short[] biomes = ((IChunkMixin) chunk).getBiomeShortArray();

        for (int i = 0; i < biomes.length; ++i) {
            biomes[i] = (short) this.biomesForGeneration[i].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }
}
