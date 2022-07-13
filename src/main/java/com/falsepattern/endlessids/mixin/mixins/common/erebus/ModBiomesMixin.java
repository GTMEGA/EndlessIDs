package com.falsepattern.endlessids.mixin.mixins.common.erebus;

import com.falsepattern.endlessids.PlaceholderBiome;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import erebus.ModBiomes;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(value = ModBiomes.class,
       remap = false)
public abstract class ModBiomesMixin {
    @Shadow
    public static int undergroundJungleID;
    @Shadow
    public static int volcanicDesertID;
    @Shadow
    public static int subterraneanSavannahID;
    @Shadow
    public static int elysianFieldsID;
    @Shadow
    public static int ulteriorOutbackID;
    @Shadow
    public static int fungalForestID;
    @Shadow
    public static int submergedSwampID;
    @Shadow
    public static int fieldsSubForestID;
    @Shadow
    public static int jungleSubLakeID;
    @Shadow
    public static int jungleSubAsperGroveID;
    @Shadow
    public static int desertSubCharredForestID;
    @Shadow
    public static int savannahSubRockyWastelandID;
    @Shadow
    public static int savannahSubAsperGroveID;
    @Shadow
    public static int savannahSubSteppeID;

    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 128),
                    require = 1)
    private static int extendIDs(int constant) {
        return ExtendedConstants.biomeIDCount - 129;
    }

    @ModifyConstant(method = "init",
                    constant = @Constant(stringValue = "Erebus biome IDs cannot be higher than 127 or smaller than 0!"),
                    require = 1)
    private static String modifyWarning(String str) {
        return "Erebus biome IDs cannot be higher than " + (ExtendedConstants.biomeIDCount - 129) + " or smaller than 0!";
    }

    @Inject(method = "init",
            at = @At("HEAD"),
            require = 1)
    private static void cleanupBiomeArrayForAssignment(CallbackInfo ci) {
        val bga = BiomeGenBase.getBiomeGenArray();

        int[] biomes = new int[]{undergroundJungleID, volcanicDesertID, subterraneanSavannahID, elysianFieldsID,
                                 ulteriorOutbackID, fungalForestID, submergedSwampID, fieldsSubForestID,
                                 jungleSubLakeID, jungleSubAsperGroveID, desertSubCharredForestID,
                                 savannahSubRockyWastelandID, savannahSubAsperGroveID, savannahSubSteppeID};
        for (val biome: biomes) {
            if (biome <= 0 || biome >= ExtendedConstants.biomeIDCount - 129) {
                continue;
            }
            if (bga[biome] instanceof PlaceholderBiome) {
                bga[biome] = null;
            }
        }
    }
}
