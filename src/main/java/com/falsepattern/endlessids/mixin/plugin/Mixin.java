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

import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.lib.mixin.v2.MixinHelper;
import com.falsepattern.lib.mixin.v2.SidedMixins;
import com.falsepattern.lib.mixin.v2.TaggedMod;
import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import static com.falsepattern.endlessids.mixin.plugin.TargetMod.AbyssalCraft;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.AdvancedRocketry;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.AlternateTerrainGeneration;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.AntiIDConflict;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.AntiqueAtlas;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.ArsMagica2;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.BiomeTweaker;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.BiomeWand;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.BiomesOPlenty;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.BlockPhysics;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.BuildCraft;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.ClimateControl;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.CoFHLib;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.CompactMachines;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.DarkWorld;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.DimDoors;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.DragonAPI;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.Enderlicious;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.EnhancedBiomes;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.Erebus;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.ExtendedPlanets;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.ExtraPlanets;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.ExtraUtilities;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.Factorization;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.FastCraft;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.Futurepack;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.Gadomancy;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.GalactiCraftCore;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.GalaxySpace;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.Highlands;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.ImmersiveCavegen;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.IndustrialRevolutionByRedstone;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.JustAnotherSpawner;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.LordOfTheRings;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.MatterOverdrive;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.MoreFunQuicksand;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.Mystcraft;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.NaturesCompass;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.Netherlicious;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.NomadicTents;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.NuclearTech;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.OldWorldGen;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.RandomThings;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.RealisticTerrainGeneration;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.RealisticWorldGen;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.Restructured;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.Ruins;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.SalisArcana;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.Tardis;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.Thaumcraft;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.TheMistsOfRioV;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.ThutCore;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.Tropicraft;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.TwilightForest;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.UndergroundBiomes;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.Witchery;
import static com.falsepattern.endlessids.mixin.plugin.TargetMod.WorldEdit;
import static com.falsepattern.lib.mixin.v2.MixinHelper.avoid;
import static com.falsepattern.lib.mixin.v2.MixinHelper.builder;
import static com.falsepattern.lib.mixin.v2.MixinHelper.mods;
import static com.falsepattern.lib.mixin.v2.MixinHelper.require;

@SuppressWarnings("UnstableApiUsage")
@RequiredArgsConstructor
public enum Mixin implements IMixins {
    // @formatter:off
    AntiIDConflict_Compat(Phase.EARLY,
                          require(AntiIDConflict),
                          common("antiidconflict.AntiIdConflictBaseMixin",
                                 "antiidconflict.BiomeGenBasePlaceholderMixin",
                                 "antiidconflict.BiomesManagerMixin",
                                 "antiidconflict.DimensionsManagerMixin",
                                 "antiidconflict.EnchantementsManagerMixin",
                                 "antiidconflict.EntitiesManagerMixin",
                                 "antiidconflict.PotionsManagerMixin")),

    // region Biome

    Biome_Vanilla(Phase.EARLY,
                  Ext.Biome,
                  common("biome.vanilla.BiomeDictionaryMixin",
                         "biome.vanilla.BiomeGenBaseMixin",
                         "biome.vanilla.ChunkMixin",
                         "biome.vanilla.GenLayerRiverMixMixin",
                         "biome.vanilla.GenLayerVoronoiZoomMixin")),

    Biome_Vanilla_Placeholders(Phase.EARLY,
                               () -> GeneralConfig.extendBiome && GeneralConfig.biomeConflictAvoidancePlaceholders,
                               common("biome.vanilla.BiomeGenBasePlaceholderMixin")),

    Biome_AbyssalCraft(Phase.LATE,
                       Ext.Biome,
                       require(AbyssalCraft),
                       common("biome.abyssalcraft.AbyssalCraftMixin")),

    Biome_AdvancedRocketry(Phase.EARLY,
                           Ext.Biome,
                           require(AdvancedRocketry),
                           common("biome.arocketry.AdvancedRocketryMixin",
                                  "biome.arocketry.ChunkProviderSpaceMixin")),

