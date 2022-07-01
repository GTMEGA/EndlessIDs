package com.falsepattern.endlessids.mixin.plugin;

import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.ITargetedMod.PredicateHelpers.*;

@RequiredArgsConstructor
public enum TargetedMod implements ITargetedMod {

    GALACTICRAFTCORE("GalactiCraftCore", false, startsWith("galacticraftcore")),
    UBC("Underground Biomes Constructs", false, startsWith("undergroundbiomesconstructs-")),
    COFHLIB("CoFH Lib", false, startsWith("cofhlib-")),
    DRAGONAPI("DragonAPI", false, startsWith("dragonapi")),
    MFQM("More Fun Quicksand Mod", false, startsWith("morefunquicksandmod-")),
    WORLDEDIT("WorldEdit", false, startsWith("worldedit-")),
    ;

    @Getter
    private final String modName;
    @Getter
    private final boolean loadInDevelopment;
    @Getter
    private final Predicate<String> condition;
}
