package com.falsepattern.endlessids;

import net.minecraft.world.biome.BiomeGenBase;

public class PlaceholderBiome extends BiomeGenBase {
    public PlaceholderBiome(int id, BiomeGenBase parent) {
        super(id);
        biomeName = "EndlessIDs ID conflict avoidance placeholder for ID " + parent.biomeID +
                    " [" + parent.getClass().getName() + "]";
    }


}
