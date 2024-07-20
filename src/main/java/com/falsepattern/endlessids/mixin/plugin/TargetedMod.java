/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2024 FalsePattern
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
    IR3("Industrial Revolution by Redstone Rebooted", false, startsWith("industrialrevolutionbyredstonerebooted-")),
    LOTR("LOTR Mod", false, startsWith("lotrmod")),
    MATTEROVERDRIVE("Matter Overdrive", false, startsWith("matteroverdrive-")),
    MATTERMEGADRIVE("Matter Megadrive", false, startsWith("mattermegadrive-")),
    MFQM("More Fun Quicksand Mod", false, startsWith("morefunquicksandmod-")),
    NATURESCOMPASS("Nature's Compass", false, startsWith("naturescompass")),
    NETHERLICIOUS("Netherlicious", false, startsWith("netherlicious-")),
    NOMADICTENTS("Nomadic Tents", false, startsWith("nomadictents")),
    OWG("Nostalgic world generation", false, startsWith("owg-").or(startsWith("nostalgiagenerator-"))),
    RANDOMTHINGS("Random Things", false, startsWith("randomthings-")),
    RESTRUCTURED("Restructured", false, startsWith("restructured-")),
    RTG("Realistic Terrain Generation", false, startsWith("rtg-")),
    RWG("Realistic World Gen", false, startsWith("rwg-")),
    THAUMCRAFT("Thaumcraft", false, startsWith("thaumcraft")),
    THUTCORE("ThutCore", false, startsWith("thutcore-").or(startsWith("thutmods-"))),
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
