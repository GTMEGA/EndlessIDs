package com.falsepattern.endlessids.mixin.plugin;

import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.ITargetedMod.PredicateHelpers.startsWith;

@RequiredArgsConstructor
public enum TargetedMod implements ITargetedMod {

    ANTIIDCONFLICT("Anti ID Conflict", true, startsWith("antiidconflict")),
    ATG("Alternate Terrain Generation", true, startsWith("atg")),
    BOP("Biomes O' Plenty", true, startsWith("biomesoplenty-")),
    COFHLIB("CoFH Lib", true, startsWith("cofhlib-")),
    DRAGONAPI("DragonAPI", false, startsWith("dragonapi")),
    GALACTICRAFTCORE("GalactiCraftCore", false, startsWith("galacticraftcore")),
    ICG("Immersive Cavegen", true, startsWith("immersivecavegen")),
    MFQM("More Fun Quicksand Mod", false, startsWith("morefunquicksandmod-")),
    NATURESCOMPASS("Nature's Compass", true, startsWith("naturescompass")),
    RTG("Realistic Terrain Generation", true, startsWith("rtg-")),
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
