package com.falsepattern.endlessids.mixin.mixins.common.biome.lotr;

import com.falsepattern.endlessids.mixin.helpers.LOTRBiomeVariantStorageShort;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import cpw.mods.fml.common.FMLLog;

@Mixin(value = LOTRWorldChunkManager.class,
       remap = false)
public abstract class LOTRWorldChunkManagerMixin {
    @Shadow
    private World worldObj;

    @Shadow
    public abstract LOTRBiomeVariant[] getBiomeVariants(LOTRBiomeVariant[] variants, int i, int k, int xSize, int zSize);

    /**
     * @author FalsePattern
     * @reason fix stuff
     */
    @Overwrite
    public LOTRBiomeVariant getBiomeVariantAt(int i, int k) {
        short[] variants;
        Chunk chunk = this.worldObj.getChunkFromBlockCoords(i, k);
        if (chunk != null &&
            (variants = LOTRBiomeVariantStorageShort.getChunkBiomeVariants(this.worldObj, chunk)) != null) {
            if (variants.length == 256) {
                int chunkX = i & 0xF;
                int chunkZ = k & 0xF;
                short variantID = variants[chunkX + chunkZ * 16];
                return LOTRBiomeVariant.getVariantForID(variantID);
            }
            FMLLog.severe("Found chunk biome variant array of unexpected length " + variants.length);
        }
        if (!this.worldObj.isRemote) {
            return this.getBiomeVariants(null, i, k, 1, 1)[0];
        }
        return LOTRBiomeVariant.STANDARD;
    }
}
