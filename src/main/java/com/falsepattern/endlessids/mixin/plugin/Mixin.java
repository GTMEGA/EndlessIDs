package com.falsepattern.endlessids.mixin.plugin;

import com.falsepattern.endlessids.IEConfig;
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
    BiomesGenBasePlaceholderMixin(Side.COMMON, avoid(TargetedMod.ANTIIDCONFLICT),
                                  "vanilla.biome.BiomeGenBasePlaceholderMixin"),
    //endregion Minecraft->common->biome
    //region Minecraft->common->misc
    BlockFireMixin(Side.COMMON, always(), "vanilla.misc.BlockFireMixin"),
    DataWatcherMixin(Side.COMMON, condition(() -> IEConfig.extendDataWatcher), "vanilla.misc.DataWatcherMixin"),
    ItemInWorldManagerMixin(Side.COMMON, always(), "vanilla.misc.ItemInWorldManagerMixin"),
    StatListMixin(Side.COMMON, always(), "vanilla.misc.StatListMixin"),
    WorldMixin(Side.COMMON, always(), "vanilla.misc.WorldMixin"),
    //endregion Minecraft->common->misc
    //region Minecraft->common->worldgen
    ChunkProviderEndMixin(Side.COMMON, always(), "vanilla.worldgen.ChunkProviderEndMixin"),
    ChunkProviderFlatMixin(Side.COMMON, always(), "vanilla.worldgen.ChunkProviderFlatMixin"),
    ChunkProviderGenerateMixin(Side.COMMON, always(), "vanilla.worldgen.ChunkProviderGenerateMixin"),
    ChunkProviderHellMixin(Side.COMMON, always(), "vanilla.worldgen.ChunkProviderHellMixin"),
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
    //region AntiIDConflict->common
    AIDCBiomesGenBasePlaceholderMixin(Side.COMMON, require(TargetedMod.ANTIIDCONFLICT),
                                      "antiidconflict.BiomeGenBasePlaceholderMixin"),
    //endregion AntiIDConflict->common
    //region ATG->common
    ATGBiomeConfigMixin(Side.COMMON, require(TargetedMod.ATG), "atg.ATGBiomeConfigMixin"),
    ATGBiomeManagerMixin(Side.COMMON, require(TargetedMod.ATG), "atg.ATGBiomeManagerMixin"),
    ATGChunkProviderMixin(Side.COMMON, require(TargetedMod.ATG), "atg.ATGChunkProviderMixin"),
    ATGWorldGenRocksMixin(Side.COMMON, require(TargetedMod.ATG), "atg.ATGWorldGenRocksMixin"),
    //endregion ATG->common
    //region BoP->common
    BoPBiomeManagerMixin(Side.COMMON, require(TargetedMod.BOP), "bop.BoPBiomeManagerMixin"),
    //endregion BoP->common
    //region CoFHLib->common
    CoFHLibCofhBlockHelperMixin(Side.COMMON, require(TargetedMod.COFHLIB), "cofhlib.BlockHelperMixin"),
    //endregion CoFHLib->common
    //region DragonAPI->common
    //region DragonAPI->common->vanilla
    DragonAPIGenLayerRiverMixMixin(Side.COMMON, require(TargetedMod.DRAGONAPI),
                                   "dragonapi.vanilla.GenLayerRiverMixMixin"),
    //endregion DragonAPI->common->vanilla
    DragonAPIGenLayerRiverEventMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "dragonapi.GenLayerRiverEventMixin"),
    DragonAPIBlockPropertiesMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "dragonapi.BlockPropertiesMixin"),
    DragonAPIIDTypeMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "dragonapi.IDTypeMixin"),
    //endregion DragonAPI->common
    //region GalactiCraftCore->common
    GalactiCraftCoreConfigManagerCoreMixin(Side.COMMON, require(TargetedMod.GALACTICRAFTCORE),
                                           "galacticraft.ConfigManagerCoreMixin"),
    //endregion GalactiCraftCore->common
    //region ICG->common
    ICGMysteriumPatchesFixesCaveMixin(Side.COMMON, require(TargetedMod.ICG),
                                      "icg.MysteriumPatchesFixesCaveMixin"),
    //endregion ICG->common
    //region MFQM->common
    MFQMMixin(Side.COMMON, require(TargetedMod.MFQM), "mfqm.MFQMMixin"),
    MFQMDataWatcherMixin(Side.COMMON, require(TargetedMod.MFQM).and(condition(() -> IEConfig.extendDataWatcher)),
                         "mfqm.MFQMDataWatcherMixin"),
    //endregion MFQM->common
    //region NaturesCompass->common
    NaturesCompassBiomeUtilsMixin(Side.COMMON, require(TargetedMod.NATURESCOMPASS), "naturescompass.BiomeUtilsMixin"),
    //endregion NaturesCompass->common
    //region RTG->common
    RTGChunkProviderRTGMixin(Side.COMMON, require(TargetedMod.RTG), "rtg.ChunkProviderRTGMixin"),
    RTGLandscapeGeneratorMixin(Side.COMMON, require(TargetedMod.RTG), "rtg.LandscapeGeneratorMixin"),
    RTGRealisticBiomeBaseMixin(Side.COMMON, require(TargetedMod.RTG), "rtg.RealisticBiomeBaseMixin"),
    //endregion RTG->common
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

