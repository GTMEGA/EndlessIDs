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
    //region Minecraft
    //region Minecraft->common
    //region Minecraft->common->storage
    AnvilChunkLoaderMixin(Side.COMMON, always(), "vanilla.storage.AnvilChunkLoaderMixin"),
    ChunkMixin(Side.COMMON, always(), "vanilla.storage.ChunkMixin"),
    ExtendedBlockStorageMixin(Side.COMMON, always(), "vanilla.storage.ExtendedBlockStorageMixin"),
    ItemStackMixin(Side.COMMON, always(), "vanilla.storage.ItemStackMixin"),
    //endregion Minecraft->common->storage
    //region Minecraft->common->networking
    PacketBufferMixin(Side.COMMON, always(), "vanilla.networking.PacketBufferMixin"),
    S21PacketChunkDataMixin(Side.COMMON, always(), "vanilla.networking.S21PacketChunkDataMixin"),
    S22PacketMultiBlockChangeMixin(Side.COMMON, always(), "vanilla.networking.S22PacketMultiBlockChangeMixin"),
    S24PacketBlockActionMixin(Side.COMMON, always(), "vanilla.networking.S24PacketBlockActionMixin"),
    S26PacketMapChunkBulkMixin(Side.COMMON, always(), "vanilla.networking.S26PacketMapChunkBulkMixin"),
    //endregion Minecraft->common->networking
    //region Minecraft->common->biome
    BiomeDictionaryMixin(Side.COMMON, always(), "vanilla.biome.BiomeDictionaryMixin"),
    BiomeGenBaseMixin(Side.COMMON, always(), "vanilla.biome.BiomeGenBaseMixin"),
    //endregion Minecraft->common->biome
    //region Minecraft->common->misc
    BlockFireMixin(Side.COMMON, always(), "vanilla.misc.BlockFireMixin"),
    DataWatcherMixin(Side.COMMON, condition(() -> GeneralConfig.extendDataWatcher), "vanilla.misc.DataWatcherMixin"),
    ItemInWorldManagerMixin(Side.COMMON, always(), "vanilla.misc.ItemInWorldManagerMixin"),
    StatListMixin(Side.COMMON, always(), "vanilla.misc.StatListMixin"),
    WorldMixin(Side.COMMON, always(), "vanilla.misc.WorldMixin"),
    //endregion Minecraft->common->misc
    //region Minecraft->common->worldgen
    GenLayerRiverMixMixin(Side.COMMON, avoid(TargetedMod.DRAGONAPI), "vanilla.worldgen.GenLayerRiverMixMixin"),
    GenLayerVoronoiZoomMixin(Side.COMMON, always(), "vanilla.worldgen.GenLayerVoronoiZoomMixin"),
    //endregion Minecraft->common->worldgen
    //endregion Minecraft->common
    //region Minecraft->client
    ChunkMixinClient(Side.CLIENT, always(), "vanilla.ChunkMixin"),
    NetHandlerPlayClientMixin(Side.CLIENT, always(), "vanilla.NetHandlerPlayClientMixin"),
    PlayerControllerMPMixin(Side.CLIENT, always(), "vanilla.PlayerControllerMPMixin"),
    RenderGlobalMixin(Side.CLIENT, always(), "vanilla.RenderGlobalMixin"),
    //endregion Minecraft->client
    //endregion Minecraft
    //region AbyssalCraft->common
    AbyssalCraftMixin(Side.COMMON, require(TargetedMod.ABYSSALCRAFT), "abyssalcraft.AbyssalCraftMixin"),
    //endregion AbyssalCraft->common
    //region ATG->common
    ATGBiomeConfigMixin(Side.COMMON, require(TargetedMod.ATG), "atg.ATGBiomeConfigMixin"),
    ATGBiomeManagerMixin(Side.COMMON, require(TargetedMod.ATG), "atg.ATGBiomeManagerMixin"),
    ATGWorldGenRocksMixin(Side.COMMON, require(TargetedMod.ATG), "atg.ATGWorldGenRocksMixin"),
    //endregion ATG->common
    //region AntiqueAtlas->commond
    BiomeDetectorBaseMixin(Side.COMMON, require(TargetedMod.ANTIQUEATLAS), "antiqueatlas.BiomeDetectorBaseMixin"),
    BiomeDetectorNetherMixin(Side.COMMON, require(TargetedMod.ANTIQUEATLAS), "antiqueatlas.BiomeDetectorNetherMixin"),
    //endregion AntiqueAtlas->commond
    //region AntiIDConflict->common
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
    //endregion AntiIDConflict->common
    //region BoP->common
    BOPBiomeManagerMixin(Side.COMMON, require(TargetedMod.BOP), "bop.BOPBiomeManagerMixin"),
    //endregion BoP->common
    //region BiomeTweaker->common
    BiomeEventHandlerMixin(Side.COMMON, require(TargetedMod.BIOMETWEAKER), "biometweaker.BiomeEventHandlerMixin"),
    //endregion BiomeTweaker->common
    //region Biome Wand
    //region Biome Wand->client
    BiomeWandItemClientMixin(Side.CLIENT, require(TargetedMod.BIOMEWAND), "biomewand.BiomeWandItemMixin"),
    //endregion Biome Wand->client
    //region Biome Wand->common
    BiomeWandItemMixin(Side.COMMON, require(TargetedMod.BIOMEWAND), "biomewand.BiomeWandItemMixin"),
    //endregion Biome Wand->common
    //endregion Biome Wand
    //region Buildcraft
    BuildcraftEnergyMixin(Side.COMMON, require(TargetedMod.BUILDCRAFT), "buildcraft.BuildcraftEnergyMixin"),
    //endregion Buildcraft
    //region CoFHLib->common
    CoFHLibCofhBlockHelperMixin(Side.COMMON, require(TargetedMod.COFHLIB), "cofhlib.BlockHelperMixin"),
    //endregion CoFHLib->common
    //region CompactMachines->common
    CMCubeToolsMixin(Side.COMMON, require(TargetedMod.COMPACTMACHINES), "compactmachines.CubeToolsMixin"),
    //endregion CompactMachines->common
    //region DarkWorld->common
    DarkWorldModBiomesMixin(Side.COMMON, require(TargetedMod.DARKWORLD), "darkworld.ModBiomesMixin"),
    //endregion DarkWorld->common
    //region DragonAPI->common
    //region DragonAPI->common->vanilla
    DragonAPIGenLayerRiverMixMixin(Side.COMMON, require(TargetedMod.DRAGONAPI),
                                   "dragonapi.vanilla.GenLayerRiverMixMixin"),
    //endregion DragonAPI->common->vanilla
    DragonAPIGenLayerRiverEventMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "dragonapi.GenLayerRiverEventMixin"),
    DragonAPIBlockPropertiesMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "dragonapi.BlockPropertiesMixin"),
    DragonAPIIDTypeMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "dragonapi.IDTypeMixin"),
    ReikaChunkHelperMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "dragonapi.ReikaChunkHelperMixin"),
    ReikaWorldHelperMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "dragonapi.ReikaWorldHelperMixin"),
    //endregion DragonAPI->common
    //region Enderlicious->common
    EnderliciousBiomeConfigurationMixin(Side.COMMON, require(TargetedMod.ENDERLICIOUS), "enderlicious.BiomeConfigurationMixin"),
    EnderliciousEndChunkProviderMixin(Side.COMMON, require(TargetedMod.ENDERLICIOUS), "enderlicious.EndChunkProviderMixin"),
    //endregion Enderlicious->common
    //region EnhancedBiomes
    EBBiomeIDsMixin(Side.COMMON, require(TargetedMod.EB), "eb.BiomeIDsMixin"),
    EBGenLayerArchipelagoEdgeMixin(Side.COMMON, require(TargetedMod.EB), "eb.GenLayerArchipelagoEdgeMixin"),
    EBGenLayerEBHillsMixin(Side.COMMON, require(TargetedMod.EB), "eb.GenLayerEBHillsMixin"),
    EBGenLayerEBRiverMixMixin(Side.COMMON, require(TargetedMod.EB), "eb.GenLayerEBRiverMixMixin"),
    EBGenLayerEBVoronoiZoomMixin(Side.COMMON, require(TargetedMod.EB), "eb.GenLayerEBVoronoiZoomMixin"),
    //endregion EnhancedBiomes
    //region Erebus->common
    ErebusModBiomesMixin(Side.COMMON, require(TargetedMod.EREBUS), "erebus.ModBiomesMixin"),
    //endregion Erebus->common
    //region ExtraPlanets->common
    ExtraPlanetsConfigMixin(Side.COMMON, require(TargetedMod.EXTRAPLANETS), "extraplanets.ConfigMixin"),
    //endregion ExtraPlanets->common
    //region ExtraUtilities->common
    ChunkProviderEndOfTimeMixin(Side.COMMON, require(TargetedMod.EXTRAUTILITIES), "extrautilities.ChunkProviderEndOfTimeMixin"),
    //endregion ExtraUtilities->common
    //region Futurepack->common
    FuturepackBiomeGenSpaceMixin(Side.COMMON, require(TargetedMod.FUTUREPACK), "futurepack.BiomeGenSpaceMixin"),
    //endregion Futurepack->common
    //region GalactiCraftCore->common
    GalactiCraftConfigManagerCoreMixin(Side.COMMON, require(TargetedMod.GALACTICRAFTCORE),
                                       "galacticraft.ConfigManagerCoreMixin"),
    //endregion GalactiCraftCore->common
    //region Highlands->common
    HighlandsConfigMixin(Side.COMMON, require(TargetedMod.HIGHLANDS), "highlands.ConfigMixin"),
    //endregion Highlands->common
    //region ICG->common
    ICGMysteriumPatchesFixesCaveMixin(Side.COMMON, require(TargetedMod.ICG), "icg.MysteriumPatchesFixesCaveMixin"),
    //endregion ICG->common
    //region LOTR->common
    LOTRBiomeVariantStorageMixin(Side.COMMON, require(TargetedMod.LOTR), "lotr.LOTRBiomeVariantStorageMixin"),
    LOTRChunkProviderMixin(Side.COMMON, require(TargetedMod.LOTR), "lotr.LOTRChunkProviderMixin"),
    LOTRChunkProviderUtumnoMixin(Side.COMMON, require(TargetedMod.LOTR), "lotr.LOTRChunkProviderUtumnoMixin"),
    LOTRPacketBiomeVariantsWatchHandlerMixin(Side.COMMON, require(TargetedMod.LOTR),
                                             "lotr.LOTRPacketBiomeVariantsWatchHandlerMixin"),
    LOTRWorldChunkManagerMixin(Side.COMMON, require(TargetedMod.LOTR), "lotr.LOTRWorldChunkManagerMixin"),
    //endregion LOTR->common
    //region MFQM->common
    MFQMMixin(Side.COMMON, require(TargetedMod.MFQM), "mfqm.MFQMMixin"),
    MFQMDataWatcherMixin(Side.COMMON, require(TargetedMod.MFQM).and(condition(() -> GeneralConfig.extendDataWatcher)),
                         "mfqm.MFQMDataWatcherMixin"),
    //endregion MFQM->common
    //region NaturesCompass->common
    NaturesCompassBiomeUtilsMixin(Side.COMMON, require(TargetedMod.NATURESCOMPASS), "naturescompass.BiomeUtilsMixin"),
    //endregion NaturesCompass->common
    //region Netherlicious->common
    NetherliciousBiomeConfigurationMixin(Side.COMMON, require(TargetedMod.NETHERLICIOUS),
                                         "netherlicious.BiomeConfigurationMixin"),
    //endregion Netherlicious->common
    //region RandomThings->common
    ItemBiomePainterMixin(Side.COMMON, require(TargetedMod.RANDOMTHINGS), "randomthings.ItemBiomePainterMixin"),
    MessagePaintBiomeMixin(Side.COMMON, require(TargetedMod.RANDOMTHINGS), "randomthings.MessagePaintBiomeMixin"),
    //endregion RandomThings->common
    //region RTG->common
    RTGChunkProviderRTGMixin(Side.COMMON, require(TargetedMod.RTG), "rtg.ChunkProviderRTGMixin"),
    RTGLandscapeGeneratorMixin(Side.COMMON, require(TargetedMod.RTG), "rtg.LandscapeGeneratorMixin"),
    RTGRealisticBiomeBaseMixin(Side.COMMON, require(TargetedMod.RTG), "rtg.RealisticBiomeBaseMixin"),
    //endregion RTG->common
    //region Thaumcraft->common
    ThaumcraftUtilsMixin(Side.COMMON, require(TargetedMod.THAUMCRAFT), "thaumcraft.UtilsMixin"),
    //endregion Thaumcraft->common
    //region Tropicraft->common
    GenLayerTropiVoronoiZoomMixin(Side.COMMON, require(TargetedMod.TROPICRAFT),
                                  "tropicraft.GenLayerTropiVoronoiZoomMixin"),
    //endregion Tropicraft->common
    //region TwilightForest->common
    TFBiomeBaseMixin(Side.COMMON, require(TargetedMod.TWILIGHTFOREST), "twilightforest.TFBiomeBaseMixin"),
    //endregion TwilightForest->common
    //region UBC->common
    UBCOreUBifierMixin(Side.COMMON, require(TargetedMod.UBC), "ubc.OreUBifierMixin"),
    UBCBiomeUndergroundDecoratorMixin(Side.COMMON, require(TargetedMod.UBC), "ubc.BiomeUndergroundDecoratorMixin"),
    //endregion UBC->common
    //region WorldEdit->common
    WorldEditBaseBlockMixin(Side.COMMON, require(TargetedMod.WORLDEDIT), "worldedit.BaseBlockMixin"),
    //endregion WorldEdit->common
    ;
    @Getter
    private final Side side;
    @Getter
    private final Predicate<List<ITargetedMod>> filter;
    @Getter
    private final String mixin;
}