    Biome_AntiqueAtlas(Phase.LATE,
                       Ext.Biome,
                       require(AntiqueAtlas),
                       common("biome.antiqueatlas.BiomeDetectorBaseMixin",
                              "biome.antiqueatlas.BiomeDetectorNetherMixin")),

    Biome_AlternateTerrainGeneration(Phase.LATE,
                                     Ext.Biome,
                                     require(AlternateTerrainGeneration),
                                     common("biome.atg.ATGBiomeConfigMixin",
                                            "biome.atg.ATGBiomeManagerMixin",
                                            "biome.atg.ATGWorldGenRocksMixin")),

    Biome_BiomeTweaker(Phase.EARLY,
                       Ext.Biome,
                       require(BiomeTweaker),
                       common("biome.biometweaker.BiomeEventHandlerMixin")),

    Biome_BiomeWand(Phase.EARLY,
                    Ext.Biome,
                    require(BiomeWand),
                    common("biome.biomewand.BiomeWandItemMixin"),
                    client("biome.biomewand.BiomeWandItemMixin")),

    Biome_BiomesOPlenty(Phase.LATE,
                        Ext.Biome,
                        require(BiomesOPlenty),
                        common("biome.bop.BOPBiomeManagerMixin",
                               "biome.bop.BOPBiomesMixin")),

    Biome_BuildCraft(Phase.LATE,
                     Ext.Biome,
                     require(BuildCraft),
                     common("biome.buildcraft.BuildcraftEnergyMixin")),

    Biome_ClimateControl(Phase.LATE,
                         Ext.Biome,
                         require(ClimateControl),
                         common("biome.climatecontrol.api.BiomeSettingsMixin",
                                "biome.climatecontrol.api.ClimateControlRulesMixin",
                                "biome.climatecontrol.customGenLayer.ConfirmBiomeMixin",
                                "biome.climatecontrol.customGenLayer.GenLayerBiomeByClimateMixin",
                                "biome.climatecontrol.customGenLayer.GenLayerBiomeByTaggedClimateMixin",
                                "biome.climatecontrol.customGenLayer.GenLayerDefineClimateMixin",
                                "biome.climatecontrol.customGenLayer.GenLayerLowlandRiverMixMixin",
                                "biome.climatecontrol.customGenLayer.GenLayerSmoothCoastMixin",
                                "biome.climatecontrol.customGenLayer.GenLayerSubBiomeMixin",
                                "biome.climatecontrol.generator.AbstractWorldGeneratorMixin",
                                "biome.climatecontrol.generator.BiomeSwapperMixin",
                                "biome.climatecontrol.generator.SubBiomeChooserMixin",
                                "biome.climatecontrol.genLayerPack.GenLayerHillsMixin",
                                "biome.climatecontrol.genLayerPack.GenLayerPackMixin",
                                "biome.climatecontrol.genLayerPack.GenLayerSmoothMixin",
                                "biome.climatecontrol.genLayerPack.GenLayerVoronoiZoomMixin",
                                "biome.climatecontrol.genLayerPack.GenLayerZoomMixin")),

    Biome_CompactMachines(Phase.LATE,
                          Ext.Biome,
                          require(CompactMachines),
                          common("biome.compactmachines.CubeToolsMixin")),

    Biome_DarkWorld(Phase.LATE,
                    Ext.Biome,
                    require(DarkWorld),
                    common("biome.darkworld.ModBiomesMixin")),

    Biome_DimDoors(Phase.LATE,
                   Ext.Biome,
                   require(DimDoors),
                   common("biome.dimdoors.DDBiomeGenBaseMixin")),

    Biome_DragonAPI(Phase.EARLY,
                    Ext.Biome,
                    require(DragonAPI),
                    common("biome.dragonapi.vanilla.GenLayerRiverMixMixin",
                           "biome.dragonapi.GenLayerRiverEventMixin",
                           "biome.dragonapi.IDTypeMixin",
                           "biome.dragonapi.ReikaChunkHelperMixin",
                           "biome.dragonapi.ReikaWorldHelperMixin")),

