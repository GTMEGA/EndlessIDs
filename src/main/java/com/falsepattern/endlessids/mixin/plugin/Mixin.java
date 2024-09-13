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

import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.lib.mixin.IMixin;
import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.avoid;
import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.condition;
import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.require;

@RequiredArgsConstructor
public enum Mixin implements IMixin {
    //region common
    //region AntiIDConflict
    AIDCAntiIdConflictBaseMixin(Side.COMMON, require(TargetedMod.ANTIIDCONFLICT), "antiidconflict.AntiIdConflictBaseMixin"),
    AIDCBiomesGenBasePlaceholderMixin(Side.COMMON, require(TargetedMod.ANTIIDCONFLICT), "antiidconflict.BiomeGenBasePlaceholderMixin"),
    AIDCBiomesManagerMixin(Side.COMMON, require(TargetedMod.ANTIIDCONFLICT), "antiidconflict.BiomesManagerMixin"),
    AIDCDimensionsManagerMixin(Side.COMMON, require(TargetedMod.ANTIIDCONFLICT), "antiidconflict.DimensionsManagerMixin"),
    AIDCEnchantementsManagerMixin(Side.COMMON, require(TargetedMod.ANTIIDCONFLICT), "antiidconflict.EnchantementsManagerMixin"),
    AIDCEntitiesManagerMixin(Side.COMMON, require(TargetedMod.ANTIIDCONFLICT), "antiidconflict.EntitiesManagerMixin"),
    AIDCPotionsManagerMixin(Side.COMMON, require(TargetedMod.ANTIIDCONFLICT), "antiidconflict.PotionsManagerMixin"),
    //endregion AntiIDConflict
    //region biome
    //region vanilla
    BiomeDictionaryMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome), "biome.vanilla.BiomeDictionaryMixin"),
    BiomeGenBaseMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome), "biome.vanilla.BiomeGenBaseMixin"),
    ChunkMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome), "biome.vanilla.ChunkMixin"),
    GenLayerRiverMixMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(avoid(TargetedMod.DRAGONAPI)), "biome.vanilla.GenLayerRiverMixMixin"),
    GenLayerVoronoiZoomMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome), "biome.vanilla.GenLayerVoronoiZoomMixin"),
    //endregion vanilla
    //region AbyssalCraft
    AbyssalCraftMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.ABYSSALCRAFT)), "biome.abyssalcraft.AbyssalCraftMixin"),
    //endregion AbyssalCraft
    //region AntiqueAtlas
    BiomeDetectorBaseMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.ANTIQUEATLAS)), "biome.antiqueatlas.BiomeDetectorBaseMixin"),
    BiomeDetectorNetherMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.ANTIQUEATLAS)), "biome.antiqueatlas.BiomeDetectorNetherMixin"),
    //endregion AntiqueAtlas
    //region AdvancedRocketry
    AdvancedRocketryMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.AROCKETRY)), "biome.arocketry.AdvancedRocketryMixin"),
    ARocketryChunkProviderSpaceMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.AROCKETRY)), "biome.arocketry.ChunkProviderSpaceMixin"),
    //endregion AdvancedRocketry
    //region ATG
    ATGBiomeConfigMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.ATG)), "biome.atg.ATGBiomeConfigMixin"),
    ATGBiomeManagerMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.ATG)), "biome.atg.ATGBiomeManagerMixin"),
    ATGWorldGenRocksMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.ATG)), "biome.atg.ATGWorldGenRocksMixin"),
    //endregion ATG
    //region BiomeTweaker
    BiomeEventHandlerMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.BIOMETWEAKER)), "biome.biometweaker.BiomeEventHandlerMixin"),
    //endregion BiomeTweaker
    //region Biome Wand
    BiomeWandItemMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.BIOMEWAND)), "biome.biomewand.BiomeWandItemMixin"),
    //endregion Biome Wand
    //region BoP
    BOPBiomeManagerMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.BOP)), "biome.bop.BOPBiomeManagerMixin"),
    BOPBiomesMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.BOP)), "biome.bop.BOPBiomesMixin"),
    //endregion BoP
    //region Buildcraft
    BuildcraftEnergyMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.BUILDCRAFT)), "biome.buildcraft.BuildcraftEnergyMixin"),
    //endregion Buildcraft
    //region ClimateControl
    ClimateControl_api_BiomeSettingsMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.api.BiomeSettingsMixin"),
    ClimateControl_api_ClimateControlRulesMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.api.ClimateControlRulesMixin"),
    ClimateControl_customGenLayer_ConfirmBiomeMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.customGenLayer.ConfirmBiomeMixin"),
    ClimateControl_customGenLayer_GenLayerBiomeByClimateMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.customGenLayer.GenLayerBiomeByClimateMixin"),
    ClimateControl_customGenLayer_GenLayerBiomeByTaggedClimateMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.customGenLayer.GenLayerBiomeByTaggedClimateMixin"),
    ClimateControl_customGenLayer_GenLayerDefineClimateMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.customGenLayer.GenLayerDefineClimateMixin"),
    ClimateControl_customGenLayer_GenLayerLowlandRiverMixMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.customGenLayer.GenLayerLowlandRiverMixMixin"),
    ClimateControl_customGenLayer_GenLayerSmoothCoastMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.customGenLayer.GenLayerSmoothCoastMixin"),
    ClimateControl_customGenLayer_GenLayerSubBiomeMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.customGenLayer.GenLayerSubBiomeMixin"),
    ClimateControl_generator_AbstractWorldGeneratorMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.generator.AbstractWorldGeneratorMixin"),
    ClimateControl_generator_BiomeSwapperMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.generator.BiomeSwapperMixin"),
    ClimateControl_generator_SubBiomeChooserMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.generator.SubBiomeChooserMixin"),
    ClimateControl_genLayerPack_GenLayerHillsMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.genLayerPack.GenLayerHillsMixin"),
    ClimateControl_genLayerPack_GenLayerPackMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.genLayerPack.GenLayerPackMixin"),
    ClimateControl_genLayerPack_GenLayerSmoothMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.genLayerPack.GenLayerSmoothMixin"),
    ClimateControl_genLayerPack_GenLayerVoronoiZoomMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.genLayerPack.GenLayerVoronoiZoomMixin"),
    ClimateControl_genLayerPack_GenLayerZoomMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.CLIMATECONTROL)), "biome.climatecontrol.genLayerPack.GenLayerZoomMixin"),
    //endregion ClimateControl
    //region CompactMachines
    CMCubeToolsMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.COMPACTMACHINES)), "biome.compactmachines.CubeToolsMixin"),
    //endregion CompactMachines
    //region DarkWorld
    DarkWorldModBiomesMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.DARKWORLD)), "biome.darkworld.ModBiomesMixin"),
    //endregion DarkWorld
    //region DimDoors
    DDBiomeGenBaseMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.DIMDOORS)), "biome.dimdoors.DDBiomeGenBaseMixin"),
    //endregion DimDoors
    //region DragonAPI
    DragonAPIGenLayerRiverMixVanillaMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.DRAGONAPI)), "biome.dragonapi.vanilla.GenLayerRiverMixMixin"),
    DragonAPIGenLayerRiverEventMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.DRAGONAPI)), "biome.dragonapi.GenLayerRiverEventMixin"),
    DragonAPIBiomeIDTypeMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.DRAGONAPI)), "biome.dragonapi.IDTypeMixin"),
    ReikaChunkHelperMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.DRAGONAPI)), "biome.dragonapi.ReikaChunkHelperMixin"),
    ReikaWorldHelperMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.DRAGONAPI)), "biome.dragonapi.ReikaWorldHelperMixin"),
    //endregion DragonAPI
    //region EnhancedBiomes
    EBBiomeIDsMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.EB)), "biome.eb.BiomeIDsMixin"),
    EBGenLayerArchipelagoEdgeMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.EB)), "biome.eb.GenLayerArchipelagoEdgeMixin"),
    EBGenLayerEBHillsMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.EB)), "biome.eb.GenLayerEBHillsMixin"),
    EBGenLayerEBRiverMixMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.EB)), "biome.eb.GenLayerEBRiverMixMixin"),
    EBGenLayerEBVoronoiZoomMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.EB)), "biome.eb.GenLayerEBVoronoiZoomMixin"),
    //endregion EnhancedBiomes
    //region Enderlicious
    EnderliciousBiomeConfigurationMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.ENDERLICIOUS)), "biome.enderlicious.BiomeConfigurationMixin"),
    EnderliciousEndChunkProviderMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.ENDERLICIOUS)), "biome.enderlicious.EndChunkProviderMixin"),
    //endregion Enderlicious
    //region Erebus
    ErebusModBiomesMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.EREBUS)), "biome.erebus.ModBiomesMixin"),
    //endregion Erebus
    //region ExtraPlanets
    ExtraPlanetsConfigMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.EXTRAPLANETS)), "biome.extraplanets.ConfigMixin"),
    //endregion ExtraPlanets
    //region ExtraUtilities
    ChunkProviderEndOfTimeMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.EXTRAUTILITIES)), "biome.extrautilities.ChunkProviderEndOfTimeMixin"),
    //endregion ExtraUtilities
    //region Futurepack
    FuturepackBiomeGenSpaceMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.FUTUREPACK)), "biome.futurepack.BiomeGenSpaceMixin"),
    //endregion Futurepack
    //region GalactiCraftCore
    GalactiCraftConfigManagerCoreMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.GALACTICRAFTCORE)), "biome.galacticraft.ConfigManagerCoreMixin"),
    //endregion GalactiCraftCore
    //region GalaxySpace
    GSChunkProviderKuiperMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.GALAXYSPACE)), "biome.galaxyspace.ChunkProviderKuiperMixin"),
    GSChunkProviderMarsSSMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.GALAXYSPACE)), "biome.galaxyspace.ChunkProviderMarsSSMixin"),
    GSChunkProviderSpaceLakesMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.GALAXYSPACE)), "biome.galaxyspace.ChunkProviderSpaceLakesMixin"),
    GSChunkProviderVenusSSMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.GALAXYSPACE)), "biome.galaxyspace.ChunkProviderVenusSSMixin"),
    //endregion GalaxySpace
    //region Highlands
    HighlandsConfigMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.HIGHLANDS)), "biome.highlands.ConfigMixin"),
    //endregion Highlands
    //region ICG
    ICGMysteriumPatchesFixesCaveMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.ICG)), "biome.icg.MysteriumPatchesFixesCaveMixin"),
    //endregion ICG
    //region Industrial Revolution by Redstone Rebooted
    IR3IR2Mixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.IR3)), "biome.ir3.IR2Mixin"),
    //endregion Industrial Revolution by Redstone Rebooted
    //region LOTR
    LOTRBiomeVariantStorageMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.LOTR)), "biome.lotr.LOTRBiomeVariantStorageMixin"),
    LOTRChunkProviderMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.LOTR)), "biome.lotr.LOTRChunkProviderMixin"),
    LOTRChunkProviderUtumnoMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.LOTR)), "biome.lotr.LOTRChunkProviderUtumnoMixin"),
    LOTRDimensionMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.LOTR)), "biome.lotr.LOTRDimensionMixin"),
    LOTRPacketBiomeVariantsWatchHandlerMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.LOTR)), "biome.lotr.LOTRPacketBiomeVariantsWatchHandlerMixin"),
    LOTRWorldChunkManagerMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.LOTR)), "biome.lotr.LOTRWorldChunkManagerMixin"),
    LOTRWorldProviderMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.LOTR)), "biome.lotr.LOTRWorldProviderMixin"),
    //endregion LOTR
    //region NaturesCompass
    NaturesCompassBiomeUtilsMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.NATURESCOMPASS)), "biome.naturescompass.BiomeUtilsMixin"),
    //endregion NaturesCompass
    //region Netherlicious
    NetherliciousBiomeConfigurationMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.NETHERLICIOUS)), "biome.netherlicious.BiomeConfigurationMixin"),
    //endregion Netherlicious
    //region NomadicTents
    NomadicTentsTentChunkProviderMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.NOMADICTENTS)), "biome.nomadictents.TentChunkProviderMixin"),
    //endregion NomadicTents
    //region OWG
    OWGChunkGeneratorBetaMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.OWG)), "biome.owg.ChunkGeneratorBetaMixin"),
    //endregion OWG
    //region RandomThings
    ItemBiomePainterMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.RANDOMTHINGS)), "biome.randomthings.ItemBiomePainterMixin"),
    MessagePaintBiomeMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.RANDOMTHINGS)), "biome.randomthings.MessagePaintBiomeMixin"),
    //endregion RandomThings
    //region Restructured
    RSBiomeHelperMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.RESTRUCTURED)), "biome.restructured.BiomeHelperMixin"),
    //endregion Restructured
    //region RTG
    RTGChunkProviderRTGMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.RTG)), "biome.rtg.ChunkProviderRTGMixin"),
    RTGLandscapeGeneratorMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.RTG)), "biome.rtg.LandscapeGeneratorMixin"),
    RTGWorldChunkManagerRTGMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.RTG)), "biome.rtg.WorldChunkManagerRTGMixin"),
    //endregion RTG
    //region RWG
    RWGChunkGeneratorRealisticMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.RWG)), "biome.rwg.ChunkGeneratorRealisticMixin"),
    RWGRealisticBiomeBaseMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.RWG)), "biome.rwg.RealisticBiomeBaseMixin"),
    RWGVillageMaterialsMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.RWG)), "biome.rwg.VillageMaterialsMixin"),
    //endregion RWG
    //region Thaumcraft
    ThaumcraftUtilsMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.THAUMCRAFT)), "biome.thaumcraft.UtilsMixin"),
    //endregion Thaumcraft
    //region ThutCore
    ThutVector3Mixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.THUTCORE)), "biome.thut.Vector3Mixin"),
    //endregion ThutCore
    //region Tropicraft
    GenLayerTropiVoronoiZoomMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.TROPICRAFT)), "biome.tropicraft.GenLayerTropiVoronoiZoomMixin"),
    //endregion Tropicraft
    //region TwilightForest
    TFBiomeBaseMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.TWILIGHTFOREST)), "biome.twilightforest.TFBiomeBaseMixin"),
    //endregion TwilightForest
    //region Witchery
    WitcheryWorldChunkManagerMirrorMixin(Side.COMMON, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.WITCHERY)), "biome.witchery.WorldChunkManagerMirrorMixin"),
    //endregion Witchery
    //endregion biome
    //region blockitem
    //region vanilla
    BlockFireMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem), "blockitem.vanilla.BlockFireMixin"),
    BlockMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem), "blockitem.vanilla.BlockMixin"),
    ExtendedBlockStorageMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem), "blockitem.vanilla.ExtendedBlockStorageMixin"),
    ItemInWorldManagerMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem), "blockitem.vanilla.ItemInWorldManagerMixin"),
    ItemStackMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem), "blockitem.vanilla.ItemStackMixin"),
    PacketBufferMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem), "blockitem.vanilla.PacketBufferMixin"),
    S22PacketMultiBlockChangeMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem), "blockitem.vanilla.S22PacketMultiBlockChangeMixin"),
    S24PacketBlockActionMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem), "blockitem.vanilla.S24PacketBlockActionMixin"),
    StatListMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem), "blockitem.vanilla.StatListMixin"),
    WorldMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem), "blockitem.vanilla.WorldMixin"),
    //endregion vanilla
    //region CoFHLib
    CoFHLibCofhBlockHelperMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem).and(require(TargetedMod.COFHLIB)), "blockitem.cofhlib.BlockHelperMixin"),
    //endregion CoFHLib
    //region DragonAPI
    DragonAPIBlockPropertiesMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem).and(require(TargetedMod.DRAGONAPI)), "blockitem.dragonapi.BlockPropertiesMixin"),
    DragonAPIBlockItemIDTypeMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem).and(require(TargetedMod.DRAGONAPI)), "blockitem.dragonapi.IDTypeMixin"),
    //endregion DragonAPI
    //region HBM's Nuclear Tech Nod
    NTMChunkRadiationHandlerPRISMMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem).and(require(TargetedMod.HBM_NTM)), "blockitem.ntm.ChunkRadiationHandlerPRISMMixin"),
    NTMChunkRadiationHandlerPRISM_SubChunkMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem).and(require(TargetedMod.HBM_NTM)), "blockitem.ntm.ChunkRadiationHandlerPRISMMixin"),
    //endregion HBM's Nuclear Tech Nod
    //region Matter Overdrive
    MOInventoryMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem).and(require(TargetedMod.MATTEROVERDRIVE).or(require(TargetedMod.MATTERMEGADRIVE))), "blockitem.matteroverdrive.InventoryMixin"),
    MOItemPatternMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem).and(require(TargetedMod.MATTEROVERDRIVE).or(require(TargetedMod.MATTERMEGADRIVE))), "blockitem.matteroverdrive.ItemPatternMixin"),
    //endregion Matter Overdrive
    //region MFQM
    MFQMMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem).and(require(TargetedMod.MFQM)), "blockitem.mfqm.MFQMMixin"),
    //endregion MFQM
    //region WorldEdit
    WorldEditBaseBlockMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem).and(require(TargetedMod.WORLDEDIT)), "blockitem.worldedit.BaseBlockMixin"),
    //endregion WorldEdit
    //region UBC
    UBCOreUBifierMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem).and(require(TargetedMod.UBC)), "blockitem.ubc.OreUBifierMixin"),
    UBCBiomeUndergroundDecoratorMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem).and(require(TargetedMod.UBC)), "blockitem.ubc.BiomeUndergroundDecoratorMixin"),
    //endregion UBC
    //endregion blockitem
    //region datawatcher
    //region vanilla
    DataWatcherMixin(Side.COMMON, condition(() -> GeneralConfig.extendDataWatcher), "datawatcher.vanilla.DataWatcherMixin"),
    //endregion vanilla
    //region MFQM
    MFQMDataWatcherMixin(Side.COMMON, condition(() -> GeneralConfig.extendDataWatcher).and(require(TargetedMod.MFQM)), "datawatcher.mfqm.MFQMDataWatcherMixin"),
    //endregion MFQM
    //endregion datawatcher
    //region enchantment
    //region vanilla
    EnchantmentMixin(Side.COMMON, condition(() -> GeneralConfig.extendEnchantment), "enchantment.vanilla.EnchantmentMixin"),
    //endregion vanilla
    //endregion enchantment
    //region potion
    //region vanilla
    PotionEffectMixin(Side.COMMON, condition(() -> GeneralConfig.extendPotion), "potion.vanilla.PotionEffectMixin"),
    PotionMixin(Side.COMMON, condition(() -> GeneralConfig.extendPotion), "potion.vanilla.PotionMixin"),
    S1DPacketEntityEffectMixin(Side.COMMON, condition(() -> GeneralConfig.extendPotion), "potion.vanilla.network.S1DPacketEntityEffectMixin"),
    S1EPacketRemoveEntityEffectMixin(Side.COMMON, condition(() -> GeneralConfig.extendPotion), "potion.vanilla.network.S1EPacketRemoveEntityEffectMixin"),
    //endregion vanilla
    //endregion potion
    //region entity
    //region vanilla
    EntityListMixin(Side.COMMON, condition(() -> GeneralConfig.extendEntity), "entity.vanilla.EntityListMixin"),
    EntityRegistryMixin(Side.COMMON, condition(() -> GeneralConfig.extendEntity), "entity.vanilla.EntityRegistryMixin"),
    S0FPacketSpawnMobMixin(Side.COMMON, condition(() -> GeneralConfig.extendEntity), "entity.vanilla.S0FPacketSpawnMobMixin"),
    //endregion vanilla
    //endregion entity
    //endregion common

    //region client
    //region biome
    //region Biome Wand
    BiomeWandItemClientMixin(Side.CLIENT, condition(() -> GeneralConfig.extendBiome).and(require(TargetedMod.BIOMEWAND)), "biome.biomewand.BiomeWandItemMixin"),
    //endregion Biome Wand
    //endregion biome
    //region blockitem
    //region vanilla
    NetHandlerPlayClientMixinBlockItem(Side.CLIENT, condition(() -> GeneralConfig.extendBlockItem), "blockitem.vanilla.NetHandlerPlayClientMixin"),
    PlayerControllerMPMixin(Side.CLIENT, condition(() -> GeneralConfig.extendBlockItem), "blockitem.vanilla.PlayerControllerMPMixin"),
    RenderGlobalMixin(Side.CLIENT, condition(() -> GeneralConfig.extendBlockItem), "blockitem.vanilla.RenderGlobalMixin"),
    //endregion vanilla
    //endregion blockitem
    //region potion
    //region vanilla
    NetHandlerPlayClientMixinPotion(Side.CLIENT, condition(() -> GeneralConfig.extendPotion), "potion.vanilla.NetHandlerPlayClientMixin"),
    //endregion vanilla
    //endregion potion
    //endregion client

    //region redstone
    //region common
    BlockCompressedPoweredMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem && GeneralConfig.extendRedstone), "redstone.BlockCompressedPoweredMixin"),
    BlockRedstoneDiodeMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem && GeneralConfig.extendRedstone), "redstone.BlockRedstoneDiodeMixin"),
    BlockRedstoneTorchMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem && GeneralConfig.extendRedstone), "redstone.BlockRedstoneTorchMixin"),
    RedstoneWorldMixin(Side.COMMON, condition(() -> GeneralConfig.extendBlockItem && GeneralConfig.extendRedstone), "redstone.WorldMixin"),
    //endregion common
    //region client
    BlockRedstoneWireMixin(Side.CLIENT, condition(() -> GeneralConfig.extendBlockItem && GeneralConfig.extendRedstone), "redstone.BlockRedstoneWireMixin"),
    RedstoneRenderBlocksMixin(Side.CLIENT, condition(() -> GeneralConfig.extendBlockItem && GeneralConfig.extendRedstone), "redstone.RenderBlocksMixin"),
    //endregion client
    //endregion redstone

    ;
    @Getter
    private final Side side;
    @Getter
    private final Predicate<List<ITargetedMod>> filter;
    @Getter
    private final String mixin;
}

