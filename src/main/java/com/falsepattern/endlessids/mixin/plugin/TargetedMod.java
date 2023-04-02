package com.falsepattern.endlessids.mixin.plugin;

import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.ITargetedMod.PredicateHelpers.contains;
import static com.falsepattern.lib.mixin.ITargetedMod.PredicateHelpers.startsWith;

@RequiredArgsConstructor
public enum TargetedMod implements ITargetedMod {
    ABYSSALCRAFT("AbyssalCraft", false, startsWith("abyssalcraft-")),
    AROCKETRY("Advanced Rocketry", false, startsWith("advancedrocketry-")),
    ATG("Alternate Terrain Generation", false, startsWith("atg")),
    ANTIQUEATLAS("Antique Atlas", false, startsWith("antiqueatlas")),
    ANTIIDCONFLICT("Anti ID Conflict", false, startsWith("antiidconflict")),
    BOP("Biomes O' Plenty", false, startsWith("biomesoplenty-")),
    BIOMETWEAKER("Biome Tweaker", false, startsWith("biometweaker-")),
    BIOMEWAND("Biome Wand", false, startsWith("1.7.10-biome-wand-").or(startsWith("biome-wand"))),
    BUILDCRAFT("BuildCraft", false, startsWith("buildcraft-").and(contains("compat").negate())),
    COFHLIB("CoFH Lib", false, startsWith("cofhlib-")),
    COMPACTMACHINES("Compact Machines", false, startsWith("compactmachines-")),
    DARKWORLD("Dark World", false, startsWith("darkworld-")),
    DIMDOORS("Dimensional Doors", false, startsWith("dimensionaldoors-").or(startsWith("dimdoors-"))),
    DRAGONAPI("DragonAPI", false, startsWith("dragonapi")),
    EB("Enhanced Biomes", false, startsWith("Enhanced Biomes").or(startsWith("enhancedbiomes"))),
    ENDERLICIOUS("Enderlicious", false, startsWith("enderlicious-")),
    EREBUS("The Erebus", false, startsWith("theerebus")),
    EXTRAPLANETS("Extra Planets", false, startsWith("extraplanets-")),
    EXTRAUTILITIES("Extra Utilities", false, startsWith("extrautilities-")),
    FUTUREPACK("Futurepack", false, startsWith("[1.7.10]futurepack").or(startsWith("futurepack-"))),
    GALACTICRAFTCORE("GalactiCraftCore", false, startsWith("galacticraftcore")),
    GALAXYSPACE("GalaxySpace", false, startsWith("galaxyspace-")),
    HIGHLANDS("Highlands", false, startsWith("highlands")),
    ICG("Immersive Cavegen", false, startsWith("immersivecavegen")),
    LOTR("LOTR Mod", false, startsWith("lotrmod")),
    MFQM("More Fun Quicksand Mod", false, startsWith("morefunquicksandmod-")),
    NATURESCOMPASS("Nature's Compass", false, startsWith("naturescompass")),
    NETHERLICIOUS("Netherlicious", false, startsWith("netherlicious-")),
    NOMADICTENTS("Nomadic Tents", false, startsWith("nomadictents")),
    RANDOMTHINGS("Random Things", false, startsWith("randomthings-")),
    RESTRUCTURED("Restructured", false, startsWith("restructured-")),
    RTG("Realistic Terrain Generation", false, startsWith("rtg-")),
    RWG("Realistic World Gen", false, startsWith("rwg-")),
    THAUMCRAFT("Thaumcraft", false, startsWith("thaumcraft")),
    TROPICRAFT("Tropicraft", false, startsWith("tropicraft-")),
    TWILIGHTFOREST("Twilight Forest", false, startsWith("twilightforest")),
    UBC("Underground Biomes Constructs", false, startsWith("undergroundbiomesconstructs-")),
    WITCHERY("Witchery", false, startsWith("witchery-")),
    WORLDEDIT("WorldEdit", false, startsWith("worldedit-")),
    ;

    @Getter
    private final String modName;
    @Getter
    private final boolean loadInDevelopment;
    @Getter
    private final Predicate<String> condition;
}
