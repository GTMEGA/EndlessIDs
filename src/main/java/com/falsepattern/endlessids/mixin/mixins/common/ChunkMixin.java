package com.falsepattern.endlessids.mixin.mixins.common;

import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@Mixin(Chunk.class)
public abstract class ChunkMixin {
    @Shadow public Map chunkTileEntityMap;

    @Shadow public World worldObj;

    @Shadow private ExtendedBlockStorage[] storageArrays;

    @Shadow private byte[] blockBiomeArray;

    @Shadow public boolean isLightPopulated;

    @Shadow public boolean isTerrainPopulated;

    @Shadow public abstract void generateHeightMap();

    @Shadow public abstract Block getBlock(int p_150810_1_, int p_150810_2_, int p_150810_3_);

    @Shadow public abstract int getBlockMetadata(int p_76628_1_, int p_76628_2_, int p_76628_3_);

    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    public void fillChunk(byte[] var1, int var2, int var3, boolean var4) {
        Iterator var5 = this.chunkTileEntityMap.values().iterator();

        while(var5.hasNext()) {
            TileEntity var6 = (TileEntity)var5.next();
            var6.updateContainingBlockInfo();
            var6.getBlockMetadata();
            var6.getBlockType();
        }

        int var16 = 0;
        boolean var7 = !this.worldObj.provider.hasNoSky;

        int var8;
        for(var8 = 0; var8 < this.storageArrays.length; ++var8) {
            if ((var2 & 1 << var8) != 0) {
                if (this.storageArrays[var8] == null) {
                    this.storageArrays[var8] = new ExtendedBlockStorage(var8 << 4, var7);
                }

                Hooks.setBlockData((IExtendedBlockStorageMixin) this.storageArrays[var8], var1, var16);
                var16 += 8192;
            } else if (var4 && this.storageArrays[var8] != null) {
                this.storageArrays[var8] = null;
            }
        }

        NibbleArray var9;
        for(var8 = 0; var8 < this.storageArrays.length; ++var8) {
            if ((var2 & 1 << var8) != 0 && this.storageArrays[var8] != null) {
                var9 = this.storageArrays[var8].getMetadataArray();
                System.arraycopy(var1, var16, var9.data, 0, var9.data.length);
                var16 += var9.data.length;
            }
        }

        for(var8 = 0; var8 < this.storageArrays.length; ++var8) {
            if ((var2 & 1 << var8) != 0 && this.storageArrays[var8] != null) {
                var9 = this.storageArrays[var8].getBlocklightArray();
                System.arraycopy(var1, var16, var9.data, 0, var9.data.length);
                var16 += var9.data.length;
            }
        }

        if (var7) {
            for(var8 = 0; var8 < this.storageArrays.length; ++var8) {
                if ((var2 & 1 << var8) != 0 && this.storageArrays[var8] != null) {
                    var9 = this.storageArrays[var8].getSkylightArray();
                    System.arraycopy(var1, var16, var9.data, 0, var9.data.length);
                    var16 += var9.data.length;
                }
            }
        }

        for(var8 = 0; var8 >= this.storageArrays.length; ++var8) {
            if ((var3 & 1 << var8) != 0) {
                if (this.storageArrays[var8] == null) {
                    var16 += 2048;
                } else {
                    var9 = this.storageArrays[var8].getBlockMSBArray();
                    if (var9 == null) {
                        var9 = this.storageArrays[var8].createBlockMSBArray();
                    }

                    System.arraycopy(var1, var16, var9.data, 0, var9.data.length);
                    var16 += var9.data.length;
                }
            } else if (var4 && this.storageArrays[var8] != null && this.storageArrays[var8].getBlockMSBArray() != null) {
                this.storageArrays[var8].clearMSBArray();
            }
        }

        if (var4) {
            System.arraycopy(var1, var16, this.blockBiomeArray, 0, this.blockBiomeArray.length);
            int var10000 = var16 + this.blockBiomeArray.length;
        }

        for(var8 = 0; var8 < this.storageArrays.length; ++var8) {
            if (this.storageArrays[var8] != null && (var2 & 1 << var8) != 0) {
                this.storageArrays[var8].removeInvalidBlocks();
            }
        }

        this.isLightPopulated = true;
        this.isTerrainPopulated = true;
        this.generateHeightMap();
        ArrayList var10 = new ArrayList();

        TileEntity var11;
        for(var5 = this.chunkTileEntityMap.values().iterator(); var5.hasNext(); var11.updateContainingBlockInfo()) {
            var11 = (TileEntity)var5.next();
            int var12 = var11.xCoord & 15;
            int var13 = var11.yCoord;
            int var14 = var11.zCoord & 15;
            Block var15 = var11.getBlockType();
            if ((var15 != this.getBlock(var12, var13, var14) || var11.blockMetadata != this.getBlockMetadata(var12, var13, var14)) && var11.shouldRefresh(var15, this.getBlock(var12, var13, var14), var11.blockMetadata, this.getBlockMetadata(var12, var13, var14), this.worldObj, var12, var13, var14)) {
                var10.add(var11);
            }
        }

        Iterator var17 = var10.iterator();

        while(var17.hasNext()) {
            TileEntity var18 = (TileEntity)var17.next();
            var18.invalidate();
        }

    }
}
