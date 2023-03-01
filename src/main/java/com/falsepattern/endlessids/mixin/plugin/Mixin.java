package com.falsepattern.endlessids.mixin.plugin;

import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.lib.mixin.IMixin;
import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.always;
import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.avoid;
import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.condition;
import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.require;

@RequiredArgsConstructor
public enum Mixin implements IMixin {
    //region common
    //region vanilla
    S21PacketChunkDataMixin(Side.COMMON, always(), "vanilla.S21PacketChunkDataMixin"),
    //endregion vanilla

    //region AntiIDConflict
    AIDCAntiIdConflictBaseMixin(Side.COMMON, require(TargetedMod.ANTIIDCONFLICT),
                                "antiidconflict.AntiIdConflictBaseMixin"),
    AIDCBiomesGenBasePlaceholderMixin(Side.COMMON, require(TargetedMod.ANTIIDCONFLICT),
                                      "antiidconflict.BiomeGenBasePlaceholderMixin"),
    AIDCBiomesManagerMixin(Side.COMMON, require(TargetedMod.ANTIIDCONFLICT), "antiidconflict.BiomesManagerMixin"),
    AIDCDimensionsManagerMixin(Side.COMMON, require(TargetedMod.ANTIIDCONFLICT),
                               "antiidconflict.DimensionsManagerMixin"),
    AIDCEnchantementsManagerMixin(Side.COMMON, require(TargetedMod.ANTIIDCONFLICT),
                                  "antiidconflict.EnchantementsManagerMixin"),
    AIDCEntitiesManagerMixin(Side.COMMON, require(TargetedMod.ANTIIDCONFLICT), "antiidconflict.EntitiesManagerMixin"),
    AIDCPotionsManagerMixin(Side.COMMON, require(TargetedMod.ANTIIDCONFLICT), "antiidconflict.PotionsManagerMixin"),
    //endregion AntiIDConflict

