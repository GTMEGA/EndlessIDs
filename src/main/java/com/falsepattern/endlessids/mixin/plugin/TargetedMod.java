package com.falsepattern.endlessids.mixin.plugin;

import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.ITargetedMod.PredicateHelpers.*;

@RequiredArgsConstructor
public enum TargetedMod implements ITargetedMod {

    COFHLIB("CoFH Lib", false, startsWith("cofhlib-")),
    DRAGONAPI("DragonAPI", false, startsWith("dragonapi")),
    GALACTICRAFTCORE("GalactiCraftCore", false, startsWith("galacticraftcore")),
    MFQM("More Fun Quicksand Mod", false, startsWith("morefunquicksandmod-")),
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
