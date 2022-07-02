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
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.MapGenBase;

import java.util.List;

@Mixin(ChunkProviderFlat.class)
public abstract class ChunkProviderFlatMixin implements IChunkProvider {
    @Shadow
    private World worldObj;

    @Shadow
    @Final
    private Block[] cachedBlockIDs;

    @Shadow
    @Final
    private byte[] cachedBlockMetadata;

    @Shadow
    @Final
    private List structureGenerators;

    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    public Chunk provideChunk(int x, int z) {
        Chunk chunk = new Chunk(this.worldObj, x, z);

        for (int Y = 0; Y < this.cachedBlockIDs.length; ++Y) {
            Block block = this.cachedBlockIDs[Y];
            if (block != null) {
                int ebsID = Y >> 4;
                ExtendedBlockStorage ebs = chunk.getBlockStorageArray()[ebsID];
                if (ebs == null) {
                    ebs = new ExtendedBlockStorage(Y, !this.worldObj.provider.hasNoSky);
                    chunk.getBlockStorageArray()[ebsID] = ebs;
                }

                for (int X = 0; X < 16; ++X) {
                    for (int Z = 0; Z < 16; ++Z) {
                        ebs.func_150818_a(X, Y & 15, Z, block);
                        ebs.setExtBlockMetadata(X, Y & 15, Z, this.cachedBlockMetadata[Y]);
                    }
                }
            }
        }

        chunk.generateSkylightMap();
        BiomeGenBase[] bgb = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, x * 16, z * 16, 16, 16);
        short[] biomeArray = ((IChunkMixin) chunk).getBiomeShortArray();

        for (int i = 0; i < biomeArray.length; ++i) {
            biomeArray[i] = (short) bgb[i].biomeID;
        }

        for (Object o : this.structureGenerators) {
            MapGenBase structureGenerator = (MapGenBase) o;
            structureGenerator.func_151539_a(this, this.worldObj, x, z, null);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

}
