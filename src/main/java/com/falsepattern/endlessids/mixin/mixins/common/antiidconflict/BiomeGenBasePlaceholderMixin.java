package com.falsepattern.endlessids.mixin.mixins.common.antiidconflict;

import code.elix_x.coremods.antiidconflict.core.AsmHooks;
import com.falsepattern.endlessids.PlaceholderBiome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(BiomeGenBase.class)
public abstract class BiomeGenBasePlaceholderMixin {
    @Shadow
    @Final
    private static BiomeGenBase[] biomeList;

    @SuppressWarnings({"InvalidInjectorMethodSignature", "UnresolvedMixinReference", "MixinAnnotationTarget"})
    @Redirect(method = "<init>(IZ)V",
              at = @At(value = "INVOKE",
                       target = "Lcode/elix_x/coremods/antiidconflict/core/AsmHooks;getBiomeID(IZ)I",
                       remap = false),
              require = 1)
    private int removePlaceholders(int id, boolean register) {
        if (register) {
            if (biomeList[id] instanceof PlaceholderBiome) {
                biomeList[id] = null;
            }
        }
        return AsmHooks.getBiomeID(id, register);
    }
}