    Biome_EnhancedBiomes(Phase.LATE,
                         Ext.Biome,
                         require(EnhancedBiomes),
                         common("biome.eb.BiomeIDsMixin",
                                "biome.eb.GenLayerArchipelagoEdgeMixin",
                                "biome.eb.GenLayerEBHillsMixin",
                                "biome.eb.GenLayerEBRiverMixMixin",
                                "biome.eb.GenLayerEBVoronoiZoomMixin")),

    Biome_Enderlicious(Phase.LATE,
                       Ext.Biome,
                       require(Enderlicious),
                       common("biome.enderlicious.BiomeConfigurationMixin",
                              "biome.enderlicious.EndChunkProviderMixin")),

    Biome_Erebus(Phase.LATE,
                 Ext.Biome,
                 require(Erebus),
                 common("biome.erebus.ModBiomesMixin")),

    Biome_ExtendedPlanets(Phase.LATE,
                          Ext.Biome,
                          require(ExtendedPlanets),
                          common("biome.extendedplanets.ChunkProviderOceanMixin")),

    Biome_ExtraPlanets(Phase.LATE,
                       Ext.Biome,
                       require(ExtraPlanets),
                       common("biome.extraplanets.ConfigMixin")),

    Biome_ExtraUtilities(Phase.LATE,
                         Ext.Biome,
                         require(ExtraUtilities),
                         common("biome.extrautilities.ChunkProviderEndOfTimeMixin")),

    Biome_Factorization(Phase.EARLY,
                        Ext.Biome,
                        require(Factorization),
                        common("biome.factorization.HammerChunkProviderMixin")),

    Biome_Futurepack(Phase.LATE,
                     Ext.Biome,
                     require(Futurepack),
                     common("biome.futurepack.BiomeGenSpaceMixin")),

    Biome_Gadomancy(Phase.EARLY,
                    Ext.Biome,
                    require(Gadomancy),
                    common("biome.gadomancy.ChunkProviderTCOuterMixin")),

    Biome_GalactiCraft(Phase.LATE,
                       Ext.Biome,
                       require(GalactiCraftCore),
                       common("biome.galacticraft.ChunkProviderOrbitMixin",
                              "biome.galacticraft.ConfigManagerCoreMixin")),

    Biome_GalaxySpace(Phase.EARLY,
                      Ext.Biome,
                      require(GalaxySpace),
                      common("biome.galaxyspace.ChunkProviderKuiperMixin",
                             "biome.galaxyspace.ChunkProviderMarsSSMixin",
                             "biome.galaxyspace.ChunkProviderSpaceLakesMixin",
                             "biome.galaxyspace.ChunkProviderVenusSSMixin")),

    Biome_Highlands(Phase.LATE,
                    Ext.Biome,
                    require(Highlands),
                    common("biome.highlands.ConfigMixin")),

    Biome_ImmersiveCavegen(Phase.LATE,
                           Ext.Biome,
                           require(ImmersiveCavegen),
                           common("biome.icg.MysteriumPatchesFixesCaveMixin")),

    Biome_IndustrialRevolutionByRedstone(Phase.LATE,
                                         Ext.Biome,
                                         require(IndustrialRevolutionByRedstone),
                                         common("biome.ir3.IR2Mixin")),

    Biome_JustAnotherSpawner(Phase.LATE,
                             Ext.Biome,
                             require(JustAnotherSpawner),
                             common("biome.jas.LegacyCreatureTypeMixin",
                                    "biome.jas.ModernCreatureTypeMixin")),

