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
    public Chunk provideChunk(int p_73154_1_, int p_73154_2_) {
        this.endRNG.setSeed((long)p_73154_1_ * 341873128712L + (long)p_73154_2_ * 132897987541L);
        Block[] var3 = new Block[32768];
        byte[] var4 = new byte[var3.length];
        this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
        this.func_147420_a(p_73154_1_, p_73154_2_, var3, this.biomesForGeneration);
        this.replaceBiomeBlocks(p_73154_1_, p_73154_2_, var3, this.biomesForGeneration, var4);
        Chunk var5 = new Chunk(this.endWorld, var3, var4, p_73154_1_, p_73154_2_);
        @SuppressWarnings("ConstantConditions") //Mixin moment
        short[] var6 = ((IChunkMixin)var5).getBiomeShortArray();

        for(int var7 = 0; var7 < var6.length; ++var7) {
            var6[var7] = (short)this.biomesForGeneration[var7].biomeID;
        }

        var5.generateSkylightMap();
        return var5;
    }
}
