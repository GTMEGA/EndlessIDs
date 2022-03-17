package com.falsepattern.endlessids.mixin.mixins.common;

import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Iterator;
import java.util.List;

@Mixin(AnvilChunkLoader.class)
public abstract class AnvilChunkLoaderMixin {

    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    private void writeChunkToNBT(Chunk p_75820_1_, World p_75820_2_, NBTTagCompound p_75820_3_) {
        p_75820_3_.setByte("V", (byte)1);
        p_75820_3_.setInteger("xPos", p_75820_1_.xPosition);
        p_75820_3_.setInteger("zPos", p_75820_1_.zPosition);
        p_75820_3_.setLong("LastUpdate", p_75820_2_.getTotalWorldTime());
        p_75820_3_.setIntArray("HeightMap", p_75820_1_.heightMap);
        p_75820_3_.setBoolean("TerrainPopulated", p_75820_1_.isTerrainPopulated);
        p_75820_3_.setBoolean("LightPopulated", p_75820_1_.isLightPopulated);
        p_75820_3_.setLong("InhabitedTime", p_75820_1_.inhabitedTime);
        ExtendedBlockStorage[] var4 = p_75820_1_.getBlockStorageArray();
        NBTTagList var5 = new NBTTagList();
        boolean var6 = !p_75820_2_.provider.hasNoSky;
        ExtendedBlockStorage[] var7 = var4;
        int var8 = var4.length;

        NBTTagCompound var11;
        for(int var9 = 0; var9 < var8; ++var9) {
            ExtendedBlockStorage var10 = var7[var9];
            if (var10 != null) {
                var11 = new NBTTagCompound();
                var11.setByte("Y", (byte)(var10.getYLocation() >> 4 & 255));
                Hooks.writeChunkToNbt(var11, (IExtendedBlockStorageMixin) var10);
                if (var10.getBlockMSBArray() != null) {
                    var11.setByteArray("Add", var10.getBlockMSBArray().data);
                }

                var11.setByteArray("Data", var10.getMetadataArray().data);
                var11.setByteArray("BlockLight", var10.getBlocklightArray().data);
                if (var6) {
                    var11.setByteArray("SkyLight", var10.getSkylightArray().data);
                } else {
                    var11.setByteArray("SkyLight", new byte[var10.getBlocklightArray().data.length]);
                }

                var5.appendTag(var11);
            }
        }

        p_75820_3_.setTag("Sections", var5);
        p_75820_3_.setByteArray("Biomes", p_75820_1_.getBiomeArray());
        p_75820_1_.hasEntities = false;
        NBTTagList var21 = new NBTTagList();

        Iterator var22;
        for(var8 = 0; var8 < p_75820_1_.entityLists.length; ++var8) {
            var22 = p_75820_1_.entityLists[var8].iterator();

            while(var22.hasNext()) {
                Entity var12 = (Entity)var22.next();
                var11 = new NBTTagCompound();
                if (var12.writeToNBTOptional(var11)) {
                    p_75820_1_.hasEntities = true;
                    var21.appendTag(var11);
                }
            }
        }

        p_75820_3_.setTag("Entities", var21);
        NBTTagList var23 = new NBTTagList();
        var22 = p_75820_1_.chunkTileEntityMap.values().iterator();

        while(var22.hasNext()) {
            TileEntity var13 = (TileEntity)var22.next();
            var11 = new NBTTagCompound();
            var13.writeToNBT(var11);
            var23.appendTag(var11);
        }

        p_75820_3_.setTag("TileEntities", var23);
        List var24 = p_75820_2_.getPendingBlockUpdates(p_75820_1_, false);
        if (var24 != null) {
            long var15 = p_75820_2_.getTotalWorldTime();
            NBTTagList var17 = new NBTTagList();
            Iterator var18 = var24.iterator();

            while(var18.hasNext()) {
                NextTickListEntry var19 = (NextTickListEntry)var18.next();
                NBTTagCompound var20 = new NBTTagCompound();
                var20.setInteger("i", Block.getIdFromBlock(var19.func_151351_a()));
                var20.setInteger("x", var19.xCoord);
                var20.setInteger("y", var19.yCoord);
                var20.setInteger("z", var19.zCoord);
                var20.setInteger("t", (int)(var19.scheduledTime - var15));
                var20.setInteger("p", var19.priority);
                var17.appendTag(var20);
            }

            p_75820_3_.setTag("TileTicks", var17);
        }

    }

    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    private Chunk readChunkFromNBT(World p_75823_1_, NBTTagCompound p_75823_2_) {
        int var3 = p_75823_2_.getInteger("xPos");
        int var4 = p_75823_2_.getInteger("zPos");
        Chunk var5 = new Chunk(p_75823_1_, var3, var4);
        var5.heightMap = p_75823_2_.getIntArray("HeightMap");
        var5.isTerrainPopulated = p_75823_2_.getBoolean("TerrainPopulated");
        var5.isLightPopulated = p_75823_2_.getBoolean("LightPopulated");
        var5.inhabitedTime = p_75823_2_.getLong("InhabitedTime");
        NBTTagList var6 = p_75823_2_.getTagList("Sections", 10);
        byte var7 = 16;
        ExtendedBlockStorage[] var8 = new ExtendedBlockStorage[var7];
        boolean var9 = !p_75823_1_.provider.hasNoSky;

        for(int var10 = 0; var10 < var6.tagCount(); ++var10) {
            NBTTagCompound var11 = var6.getCompoundTagAt(var10);
            byte var12 = var11.getByte("Y");
            ExtendedBlockStorage var13 = new ExtendedBlockStorage(var12 << 4, var9);
            Hooks.readChunkFromNbt((IExtendedBlockStorageMixin) var13, var11);
            var13.setBlockMetadataArray(new NibbleArray(var11.getByteArray("Data"), 4));
            var13.setBlocklightArray(new NibbleArray(var11.getByteArray("BlockLight"), 4));
            if (var9) {
                var13.setSkylightArray(new NibbleArray(var11.getByteArray("SkyLight"), 4));
            }

            var13.removeInvalidBlocks();
            var8[var12] = var13;
        }

        var5.setStorageArrays(var8);
        if (p_75823_2_.hasKey("Biomes", 7)) {
            var5.setBiomeArray(p_75823_2_.getByteArray("Biomes"));
        }

        return var5;
    }
}