    Biome_LordOfTheRings(Phase.EARLY,
                         Ext.Biome,
                         require(LordOfTheRings),
                         common("biome.lotr.LOTRBiomeVariantStorageMixin",
                                "biome.lotr.LOTRChunkProviderMixin",
                                "biome.lotr.LOTRChunkProviderUtumnoMixin",
                                "biome.lotr.LOTRDimensionMixin",
                                "biome.lotr.LOTRPacketBiomeVariantsWatchHandlerMixin",
                                "biome.lotr.LOTRWorldChunkManagerMixin",
                                "biome.lotr.LOTRWorldProviderMixin")),

    Biome_Mystcraft(Phase.LATE,
                    Ext.Biome,
                    require(Mystcraft),
                    common("biome.mystcraft.BiomeReplacerMixin")),

    Biome_NaturesCompass(Phase.LATE,
                         Ext.Biome,
                         require(NaturesCompass),
                         common("biome.naturescompass.BiomeUtilsMixin")),

    Biome_Netherlicious(Phase.LATE,
                        Ext.Biome,
                        require(Netherlicious),
                        common("biome.netherlicious.BiomeConfigurationMixin")),

    Biome_NomadicTents(Phase.LATE,
                       Ext.Biome,
                       require(NomadicTents),
                       common("biome.nomadictents.TentChunkProviderMixin")),

    Biome_OldWorldGen(Phase.LATE,
                      Ext.Biome,
                      require(OldWorldGen),
                      common("biome.owg.ChunkGeneratorBetaMixin")),

    Biome_RandomThings(Phase.EARLY,
                       Ext.Biome,
                       require(RandomThings),
                       common("biome.randomthings.ItemBiomePainterMixin",
                              "biome.randomthings.MessagePaintBiomeMixin")),

    Biome_RealisticTerrainGeneration(Phase.LATE,
                                     Ext.Biome,
                                     require(RealisticTerrainGeneration),
                                     common("biome.rtg.ChunkProviderRTGMixin",
                                            "biome.rtg.LandscapeGeneratorMixin",
                                            "biome.rtg.WorldChunkManagerRTGMixin")),

    Biome_RealisticWorldGen(Phase.LATE,
                            Ext.Biome,
                            require(RealisticWorldGen),
                            common("biome.rwg.RealisticBiomeBaseMixin",
                                   "biome.rwg.ChunkGeneratorRealisticMixin",
                                   "biome.rwg.VillageMaterialsMixin")),

    Biome_Restructured(Phase.LATE,
                       Ext.Biome,
                       require(Restructured),
                       common("biome.restructured.BiomeHelperMixin")),

    Biome_Ruins(Phase.LATE,
                Ext.Biome,
                require(Ruins),
                common("biome.ruins.FileHandlerMixin",
                       "biome.ruins.LoaderThreadMixin",
                       "biome.ruins.RuinGeneratorMixin")),

    Biome_Tardis(Phase.LATE,
                 Ext.Biome,
                 require(Tardis),
                 common("biome.tardismod.TardisChunkProviderMixin")),

    Biome_Thaumcraft(Phase.LATE,
                     Ext.Biome,
                     require(Thaumcraft),
                     common("biome.thaumcraft.UtilsMixin")),

    Biome_TheMistsOfRioV(Phase.LATE,
                         Ext.Biome,
                         require(TheMistsOfRioV),
                         common("biome.tmor.ChunkProviderSanctuatiteMixin")),

    Biome_ThutCore(Phase.LATE,
                   Ext.Biome,
                   require(ThutCore),
                   common("biome.thut.Vector3Mixin")),

    Biome_Tropicraft(Phase.LATE,
                     Ext.Biome,
                     require(Tropicraft),
                     common("biome.tropicraft.GenLayerTropiVoronoiZoomMixin")),

    Biome_TwilightForest(Phase.LATE,
                         Ext.Biome,
                         require(TwilightForest),
                         common("biome.twilightforest.TFBiomeBaseMixin")),

    Biome_Witchery(Phase.LATE,
                   Ext.Biome,
                   require(Witchery),
                   common("biome.witchery.BrewActionBiomeChangeMixin",
                          "biome.witchery.WorldChunkManagerMirrorMixin")),

