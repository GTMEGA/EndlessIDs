package com.falsepattern.endlessids.mixin.mixins.common.biome.twilightforest;

import com.falsepattern.endlessids.PlaceholderBiome;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import twilightforest.biomes.TFBiomeBase;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(value = TFBiomeBase.class,
       remap = false)
public abstract class TFBiomeBaseMixin {
    @ModifyConstant(method = "findNextOpenBiomeId",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 1)
    private static int extendIDs(int constant) {
        return ExtendedConstants.biomeIDCount;
    }

    @Redirect(method = "isConflictAtBiomeID",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/biome/BiomeGenBase;getBiome(I)Lnet/minecraft/world/biome/BiomeGenBase;",
                       remap = true),
              require = 1)
    private static BiomeGenBase ignorePlaceholders(int id) {
        val biome = BiomeGenBase.getBiome(id);
        if (biome instanceof PlaceholderBiome) {
            BiomeGenBase.getBiomeGenArray()[id] = null;
            return null;
        } else {
            return biome;
        }
    }
}
