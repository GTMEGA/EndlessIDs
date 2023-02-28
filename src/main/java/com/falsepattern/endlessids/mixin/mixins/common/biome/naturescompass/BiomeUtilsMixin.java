package com.falsepattern.endlessids.mixin.mixins.common.biome.naturescompass;

import com.chaosthedude.naturescompass.util.BiomeUtils;
import com.falsepattern.endlessids.PlaceholderBiome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(value = BiomeUtils.class,
       remap = false)
public abstract class BiomeUtilsMixin {
    @Inject(method = "biomeIsBlacklisted",
            at = @At("HEAD"),
            cancellable = true,
            require = 1)
    private static void blockPlaceholders(BiomeGenBase biome, CallbackInfoReturnable<Boolean> cir) {
        if (biome instanceof PlaceholderBiome) {
            cir.setReturnValue(true);
        }
    }
}