    Biome_WorldEdit(Phase.LATE,
                    Ext.Biome,
                    require(WorldEdit),
                    common("biome.worldedit.ForgeWorldMixin")),

    // endregion Biome

    // region BlockItem

    BlockItem_Vanilla(Phase.EARLY,
                      Ext.BlockItem,
                      common("blockitem.vanilla.BlockFireMixin",
                             "blockitem.vanilla.BlockMixin",
                             "blockitem.vanilla.ExtendedBlockStorageMixin",
                             "blockitem.vanilla.ItemInWorldManagerMixin",
                             "blockitem.vanilla.ItemStackMixin",
                             "blockitem.vanilla.PacketBufferMixin",
                             "blockitem.vanilla.S22PacketMultiBlockChangeMixin",
                             "blockitem.vanilla.S24PacketBlockActionMixin",
                             "blockitem.vanilla.StatListMixin",
                             "blockitem.vanilla.WorldMixin"),
                      client("blockitem.vanilla.PlayerControllerMPMixin",
                             "blockitem.vanilla.RenderGlobalMixin")),

    BlockItem_BlockPhysics(Phase.EARLY,
                           Ext.BlockItem,
                           require(BlockPhysics),
                           common("blockitem.blockphysics.DefinitionMapsMixin")),

    BlockItem_CoFHLib(Phase.LATE,
                      Ext.BlockItem,
                      require(CoFHLib),
                      common("blockitem.cofhlib.BlockHelperMixin")),

    BlockItem_DragonAPI(Phase.EARLY,
                        Ext.BlockItem,
                        require(DragonAPI),
                        common("blockitem.dragonapi.BlockPropertiesMixin",
                               "blockitem.dragonapi.IDTypeMixin")),

    BlockItem_FastCraft(Phase.EARLY,
                        Ext.BlockItem,
                        require(FastCraft),
                        client("blockitem.fastcraft.EntityRendererMixin")),

    BlockItem_NuclearTech(Phase.LATE,
                          Ext.BlockItem,
                          require(NuclearTech),
                          common("blockitem.ntm.ChunkRadiationHandlerPRISMMixin",
                                 "blockitem.ntm.ChunkRadiationHandlerPRISMSubChunkMixin")),

    BlockItem_MatterOverdrive(Phase.LATE,
                              Ext.BlockItem,
                              require(MatterOverdrive),
                              common("blockitem.matteroverdrive.InventoryMixin",
                                     "blockitem.matteroverdrive.ItemPatternMixin")),

    BlockItem_MoreFunQuicksand(Phase.LATE,
                               Ext.BlockItem,
                               require(MoreFunQuicksand),
                               common("blockitem.mfqm.MFQMMixin")),

    BlockItem_UndergroundBiomes(Phase.LATE,
                                Ext.BlockItem,
                                require(UndergroundBiomes),
                                common("blockitem.ubc.BiomeUndergroundDecoratorMixin",
                                       "blockitem.ubc.OreUBifierMixin")),

    BlockItem_WorldEdit(Phase.LATE,
                        Ext.BlockItem,
                        require(WorldEdit),
                        common("blockitem.worldedit.BaseBlockMixin")),

    // endregion BlockItem

    // region DataWatcher

    DataWatcher_Vanilla(Phase.EARLY,
                        Ext.DataWatcher,
                        common("datawatcher.vanilla.DataWatcherMixin")),

    DataWatcher_MoreFunQuicksand(Phase.LATE,
                                 Ext.DataWatcher,
                                 common("datawatcher.mfqm.MFQMDataWatcherMixin")),

    // endregion DataWatcher

    // region Enchantment

    Enchantment_Vanilla(Phase.EARLY,
                        Ext.Enchantment,
                        common("enchantment.vanilla.EnchantmentMixin")),

    // endregion Enchantment

    // region Entity

