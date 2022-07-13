package com.falsepattern.endlessids.mixin.plugin;

import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.ITargetedMod.PredicateHelpers.startsWith;

@RequiredArgsConstructor
public enum TargetedMod implements ITargetedMod {

    ATG("Alternate Terrain Generation", true, startsWith("atg")),
    ANTIQUEATLAS("Antique Atlas", true, startsWith("antiqueatlas")),
    ANTIIDCONFLICT("Anti ID Conflict", true, startsWith("antiidconflict")),
    ATUM("Atum", true, startsWith("atum-")),
    BOP("Biomes O' Plenty", true, startsWith("biomesoplenty-")),
    BIOMEWAND("Biome Wand", true, startsWith("1.7.10-biome-wand-").or(startsWith("biome-wand"))),
    COFHLIB("CoFH Lib", true, startsWith("cofhlib-")),
    DRAGONAPI("DragonAPI", false, startsWith("dragonapi")),
    EREBUS("The Erebus", true, startsWith("theerebus")),
    EXTRAUTILITIES("Extra Utilities", true, startsWith("extrautilities-")),
    GALACTICRAFTCORE("GalactiCraftCore", false, startsWith("galacticraftcore")),
    ICG("Immersive Cavegen", true, startsWith("immersivecavegen")),
    MFQM("More Fun Quicksand Mod", false, startsWith("morefunquicksandmod-")),
    NATURESCOMPASS("Nature's Compass", true, startsWith("naturescompass")),
    RTG("Realistic Terrain Generation", true, startsWith("rtg-")),
    THAUMCRAFT("Thaumcraft", true, startsWith("thaumcraft")),
    TWILIGHTFOREST("Twilight Forest", true, startsWith("twilightforest")),
    UBC("Underground Biomes Constructs", false, startsWith("undergroundbiomesconstructs-")),
    WORLDEDIT("WorldEdit", false, startsWith("worldedit-")),
    ;

    @Getter
    private final String modName;
    @Getter
    private final boolean loadInDevelopment;
    @Getter
    private final Predicate<String> condition;
}