    //region biome
    //region vanilla
    AnvilChunkLoaderMixinBiome(Side.COMMON, always(), "biome.vanilla.AnvilChunkLoaderMixin"),
    BiomeDictionaryMixin(Side.COMMON, always(), "biome.vanilla.BiomeDictionaryMixin"),
    BiomeGenBaseMixin(Side.COMMON, always(), "biome.vanilla.BiomeGenBaseMixin"),
    ChunkMixin(Side.COMMON, always(), "biome.vanilla.ChunkMixin"),
    GenLayerRiverMixMixin(Side.COMMON, avoid(TargetedMod.DRAGONAPI), "biome.vanilla.GenLayerRiverMixMixin"),
    GenLayerVoronoiZoomMixin(Side.COMMON, always(), "biome.vanilla.GenLayerVoronoiZoomMixin"),
    S21PacketChunkDataMixinBiome(Side.COMMON, always(), "biome.vanilla.S21PacketChunkDataMixin"),
    S26PacketMapChunkBulkMixinBiome(Side.COMMON, always(), "biome.vanilla.S26PacketMapChunkBulkMixin"),
    //endregion vanilla
    //region AbyssalCraft
    AbyssalCraftMixin(Side.COMMON, require(TargetedMod.ABYSSALCRAFT), "biome.abyssalcraft.AbyssalCraftMixin"),
    //endregion AbyssalCraft
    //region AntiqueAtlas
    BiomeDetectorBaseMixin(Side.COMMON, require(TargetedMod.ANTIQUEATLAS), "biome.antiqueatlas.BiomeDetectorBaseMixin"),
    BiomeDetectorNetherMixin(Side.COMMON, require(TargetedMod.ANTIQUEATLAS), "biome.antiqueatlas.BiomeDetectorNetherMixin"),
    //endregion AntiqueAtlas
    //region AdvancedRocketry
    AdvancedRocketryMixin(Side.COMMON, require(TargetedMod.AROCKETRY), "biome.arocketry.AdvancedRocketryMixin"),
    ARocketryChunkProviderSpaceMixin(Side.COMMON, require(TargetedMod.AROCKETRY), "biome.arocketry.ChunkProviderSpaceMixin"),
    //endregion AdvancedRocketry
    //region ATG
    ATGBiomeConfigMixin(Side.COMMON, require(TargetedMod.ATG), "biome.atg.ATGBiomeConfigMixin"),
    ATGBiomeManagerMixin(Side.COMMON, require(TargetedMod.ATG), "biome.atg.ATGBiomeManagerMixin"),
    ATGWorldGenRocksMixin(Side.COMMON, require(TargetedMod.ATG), "biome.atg.ATGWorldGenRocksMixin"),
    //endregion ATG
    //region BiomeTweaker
    BiomeEventHandlerMixin(Side.COMMON, require(TargetedMod.BIOMETWEAKER), "biome.biometweaker.BiomeEventHandlerMixin"),
    //endregion BiomeTweaker
    //region Biome Wand
    BiomeWandItemMixin(Side.COMMON, require(TargetedMod.BIOMEWAND), "biome.biomewand.BiomeWandItemMixin"),
    //endregion Biome Wand
    //region BoP
    BOPBiomeManagerMixin(Side.COMMON, require(TargetedMod.BOP), "biome.bop.BOPBiomeManagerMixin"),
    BOPBiomesMixin(Side.COMMON, require(TargetedMod.BOP), "biome.bop.BOPBiomesMixin"),
    //endregion BoP
    //region Buildcraft
    BuildcraftEnergyMixin(Side.COMMON, require(TargetedMod.BUILDCRAFT), "buildcraft.BuildcraftEnergyMixin"),
    //endregion Buildcraft
    //region CompactMachines
    CMCubeToolsMixin(Side.COMMON, require(TargetedMod.COMPACTMACHINES), "biome.compactmachines.CubeToolsMixin"),
    //endregion CompactMachines
    //region DarkWorld
    DarkWorldModBiomesMixin(Side.COMMON, require(TargetedMod.DARKWORLD), "biome.darkworld.ModBiomesMixin"),
    //endregion DarkWorld
    //region DragonAPI
    DragonAPIGenLayerRiverMixVanillaMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "biome.dragonapi.vanilla.GenLayerRiverMixMixin"),
    DragonAPIGenLayerRiverEventMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "biome.dragonapi.GenLayerRiverEventMixin"),
    DragonAPIBiomeIDTypeMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "biome.dragonapi.IDTypeMixin"),
    ReikaChunkHelperMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "biome.dragonapi.ReikaChunkHelperMixin"),
    ReikaWorldHelperMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "biome.dragonapi.ReikaWorldHelperMixin"),
    //endregion DragonAPI
    //region EnhancedBiomes
    EBBiomeIDsMixin(Side.COMMON, require(TargetedMod.EB), "biome.eb.BiomeIDsMixin"),
    EBGenLayerArchipelagoEdgeMixin(Side.COMMON, require(TargetedMod.EB), "biome.eb.GenLayerArchipelagoEdgeMixin"),
    EBGenLayerEBHillsMixin(Side.COMMON, require(TargetedMod.EB), "biome.eb.GenLayerEBHillsMixin"),
    EBGenLayerEBRiverMixMixin(Side.COMMON, require(TargetedMod.EB), "biome.eb.GenLayerEBRiverMixMixin"),
    EBGenLayerEBVoronoiZoomMixin(Side.COMMON, require(TargetedMod.EB), "biome.eb.GenLayerEBVoronoiZoomMixin"),
    //endregion EnhancedBiomes
    //region Enderlicious
    EnderliciousBiomeConfigurationMixin(Side.COMMON, require(TargetedMod.ENDERLICIOUS), "biome.enderlicious.BiomeConfigurationMixin"),
    EnderliciousEndChunkProviderMixin(Side.COMMON, require(TargetedMod.ENDERLICIOUS), "biome.enderlicious.EndChunkProviderMixin"),
    //endregion Enderlicious
    //region Erebus
    ErebusModBiomesMixin(Side.COMMON, require(TargetedMod.EREBUS), "biome.erebus.ModBiomesMixin"),
    //endregion Erebus
    //region ExtraPlanets
    ExtraPlanetsConfigMixin(Side.COMMON, require(TargetedMod.EXTRAPLANETS), "biome.extraplanets.ConfigMixin"),
    //endregion ExtraPlanets
    //region ExtraUtilities
    ChunkProviderEndOfTimeMixin(Side.COMMON, require(TargetedMod.EXTRAUTILITIES), "biome.extrautilities.ChunkProviderEndOfTimeMixin"),
    //endregion ExtraUtilities
    //region Futurepack
    FuturepackBiomeGenSpaceMixin(Side.COMMON, require(TargetedMod.FUTUREPACK), "biome.futurepack.BiomeGenSpaceMixin"),
    //endregion Futurepack
    //region GalactiCraftCore
    GalactiCraftConfigManagerCoreMixin(Side.COMMON, require(TargetedMod.GALACTICRAFTCORE),
                                       "biome.galacticraft.ConfigManagerCoreMixin"),
    //endregion GalactiCraftCore
    //region GalaxySpace
    GSChunkProviderKuiperMixin(Side.COMMON, require(TargetedMod.GALAXYSPACE), "biome.galaxyspace.ChunkProviderKuiperMixin"),
    GSChunkProviderMarsSSMixin(Side.COMMON, require(TargetedMod.GALAXYSPACE), "biome.galaxyspace.ChunkProviderMarsSSMixin"),
    GSChunkProviderSpaceLakesMixin(Side.COMMON, require(TargetedMod.GALAXYSPACE), "biome.galaxyspace.ChunkProviderSpaceLakesMixin"),
    GSChunkProviderVenusSSMixin(Side.COMMON, require(TargetedMod.GALAXYSPACE), "biome.galaxyspace.ChunkProviderVenusSSMixin"),
    //endregion GalaxySpace
    //region Highlands
    HighlandsConfigMixin(Side.COMMON, require(TargetedMod.HIGHLANDS), "biome.highlands.ConfigMixin"),
    //endregion Highlands
    //region ICG
    ICGMysteriumPatchesFixesCaveMixin(Side.COMMON, require(TargetedMod.ICG), "biome.icg.MysteriumPatchesFixesCaveMixin"),
    //endregion ICG
    //region LOTR
    LOTRBiomeVariantStorageMixin(Side.COMMON, require(TargetedMod.LOTR), "biome.lotr.LOTRBiomeVariantStorageMixin"),
    LOTRChunkProviderMixin(Side.COMMON, require(TargetedMod.LOTR), "biome.lotr.LOTRChunkProviderMixin"),
    LOTRChunkProviderUtumnoMixin(Side.COMMON, require(TargetedMod.LOTR), "biome.lotr.LOTRChunkProviderUtumnoMixin"),
    LOTRPacketBiomeVariantsWatchHandlerMixin(Side.COMMON, require(TargetedMod.LOTR),
                                             "biome.lotr.LOTRPacketBiomeVariantsWatchHandlerMixin"),
    LOTRWorldChunkManagerMixin(Side.COMMON, require(TargetedMod.LOTR), "biome.lotr.LOTRWorldChunkManagerMixin"),
    //endregion LOTR
    //region NaturesCompass
    NaturesCompassBiomeUtilsMixin(Side.COMMON, require(TargetedMod.NATURESCOMPASS), "biome.naturescompass.BiomeUtilsMixin"),
    //endregion NaturesCompass
    //region Netherlicious
    NetherliciousBiomeConfigurationMixin(Side.COMMON, require(TargetedMod.NETHERLICIOUS),
                                         "biome.netherlicious.BiomeConfigurationMixin"),
    //endregion Netherlicious
    //region NomadicTents
    NomadicTentsTentChunkProviderMixin(Side.COMMON, require(TargetedMod.NOMADICTENTS), "biome.nomadictents.TentChunkProviderMixin"),
    //endregion NomadicTents
    //region RandomThings
    ItemBiomePainterMixin(Side.COMMON, require(TargetedMod.RANDOMTHINGS), "biome.randomthings.ItemBiomePainterMixin"),
    MessagePaintBiomeMixin(Side.COMMON, require(TargetedMod.RANDOMTHINGS), "biome.randomthings.MessagePaintBiomeMixin"),
    //endregion RandomThings
    //region RTG
    RTGChunkProviderRTGMixin(Side.COMMON, require(TargetedMod.RTG), "biome.rtg.ChunkProviderRTGMixin"),
    RTGLandscapeGeneratorMixin(Side.COMMON, require(TargetedMod.RTG), "biome.rtg.LandscapeGeneratorMixin"),
    //endregion RTG
    //region RWG
    RWGChunkGeneratorRealisticMixin(Side.COMMON, require(TargetedMod.RWG), "biome.rwg.ChunkGeneratorRealisticMixin"),
    RWGRealisticBiomeBaseMixin(Side.COMMON, require(TargetedMod.RWG), "biome.rwg.RealisticBiomeBaseMixin"),
    RWGVillageMaterialsMixin(Side.COMMON, require(TargetedMod.RWG), "biome.rwg.VillageMaterialsMixin"),
    //endregion RWG
    //region Thaumcraft
    ThaumcraftUtilsMixin(Side.COMMON, require(TargetedMod.THAUMCRAFT), "biome.thaumcraft.UtilsMixin"),
    //endregion Thaumcraft
    //region Tropicraft
    GenLayerTropiVoronoiZoomMixin(Side.COMMON, require(TargetedMod.TROPICRAFT),
                                  "biome.tropicraft.GenLayerTropiVoronoiZoomMixin"),
    //endregion Tropicraft
    //region TwilightForest
    TFBiomeBaseMixin(Side.COMMON, require(TargetedMod.TWILIGHTFOREST), "biome.twilightforest.TFBiomeBaseMixin"),
    //endregion TwilightForest
    //region Witchery
    WitcheryWorldChunkManagerMirrorixin(Side.COMMON, require(TargetedMod.WITCHERY), "biome.witchery.WorldChunkManagerMirrorMixin"),
    //endregion Witchery

    //endregion biome

    //region blockitem
    //region vanilla
    AnvilChunkLoaderMixinBlockItem(Side.COMMON, always(), "blockitem.vanilla.AnvilChunkLoaderMixin"),
    BlockFireMixin(Side.COMMON, always(), "blockitem.vanilla.BlockFireMixin"),
    ExtendedBlockStorageMixin(Side.COMMON, always(), "blockitem.vanilla.ExtendedBlockStorageMixin"),
    ItemInWorldManagerMixin(Side.COMMON, always(), "blockitem.vanilla.ItemInWorldManagerMixin"),
    ItemStackMixin(Side.COMMON, always(), "blockitem.vanilla.ItemStackMixin"),
    PacketBufferMixin(Side.COMMON, always(), "blockitem.vanilla.PacketBufferMixin"),
    S21PacketChunkDataMixinBlockItem(Side.COMMON, always(), "blockitem.vanilla.S21PacketChunkDataMixin"),
    S22PacketMultiBlockChangeMixin(Side.COMMON, always(), "blockitem.vanilla.S22PacketMultiBlockChangeMixin"),
    S24PacketBlockActionMixin(Side.COMMON, always(), "blockitem.vanilla.S24PacketBlockActionMixin"),
    S26PacketMapChunkBulkMixinBlockItem(Side.COMMON, always(), "blockitem.vanilla.S26PacketMapChunkBulkMixin"),
    StatListMixin(Side.COMMON, always(), "blockitem.vanilla.StatListMixin"),
    WorldMixin(Side.COMMON, always(), "blockitem.vanilla.WorldMixin"),
    //endregion vanilla
    //region CoFHLib
    CoFHLibCofhBlockHelperMixin(Side.COMMON, require(TargetedMod.COFHLIB), "blockitem.cofhlib.BlockHelperMixin"),
    //endregion CoFHLib
    //region DragonAPI
    DragonAPIBlockPropertiesMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "blockitem.dragonapi.BlockPropertiesMixin"),
    DragonAPIBlockItemIDTypeMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "blockitem.dragonapi.IDTypeMixin"),
    //endregion DragonAPI
    //region MFQM
    MFQMMixin(Side.COMMON, require(TargetedMod.MFQM), "blockitem.mfqm.MFQMMixin"),
    //endregion MFQM
    //region WorldEdit
    WorldEditBaseBlockMixin(Side.COMMON, require(TargetedMod.WORLDEDIT), "blockitem.worldedit.BaseBlockMixin"),
    //endregion WorldEdit
    //region UBC
    UBCOreUBifierMixin(Side.COMMON, require(TargetedMod.UBC), "blockitem.ubc.OreUBifierMixin"),
    UBCBiomeUndergroundDecoratorMixin(Side.COMMON, require(TargetedMod.UBC), "blockitem.ubc.BiomeUndergroundDecoratorMixin"),
    //endregion UBC
    //endregion blockitem

    //region datawatcher
    //region vanilla
    DataWatcherMixin(Side.COMMON, condition(() -> GeneralConfig.extendDataWatcher), "datawatcher.vanilla.DataWatcherMixin"),
    //endregion vanilla
    //region MFQM
    MFQMDataWatcherMixin(Side.COMMON, require(TargetedMod.MFQM).and(condition(() -> GeneralConfig.extendDataWatcher)),
                         "datawatcher.mfqm.MFQMDataWatcherMixin"),
    //endregion MFQM
    //endregion datawatcher

    //region enchantment
    //region vanilla
    EnchantmentMixin(Side.COMMON, always(), "enchantment.vanilla.EnchantmentMixin"),
    //endregion vanilla
    //endregion enchantment

    //region potion
    //region vanilla
    PotionEffectMixin(Side.COMMON, always(), "potion.vanilla.PotionEffectMixin"),
    PotionMixin(Side.COMMON, always(), "potion.vanilla.PotionMixin"),
    S1DPacketEntityEffectMixin(Side.COMMON, always(), "potion.vanilla.network.S1DPacketEntityEffectMixin"),
    S1EPacketRemoveEntityEffectMixin(Side.COMMON, always(), "potion.vanilla.network.S1EPacketRemoveEntityEffectMixin"),
    //endregion vanilla
    //endregion potion

    //endregion common

    //region client

    //region biome
    //region vanilla
    ChunkMixinClientBiome(Side.CLIENT, always(), "biome.vanilla.ChunkMixin"),
    //endregion vanilla
    //region Biome Wand
    BiomeWandItemClientMixin(Side.CLIENT, require(TargetedMod.BIOMEWAND), "biome.biomewand.BiomeWandItemMixin"),
    //endregion Biome Wand
    //endregion biome

    //region blockitem
    //region vanilla
    ChunkMixinClientBlockItem(Side.CLIENT, always(), "blockitem.vanilla.ChunkMixin"),
    NetHandlerPlayClientMixinBlockItem(Side.CLIENT, always(), "blockitem.vanilla.NetHandlerPlayClientMixin"),
    PlayerControllerMPMixin(Side.CLIENT, always(), "blockitem.vanilla.PlayerControllerMPMixin"),
    RenderGlobalMixin(Side.CLIENT, always(), "blockitem.vanilla.RenderGlobalMixin"),
    //endregion vanilla
    //endregion blockitem

    //region potion
    NetHandlerPlayClientMixinPotion(Side.CLIENT, always(), "potion.vanilla.NetHandlerPlayClientMixin"),
    //endregion potion

    //endregion client

    ;
    @Getter
    private final Side side;
    @Getter
    private final Predicate<List<ITargetedMod>> filter;
    @Getter
    private final String mixin;
}

