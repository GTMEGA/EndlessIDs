package com.falsepattern.endlessids.mixin.mixins.common.biome.dimdoors;

import StevenDimDoors.mod_pocketDim.world.DDBiomeGenBase;
import com.falsepattern.endlessids.PlaceholderBiome;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(DDBiomeGenBase.class)
public abstract class DDBiomeGenBaseMixin extends BiomeGenBase {
    private DDBiomeGenBaseMixin(int p_i1971_1_) {
        super(p_i1971_1_);
    }

    /**
     * @author FalsePattern
     * @reason Compat patch
     */
    @Overwrite
    public static void checkBiomes(int[] biomes) {
        val arr = getBiomeGenArray();
        for(int k = 0; k < biomes.length; ++k) {
            val b = arr[biomes[k]];
            if (b != null && !(b instanceof DDBiomeGenBase || b instanceof PlaceholderBiome)) {
                throw new IllegalStateException("There is a biome ID conflict between a biome from Dimensional Doors and another biome type. Fix your configuration!");
            }
        }
    }
}
