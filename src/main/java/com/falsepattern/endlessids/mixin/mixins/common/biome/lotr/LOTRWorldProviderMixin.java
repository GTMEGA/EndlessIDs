package com.falsepattern.endlessids.mixin.mixins.common.biome.lotr;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import lombok.val;
import lotr.common.LOTRDimension;
import lotr.common.world.LOTRWorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import static com.falsepattern.endlessids.constants.ExtendedConstants.biomeIDMask;

@Mixin(LOTRWorldProvider.class)
public abstract class LOTRWorldProviderMixin extends WorldProvider {
    @Shadow public abstract LOTRDimension getLOTRDimension();

    /**
     * @author FalsePattern
     * @reason ID extension
     */
    @Overwrite(remap = false)
    public BiomeGenBase getBiomeGenForCoords(int x, int z) {
        if (worldObj.blockExists(x, 0, z)) {
            val chunk = worldObj.getChunkFromBlockCoords(x, z);
            if (chunk != null) {
                val extendedChunk = (ChunkBiomeHook)chunk;
                val biomeArray = extendedChunk.getBiomeShortArray();
                int chunkX = x & 15;
                int chunkZ = z & 15;
                int biomeID = biomeArray[chunkZ << 4 | chunkX] & biomeIDMask;
                if (biomeID == 255) {
                    val biome = worldChunkMgr.getBiomeGenAt((chunk.xPosition << 4) + chunkX, (chunk.zPosition << 4) + chunkZ);
                    biomeID = biome.biomeID;
                    biomeArray[chunkZ << 4 | chunkX] = (short)(biomeID & biomeIDMask);
                }

                val dim = this.getLOTRDimension();
                return dim.biomeList[biomeID] == null ? dim.biomeList[0] : dim.biomeList[biomeID];
            }
        }

        return this.worldChunkMgr.getBiomeGenAt(x, z);
    }
}
