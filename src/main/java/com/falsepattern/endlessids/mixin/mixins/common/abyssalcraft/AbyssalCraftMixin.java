package com.falsepattern.endlessids.mixin.mixins.common.abyssalcraft;

import com.falsepattern.endlessids.PlaceholderBiome;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.shinoow.abyssalcraft.AbyssalCraft;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(value = AbyssalCraft.class,
       remap = false)
public abstract class AbyssalCraftMixin {
    @Shadow
    public static int configBiomeId1;
    @Shadow
    public static int configBiomeId2;
    @Shadow
    public static int configBiomeId3;
    @Shadow
    public static int configBiomeId4;
    @Shadow
    public static int configBiomeId5;
    @Shadow
    public static int configBiomeId6;
    @Shadow
    public static int configBiomeId7;
    @Shadow
    public static int configBiomeId8;
    @Shadow
    public static int configBiomeId9;
    @Shadow
    public static int configBiomeId10;
    @Shadow
    public static int configBiomeId11;
    @Shadow
    public static int configBiomeId12;
    @Shadow
    public static int configBiomeId13;

    @Inject(method = "checkBiomeIds",
            at = @At("HEAD"),
            require = 1)
    private void cleanupBiomeArrayForAssignment(CallbackInfo ci) {
        val bga = BiomeGenBase.getBiomeGenArray();

        int[] biomes = new int[]{configBiomeId1, configBiomeId2, configBiomeId3, configBiomeId4, configBiomeId5,
                                 configBiomeId6, configBiomeId7, configBiomeId8, configBiomeId9, configBiomeId10,
                                 configBiomeId11, configBiomeId12, configBiomeId13};
        for (val biome : biomes) {
            if (biome < 0 || biome >= ExtendedConstants.biomeIDCount) {
                continue;
            }
            if (bga[biome] instanceof PlaceholderBiome) {
                bga[biome] = null;
            }
        }
    }
}
