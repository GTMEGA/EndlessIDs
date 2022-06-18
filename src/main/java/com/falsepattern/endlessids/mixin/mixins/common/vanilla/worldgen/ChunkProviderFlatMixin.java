package com.falsepattern.endlessids.mixin.mixins.common.vanilla.worldgen;

import com.sk89q.worldedit.world.chunk.Chunk;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkProviderFlat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;

@Mixin(ChunkProviderFlat.class)
public abstract class ChunkProviderFlatMixin {
    @Shadow private World worldObj;

    @Shadow @Final private Block[] cachedBlockIDs;

    @Overwrite
    public Chunk provideChunk(int var1, int var2) {
        Chunk var3 = new Chunk(this.worldObj, var1, var2);

        int var6;
        for(int var4 = 0; var4 < this.cachedBlockIDs.length; ++var4) {
            Block var5 = this.cachedBlockIDs[var4];
            if (var5 != null) {
                var6 = var4 >> 4;
                ExtendedBlockStorage var7 = var3.getBlockStorageArray()[var6];
                if (var7 == null) {
                    var7 = new ExtendedBlockStorage(var4, !this.worldObj.provider.hasNoSky);
                    var3.getBlockStorageArray()[var6] = var7;
                }

                for(int var8 = 0; var8 < 16; ++var8) {
                    for(int var9 = 0; var9 < 16; ++var9) {
                        var7.setExtBlockID(var8, var4 & 15, var9, var5);
                        var7.setExtBlockMetadata(var8, var4 & 15, var9, this.cachedBlockMetadata[var4]);
                    }
                }
            }
        }

        var3.generateSkylightMap();
        BiomeGenBase[] var10 = this.worldObj.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[])null, var1 * 16, var2 * 16, 16, 16);
        short[] var11 = var3.getBiomeShortArray();

        for(var6 = 0; var6 < var11.length; ++var6) {
            var11[var6] = (short)var10[var6].biomeID;
        }

        Iterator var12 = this.structureGenerators.iterator();

        while(var12.hasNext()) {
            MapGenBase var13 = (MapGenBase)var12.next();
            var13.generate(this, this.worldObj, var1, var2, (Block[])null);
        }

        var3.generateSkylightMap();
        return var3;
    }

}
