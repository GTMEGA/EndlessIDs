package com.falsepattern.endlessids.mixin.mixins.common.vanilla.biome;

import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.PlaceholderBiome;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Arrays;

@Mixin(value = BiomeDictionary.class,
       remap = false)
public abstract class BiomeDictionaryMixin {
    @Inject(method = "registerAllBiomesAndGenerateEvents",
            at = @At("HEAD"),
            require = 1)
    private static void cleanupBiomeArray(CallbackInfo ci) {
        val biomes = BiomeGenBase.getBiomeGenArray();
        for (int i = 0; i < biomes.length; i++) {
            if (biomes[i] instanceof PlaceholderBiome) {
                biomes[i] = null;
            }
        }
    }
}
