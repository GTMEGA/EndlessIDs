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

@RequiredArgsConstructor
public enum TargetedMod implements ITargetedMod {
    ABYSSALCRAFT("AbyssalCraft", false, contains("abyssalcraft-")),
    AROCKETRY("Advanced Rocketry", false, contains("advancedrocketry-")),
    ATG("Alternate Terrain Generation", false, contains("atg")),
    AM2("Ars Magica 2", false, contains("am2").or(contains("arsmagica2"))),
    ANTIQUEATLAS("Antique Atlas", false, contains("antiqueatlas")),
    ANTIIDCONFLICT("Anti ID Conflict", false, contains("antiidconflict")),
    BOP("Biomes O' Plenty", false, contains("biomesoplenty-")),
    BIOMETWEAKER("Biome Tweaker", false, contains("biometweaker-")),
    BIOMEWAND("Biome Wand", false, contains("biome-wand")),
    BUILDCRAFT("BuildCraft", false, contains("buildcraft-").and(contains("compat").negate())),
    CLIMATECONTROL("Climate Control", false, contains("climatecontrol-")),
    COFHLIB("CoFH Lib", false, contains("cofhlib-")),
    COMPACTMACHINES("Compact Machines", false, contains("compactmachines-")),
    DARKWORLD("Dark World", false, contains("darkworld-")),
    DIMDOORS("Dimensional Doors", false, contains("dimensionaldoors-").or(contains("dimdoors-"))),
    DRAGONAPI("DragonAPI", false, contains("dragonapi")),
    EB("Enhanced Biomes", false, contains("Enhanced Biomes").or(contains("enhancedbiomes"))),
    ENDERLICIOUS("Enderlicious", false, contains("enderlicious-")),
    EREBUS("The Erebus", false, contains("theerebus")),
    EXTRAPLANETS("Extra Planets", false, contains("extraplanets-")),
    EXTRAUTILITIES("Extra Utilities", false, contains("extrautilities-")),
    FUTUREPACK("Futurepack", false, contains("futurepack")),
    GALACTICRAFTCORE("GalactiCraftCore", false, contains("galacticraftcore")),
    GALAXYSPACE("GalaxySpace", false, contains("galaxyspace-")),
    HBM_NTM("Hbm's Nuclear Tech Mod", false, contains("hbm-ntm")),
    HIGHLANDS("Highlands", false, contains("highlands")),
    ICG("Immersive Cavegen", false, contains("immersivecavegen")),
    IR3("Industrial Revolution by Redstone Rebooted", false, contains("industrialrevolutionbyredstonerebooted-")),
    LOTR("LOTR Mod", false, contains("lotrmod")),
    MATTEROVERDRIVE("Matter Overdrive", false, contains("matteroverdrive-")),
    MATTERMEGADRIVE("Matter Megadrive", false, contains("mattermegadrive-")),
    MFQM("More Fun Quicksand Mod", false, contains("morefunquicksandmod-")),
    NATURESCOMPASS("Nature's Compass", false, contains("naturescompass")),
    NETHERLICIOUS("Netherlicious", false, contains("netherlicious-")),
    NOMADICTENTS("Nomadic Tents", false, contains("nomadictents")),
    OWG("Nostalgic world generation", false, contains("owg-").or(contains("nostalgiagenerator-"))),
    RANDOMTHINGS("Random Things", false, contains("randomthings-")),
    RESTRUCTURED("Restructured", false, contains("restructured-")),
    RTG("Realistic Terrain Generation", false, contains("rtg-")),
    RWG("Realistic World Gen", false, contains("rwg-")),
    THAUMCRAFT("Thaumcraft", false, contains("thaumcraft")),
    THUTCORE("ThutCore", false, contains("thutcore-").or(contains("thutmods-"))),
    TROPICRAFT("Tropicraft", false, contains("tropicraft-")),
    TWILIGHTFOREST("Twilight Forest", false, contains("twilightforest")),
    UBC("Underground Biomes Constructs", false, contains("undergroundbiomesconstructs-").or(contains("undergroundbiomes-"))),
    WITCHERY("Witchery", false, contains("witchery-")),
    WORLDEDIT("WorldEdit", false, contains("worldedit-")),
    ;

    @Getter
    private final String modName;
    @Getter
    private final boolean loadInDevelopment;
    @Getter
    private final Predicate<String> condition;
}