    Entity_Vanilla(Phase.EARLY,
                   Ext.Entity,
                   common("entity.vanilla.EntityListMixin",
                          "entity.vanilla.EntityRegistryMixin",
                          "entity.vanilla.S0FPacketSpawnMobMixin")),

    // endregion Entity

    // region Potion

    Potion_Vanilla(Phase.EARLY,
                   Ext.Potion,
                   common("potion.vanilla.PotionEffectMixin",
                          "potion.vanilla.PotionMixin")),

    Potion_Vanilla_NoArsMagica2(Phase.EARLY,
                                Ext.Potion,
                                avoid(ArsMagica2),
                                common("potion.vanilla.network.S1DPacketEntityEffectMixin",
                                       "potion.vanilla.network.S1EPacketRemoveEntityEffectMixin"),
                                client("potion.vanilla.NetHandlerPlayClientMixin")),

    Potion_Thaumcraft(Phase.LATE,
                      Ext.Potion,
                      mods(require(Thaumcraft), avoid(SalisArcana)),
                      common("potion.thaumcraft.ConfigMixin")),

    // endregion Potion

    // region Redstone

    Redstone_Vanilla(Phase.EARLY,
                     () -> GeneralConfig.extendBlockItem && GeneralConfig.extendRedstone,
                     common("redstone.BlockCompressedPoweredMixin",
                            "redstone.BlockRedstoneDiodeMixin",
                            "redstone.BlockRedstoneTorchMixin",
                            "redstone.WorldMixin"),
                     client("redstone.BlockRedstoneWireMixin",
                            "redstone.RenderBlocksMixin")),

    // endregion Redstone

    // @formatter:on

    //region boilerplate
    ;
    @Getter
    private final MixinBuilder builder;

    Mixin(Phase phase, SidedMixins... mixins) {
        this(builder(mixins).setPhase(phase));
    }

    Mixin(Phase phase, BooleanSupplier cond, SidedMixins... mixins) {
        this(builder(cond, mixins).setPhase(phase));
    }

    Mixin(Phase phase, TaggedMod mod, SidedMixins... mixins) {
        this(builder(mod, mixins).setPhase(phase));
    }

    Mixin(Phase phase, TaggedMod[] mods, SidedMixins... mixins) {
        this(builder(mods, mixins).setPhase(phase));
    }

    Mixin(Phase phase, BooleanSupplier cond, TaggedMod mod, SidedMixins... mixins) {
        this(builder(cond, mod, mixins).setPhase(phase));
    }

    Mixin(Phase phase, BooleanSupplier cond, TaggedMod[] mods, SidedMixins... mixins) {
        this(builder(cond, mods, mixins).setPhase(phase));
    }

    private static SidedMixins common(@Language(value = "JAVA",
                                                prefix = "import " + Tags.GROUPNAME + ".mixin.mixins.common.",
                                                suffix = ";") String... mixins) {
        return MixinHelper.common(mixins);
    }

    private static SidedMixins client(@Language(value = "JAVA",
                                                prefix = "import " + Tags.GROUPNAME + ".mixin.mixins.client.",
                                                suffix = ";") String... mixins) {
        return MixinHelper.client(mixins);
    }

    private static SidedMixins server(@Language(value = "JAVA",
                                                prefix = "import " + Tags.GROUPNAME + ".mixin.mixins.server.",
                                                suffix = ";") String... mixins) {
        return MixinHelper.server(mixins);
    }
    //endregion

    private static class Ext {
        public static final BooleanSupplier Biome = () -> GeneralConfig.extendBiome;
        public static final BooleanSupplier BlockItem = () -> GeneralConfig.extendBlockItem;
        public static final BooleanSupplier DataWatcher = () -> GeneralConfig.extendDataWatcher;
        public static final BooleanSupplier Enchantment = () -> GeneralConfig.extendEnchantment;
        public static final BooleanSupplier Potion = () -> GeneralConfig.extendPotion;
        public static final BooleanSupplier Entity = () -> GeneralConfig.extendEntity;
    }
}
