package com.falsepattern.endlessids.mixin.mixins.common.bop;

import biomesoplenty.common.core.BOPBiomes;
import biomesoplenty.common.world.BOPBiomeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(value = BOPBiomes.class,
       remap = false)
public abstract class BOPBiomesMixin {
    /**
     * @author FalsePattern
     * @reason Suppress NPE
     */
    @Overwrite
    private static void disableRiver(BiomeGenBase biome) {
        try {
            BOPBiomeManager.overworldRiverBiomes[biome.biomeID] = biome;
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
