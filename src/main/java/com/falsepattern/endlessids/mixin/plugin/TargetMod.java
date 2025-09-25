/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2025 FalsePattern, The MEGA Team
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the word "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, only version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.falsepattern.endlessids.mixin.plugin;

import com.gtnewhorizon.gtnhmixins.builders.ITargetMod;
import com.gtnewhorizon.gtnhmixins.builders.TargetModBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@RequiredArgsConstructor
public enum TargetMod implements ITargetMod {
    //coremods
    AdvancedRocketry("zmaster587.advancedRocketry.AdvancedRocketry"),
    ArsMagica2("am2.AMCore"),
    AntiIDConflict("code.elix_x.coremods.antiidconflict.AntiIdConflictCore"),
    BlockPhysics("com.bloodnbonesgaming.blockphysics.BlockPhysics"),
    BiomeTweaker("me.superckl.biometweaker.BiomeTweaker"),
    DragonAPI("Reika.DragonAPI.DragonAPICore"),
    Factorization("factorization.coremod.LoadingPlugin"),
    FastCraft("fastcraft.Tweaker"),
    Gadomancy("makeo.gadomancy.common.Gadomancy"),
    GalaxySpace("galaxyspace.GalaxySpace"),
    LordOfTheRings("lotr.common.LOTRMod"),
    RandomThings("lumien.randomthings.RandomThings"),
    SalisArcana("dev.rndmorris.salisarcana.SalisArcana"),
    //regular mods
    AbyssalCraft("com.shinoow.abyssalcraft.AbyssalCraft"),
    AlternateTerrainGeneration("ttftcuts.atg.ATG"),
    AntiqueAtlas("hunternif.mc.atlas.AntiqueAtlasMod"),
    BiomesOPlenty("biomesoplenty.BiomesOPlenty"),
    BiomeWand("com.spacechase0.minecraft.biomewand.BiomeWandMod"),
    BuildCraft("buildcraft.BuildCraftCore"),
    ClimateControl("climateControl.ClimateControl"),
    CoFHLib("cofh.lib.CoFHLibProps"),
    CompactMachines("org.dave.CompactMachines.CompactMachines"),
    DarkWorld("matthbo.mods.darkworld.DarkWorld"),
    DimDoors("StevenDimDoors.mod_pocketDim.mod_pocketDim"),
    Enderlicious("DelirusCrux.Enderlicious.Enderlicious"),
    EnhancedBiomes("enhancedbiomes.EnhancedBiomesMod"),
    Erebus("erebus.Erebus"),
    ExtendedPlanets("ExtendedPlanets.EPMain"),
    ExtraPlanets("com.mjr.extraplanets.ExtraPlanets"),
    ExtraUtilities("com.rwtema.extrautils.ExtraUtilsMod"),
    Futurepack("futurepack.common.FPMain"),
    GalactiCraftCore("micdoodle8.mods.galacticraft.core.GalacticraftCore"),
    Highlands("highlands.Highlands"),
    ImmersiveCavegen("net.tclproject.immersivecavegen.ImmersiveCavegen"),
    IndustrialRevolutionByRedstone("jp.plusplus.ir2.IR2"),
    JustAnotherSpawner("jas.common.JustAnotherSpawner"),
    MatterOverdrive("matteroverdrive.MatterOverdrive"),
    MoreFunQuicksand("MoreFunQuicksandMod.main.MFQM"),
    Mystcraft("com.xcompwiz.mystcraft.Mystcraft"),
    NaturesCompass("com.chaosthedude.naturescompass.NaturesCompass"),
    Netherlicious("DelirusCrux.Netherlicious.Netherlicious"),
    NomadicTents("com.yurtmod.main.NomadicTents"),
    NuclearTech("com.hbm.main.MainRegistry"),
    OldWorldGen("owg.OWG"),
    Ruins("atomicstryker.ruins.common.RuinsMod"),
    RealisticTerrainGeneration("rtg.RTG"),
    RealisticWorldGen("rwg.RWG"),
    Restructured("org.blockartistry.mod.Restructured.Restructured"),
    Tardis("tardis.TardisMod"),
    Thaumcraft("thaumcraft.common.Thaumcraft"),
    TheMistsOfRioV("sheenrox82.RioV.src.base.TheMistsOfRioV"),
    ThutCore("thut.api.ThutCore"),
    Tropicraft("net.tropicraft.Tropicraft"),
    TwilightForest("twilightforest.TwilightForestMod"),
    UndergroundBiomes("exterminatorJeff.undergroundBiomes.common.UndergroundBiomes"),
    Witchery("com.emoniph.witchery.Witchery"),
    WorldEdit("com.sk89q.worldedit.forge.ForgeWorldEdit"),

    ;
    @Getter
    private final TargetModBuilder builder;

    TargetMod(@Language(value = "JAVA",
                        prefix = "import ",
                        suffix = ";") @NotNull String className) {
        this(className, null);
    }

    TargetMod(@Language(value = "JAVA",
                        prefix = "import ",
                        suffix = ";") @NotNull String className, @Nullable Consumer<TargetModBuilder> cfg) {
        builder = new TargetModBuilder();
        builder.setTargetClass(className);
        if (cfg != null) {
            cfg.accept(builder);
        }
    }
}
