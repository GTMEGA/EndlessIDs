/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2025 FalsePattern, The MEGA Team
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
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

import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.lib.mixin.IMixin;
import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.Getter;
import lombok.val;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.ABYSSALCRAFT;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.AM2;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.ANTIIDCONFLICT;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.ANTIQUEATLAS;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.ATG;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.BIOMETWEAKER;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.BIOMEWAND;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.BLOCKPHYSICS;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.BOP;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.BUILDCRAFT;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.CLIMATECONTROL;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.COFHLIB;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.COMPACTMACHINES;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.DARKWORLD;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.DIMDOORS;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.DRAGONAPI;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.EB;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.ENDERLICIOUS;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.EREBUS;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.EXTENDEDPLANETS;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.EXTRAPLANETS;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.EXTRAUTILITIES;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.FACTORIZATION;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.FASTCRAFT;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.FUTUREPACK;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.GADOMANCY;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.GALACTICRAFTCORE;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.GALAXYSPACE;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.HBM_NTM;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.HIGHLANDS;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.ICG;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.IR3;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.JAS;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.LOTR;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.MATTERMEGADRIVE;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.MATTEROVERDRIVE;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.MFQM;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.MYSTCRAFT;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.NATURESCOMPASS;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.NETHERLICIOUS;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.NOMADICTENTS;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.OWG;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.RANDOMTHINGS;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.RESTRUCTURED;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.RTG;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.RUINS;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.RWG;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.TARDIS;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.THAUMCRAFT;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.THUTCORE;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.TMOR;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.TROPICRAFT;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.TWILIGHTFOREST;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.UBC;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.WITCHERY;
import static com.falsepattern.endlessids.mixin.plugin.TargetedMod.WORLDEDIT;
import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.avoid;
import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.condition;
import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.require;

public class Mixin {
    private Mixin() {
    }

    public enum Common implements IMixin {
        //region antiidconflict
        antiidconflict_AntiIdConflictBaseMixin(require(ANTIIDCONFLICT)),
        antiidconflict_BiomeGenBasePlaceholderMixin(require(ANTIIDCONFLICT)),
        antiidconflict_BiomesManagerMixin(require(ANTIIDCONFLICT)),
        antiidconflict_DimensionsManagerMixin(require(ANTIIDCONFLICT)),
        antiidconflict_EnchantementsManagerMixin(require(ANTIIDCONFLICT)),
        antiidconflict_EntitiesManagerMixin(require(ANTIIDCONFLICT)),
        antiidconflict_PotionsManagerMixin(require(ANTIIDCONFLICT)),
        //endregion

        //region biome
        biome_vanilla_BiomeDictionaryMixin(EXT_BIOME),
        biome_vanilla_BiomeGenBaseMixin(EXT_BIOME),
        biome_vanilla_BiomeGenBasePlaceholderMixin(
                all(EXT_BIOME, () -> GeneralConfig.biomeConflictAvoidancePlaceholders)),
        biome_vanilla_ChunkMixin(EXT_BIOME),
        biome_vanilla_GenLayerRiverMixMixin(EXT_BIOME, avoid(DRAGONAPI)),
        biome_vanilla_GenLayerVoronoiZoomMixin(EXT_BIOME),

        biome_abyssalcraft_AbyssalCraftMixin(EXT_BIOME, require(ABYSSALCRAFT)),

        biome_antiqueatlas_BiomeDetectorBaseMixin(EXT_BIOME, require(ANTIQUEATLAS)),
        biome_antiqueatlas_BiomeDetectorNetherMixin(EXT_BIOME, require(ANTIQUEATLAS)),

        biome_atg_ATGBiomeConfigMixin(EXT_BIOME, require(ATG)),
        biome_atg_ATGBiomeManagerMixin(EXT_BIOME, require(ATG)),
        biome_atg_ATGWorldGenRocksMixin(EXT_BIOME, require(ATG)),

        biome_biometweaker_BiomeEventHandlerMixin(EXT_BIOME, require(BIOMETWEAKER)),

        biome_biomewand_BiomeWandItemMixin(EXT_BIOME, require(BIOMEWAND)),

        biome_bop_BOPBiomeManagerMixin(EXT_BIOME, require(BOP)),
        biome_bop_BOPBiomesMixin(EXT_BIOME, require(BOP)),

        biome_buildcraft_BuildcraftEnergyMixin(EXT_BIOME, require(BUILDCRAFT)),

        biome_climatecontrol_api_BiomeSettingsMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_api_ClimateControlRulesMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_customGenLayer_ConfirmBiomeMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_customGenLayer_GenLayerBiomeByClimateMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_customGenLayer_GenLayerBiomeByTaggedClimateMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_customGenLayer_GenLayerDefineClimateMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_customGenLayer_GenLayerLowlandRiverMixMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_customGenLayer_GenLayerSmoothCoastMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_customGenLayer_GenLayerSubBiomeMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_generator_AbstractWorldGeneratorMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_generator_BiomeSwapperMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_generator_SubBiomeChooserMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_genLayerPack_GenLayerHillsMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_genLayerPack_GenLayerPackMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_genLayerPack_GenLayerSmoothMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_genLayerPack_GenLayerVoronoiZoomMixin(EXT_BIOME, require(CLIMATECONTROL)),
        biome_climatecontrol_genLayerPack_GenLayerZoomMixin(EXT_BIOME, require(CLIMATECONTROL)),

        biome_compactmachines_CubeToolsMixin(EXT_BIOME, require(COMPACTMACHINES)),

        biome_darkworld_ModBiomesMixin(EXT_BIOME, require(DARKWORLD)),

        biome_dimdoors_DDBiomeGenBaseMixin(EXT_BIOME, require(DIMDOORS)),

        biome_dragonapi_vanilla_GenLayerRiverMixMixin(EXT_BIOME, require(DRAGONAPI)),
        biome_dragonapi_GenLayerRiverEventMixin(EXT_BIOME, require(DRAGONAPI)),
        biome_dragonapi_IDTypeMixin(EXT_BIOME, require(DRAGONAPI)),
        biome_dragonapi_ReikaChunkHelperMixin(EXT_BIOME, require(DRAGONAPI)),
        biome_dragonapi_ReikaWorldHelperMixin(EXT_BIOME, require(DRAGONAPI)),

        biome_eb_BiomeIDsMixin(EXT_BIOME, require(EB)),
        biome_eb_GenLayerArchipelagoEdgeMixin(EXT_BIOME, require(EB)),
        biome_eb_GenLayerEBHillsMixin(EXT_BIOME, require(EB)),
        biome_eb_GenLayerEBRiverMixMixin(EXT_BIOME, require(EB)),
        biome_eb_GenLayerEBVoronoiZoomMixin(EXT_BIOME, require(EB)),

        biome_enderlicious_BiomeConfigurationMixin(EXT_BIOME, require(ENDERLICIOUS)),
        biome_enderlicious_EndChunkProviderMixin(EXT_BIOME, require(ENDERLICIOUS)),

        biome_erebus_ModBiomesMixin(EXT_BIOME, require(EREBUS)),

        biome_extendedplanets_ChunkProviderOceanMixin(EXT_BIOME, require(EXTENDEDPLANETS)),

        biome_extraplanets_ConfigMixin(EXT_BIOME, require(EXTRAPLANETS)),

        biome_extrautilities_ChunkProviderEndOfTimeMixin(EXT_BIOME, require(EXTRAUTILITIES)),

        biome_factorization_HammerChunkProviderMixin(EXT_BIOME, require(FACTORIZATION)),

        biome_futurepack_BiomeGenSpaceMixin(EXT_BIOME, require(FUTUREPACK)),

        biome_gadomancy_ChunkProviderTCOuterMixin(EXT_BIOME, require(GADOMANCY)),

        biome_galacticraft_ConfigManagerCoreMixin(EXT_BIOME, require(GALACTICRAFTCORE)),

        biome_galaxyspace_ChunkProviderKuiperMixin(EXT_BIOME, require(GALAXYSPACE)),
        biome_galaxyspace_ChunkProviderMarsSSMixin(EXT_BIOME, require(GALAXYSPACE)),
        biome_galaxyspace_ChunkProviderSpaceLakesMixin(EXT_BIOME, require(GALAXYSPACE)),
        biome_galaxyspace_ChunkProviderVenusSSMixin(EXT_BIOME, require(GALAXYSPACE)),

        biome_highlands_ConfigMixin(EXT_BIOME, require(HIGHLANDS)),

        biome_icg_MysteriumPatchesFixesCaveMixin(EXT_BIOME, require(ICG)),

        biome_ir3_IR2Mixin(EXT_BIOME, require(IR3)),

        biome_jas_LegacyCreatureTypeMixin(EXT_BIOME, require(JAS)),
        biome_jas_ModernCreatureTypeMixin(EXT_BIOME, require(JAS)),

        biome_lotr_LOTRBiomeVariantStorageMixin(EXT_BIOME, require(LOTR)),
        biome_lotr_LOTRChunkProviderMixin(EXT_BIOME, require(LOTR)),
        biome_lotr_LOTRChunkProviderUtumnoMixin(EXT_BIOME, require(LOTR)),
        biome_lotr_LOTRDimensionMixin(EXT_BIOME, require(LOTR)),
        biome_lotr_LOTRPacketBiomeVariantsWatchHandlerMixin(EXT_BIOME, require(LOTR)),
        biome_lotr_LOTRWorldChunkManagerMixin(EXT_BIOME, require(LOTR)),
        biome_lotr_LOTRWorldProviderMixin(EXT_BIOME, require(LOTR)),

        biome_mystcraft_BiomeReplacerMixin(EXT_BIOME, require(MYSTCRAFT)),

        biome_naturescompass_BiomeUtilsMixin(EXT_BIOME, require(NATURESCOMPASS)),

        biome_netherlicious_BiomeConfigurationMixin(EXT_BIOME, require(NETHERLICIOUS)),

        biome_nomadictents_TentChunkProviderMixin(EXT_BIOME, require(NOMADICTENTS)),

        biome_owg_ChunkGeneratorBetaMixin(EXT_BIOME, require(OWG)),

        biome_randomthings_ItemBiomePainterMixin(EXT_BIOME, require(RANDOMTHINGS)),
        biome_randomthings_MessagePaintBiomeMixin(EXT_BIOME, require(RANDOMTHINGS)),

        biome_restructured_BiomeHelperMixin(EXT_BIOME, require(RESTRUCTURED)),
        biome_rtg_ChunkProviderRTGMixin(EXT_BIOME, require(RTG)),
        biome_rtg_LandscapeGeneratorMixin(EXT_BIOME, require(RTG)),
        biome_rtg_WorldChunkManagerRTGMixin(EXT_BIOME, require(RTG)),

        biome_ruins_FileHandlerMixin(EXT_BIOME, require(RUINS)),
        biome_ruins_LoaderThreadMixin(EXT_BIOME, require(RUINS)),
        biome_ruins_RuinGeneratorMixin(EXT_BIOME, require(RUINS)),

        biome_rwg_ChunkGeneratorRealisticMixin(EXT_BIOME, require(RWG)),
        biome_rwg_RealisticBiomeBaseMixin(EXT_BIOME, require(RWG)),
        biome_rwg_VillageMaterialsMixin(EXT_BIOME, require(RWG)),

        biome_tardismod_TardisChunkProviderMixin(EXT_BIOME, require(TARDIS)),

        biome_thaumcraft_UtilsMixin(EXT_BIOME, require(THAUMCRAFT)),

        biome_thut_Vector3Mixin(EXT_BIOME, require(THUTCORE)),

        biome_tmor_ChunkProviderSanctuatiteMixin(EXT_BIOME, require(TMOR)),

        biome_tropicraft_GenLayerTropiVoronoiZoomMixin(EXT_BIOME, require(TROPICRAFT)),

        biome_twilightforest_TFBiomeBaseMixin(EXT_BIOME, require(TWILIGHTFOREST)),

        biome_witchery_WorldChunkManagerMirrorMixin(EXT_BIOME, require(WITCHERY)),
        biome_witchery_BrewActionBiomeChangeMixin(EXT_BIOME, require(WITCHERY)),

        biome_worldedit_ForgeWorldMixin(EXT_BIOME, require(WORLDEDIT)),
        //endregion

        //region blockitem
        blockitem_vanilla_BlockFireMixin(EXT_BLOCK_ITEM),
        blockitem_vanilla_BlockMixin(EXT_BLOCK_ITEM),
        blockitem_vanilla_ExtendedBlockStorageMixin(EXT_BLOCK_ITEM),
        blockitem_vanilla_ItemInWorldManagerMixin(EXT_BLOCK_ITEM),
        blockitem_vanilla_ItemStackMixin(EXT_BLOCK_ITEM),
        blockitem_vanilla_PacketBufferMixin(EXT_BLOCK_ITEM),
        blockitem_vanilla_S22PacketMultiBlockChangeMixin(EXT_BLOCK_ITEM),
        blockitem_vanilla_S24PacketBlockActionMixin(EXT_BLOCK_ITEM),
        blockitem_vanilla_StatListMixin(EXT_BLOCK_ITEM),
        blockitem_vanilla_WorldMixin(EXT_BLOCK_ITEM),

        blockitem_blockphysics_DefinitionMapsMixin(EXT_BLOCK_ITEM, require(BLOCKPHYSICS)),

        blockitem_cofhlib_BlockHelperMixin(EXT_BLOCK_ITEM, require(COFHLIB)),

        blockitem_dragonapi_BlockPropertiesMixin(EXT_BLOCK_ITEM, require(DRAGONAPI)),
        blockitem_dragonapi_IDTypeMixin(EXT_BLOCK_ITEM, require(DRAGONAPI)),

        blockitem_ntm_ChunkRadiationHandlerPRISMMixin(EXT_BLOCK_ITEM, require(HBM_NTM)),
        blockitem_ntm_ChunkRadiationHandlerPRISMSubChunkMixin(EXT_BLOCK_ITEM, require(HBM_NTM)),

        blockitem_matteroverdrive_InventoryMixin(EXT_BLOCK_ITEM, require(MATTEROVERDRIVE).or(require(MATTERMEGADRIVE))),
        blockitem_matteroverdrive_ItemPatternMixin(EXT_BLOCK_ITEM,
                                                   require(MATTEROVERDRIVE).or(require(MATTERMEGADRIVE))),

        blockitem_mfqm_MFQMMixin(EXT_BLOCK_ITEM, require(MFQM)),

        blockitem_worldedit_BaseBlockMixin(EXT_BLOCK_ITEM, require(WORLDEDIT)),

        blockitem_ubc_OreUBifierMixin(EXT_BLOCK_ITEM, require(UBC)),
        blockitem_ubc_BiomeUndergroundDecoratorMixin(EXT_BLOCK_ITEM, require(UBC)),
        //endregion

        //region datawatcher
        datawatcher_vanilla_DataWatcherMixin(EXT_DATA_WATCHER),

        datawatcher_mfqm_MFQMDataWatcherMixin(EXT_DATA_WATCHER, require(MFQM)),
        //endregion

        //region enchantment
        enchantment_vanilla_EnchantmentMixin(EXT_ENCHANTMENT),
        //endregion

        //region entity
        entity_vanilla_EntityListMixin(EXT_ENTITY),
        entity_vanilla_EntityRegistryMixin(EXT_ENTITY),
        entity_vanilla_S0FPacketSpawnMobMixin(EXT_ENTITY),
        //endregion

        //region potion
        potion_vanilla_PotionEffectMixin(EXT_POTION),
        potion_vanilla_PotionMixin(EXT_POTION),
        potion_vanilla_network_S1DPacketEntityEffectMixin(EXT_POTION, avoid(AM2)),
        potion_vanilla_network_S1EPacketRemoveEntityEffectMixin(EXT_POTION, avoid(AM2)),

        potion_thaumcraft_ConfigMixin(EXT_POTION, require(THAUMCRAFT)),
        //endregion

        //region redstone
        redstone_BlockCompressedPoweredMixin(all(EXT_BLOCK_ITEM, EXT_REDSTONE)),
        redstone_BlockRedstoneDiodeMixin(all(EXT_BLOCK_ITEM, EXT_REDSTONE)),
        redstone_BlockRedstoneTorchMixin(all(EXT_BLOCK_ITEM, EXT_REDSTONE)),
        redstone_WorldMixin(all(EXT_BLOCK_ITEM, EXT_REDSTONE)),
        //endregion
        ;

        //region boilerplate
        @Getter
        private final Predicate<List<ITargetedMod>> filter;
        @Getter
        private final String mixin;

        Common(Predicate<List<ITargetedMod>> predicate) {
            filter = predicate;
            mixin = buildMixin(name());
        }

        Common(Supplier<Boolean> condition) {
            filter = buildPredicate(condition);
            mixin = buildMixin(name());
        }

        Common(Supplier<Boolean> condition, Predicate<List<ITargetedMod>> predicate) {
            filter = buildPredicate(condition).and(predicate);
            mixin = buildMixin(name());
        }

        @Override
        public Side getSide() {
            return Side.COMMON;
        }
        //endregion
    }

    public enum Client implements IMixin {
        //region biome
        biome_biomewand_BiomeWandItemMixin(EXT_BIOME, require(BIOMEWAND)),
        //endregion

        //region blockitem
        blockitem_vanilla_NetHandlerPlayClientMixin(EXT_BLOCK_ITEM),
        blockitem_vanilla_PlayerControllerMPMixin(EXT_BLOCK_ITEM),
        blockitem_vanilla_RenderGlobalMixin(EXT_BLOCK_ITEM),

        blockitem_fastcraft_EntityRendererMixin(EXT_BLOCK_ITEM, require(FASTCRAFT)),
        //endregion

        //region potion
        potion_vanilla_NetHandlerPlayClientMixin(EXT_POTION, avoid(AM2)),
        //endregion

        //region redstone
        redstone_BlockRedstoneWireMixin(all(EXT_BLOCK_ITEM, EXT_REDSTONE)),
        redstone_RenderBlocksMixin(all(EXT_BLOCK_ITEM, EXT_REDSTONE)),
        //endregion
        ;

        //region boilerplate
        @Getter
        private final Predicate<List<ITargetedMod>> filter;
        @Getter
        private final String mixin;

        Client(Supplier<Boolean> condition) {
            filter = buildPredicate(condition);
            mixin = buildMixin(name());
        }

        Client(Supplier<Boolean> condition, Predicate<List<ITargetedMod>> predicate) {
            filter = buildPredicate(condition).and(predicate);
            mixin = buildMixin(name());
        }

        @Override
        public Side getSide() {
            return Side.CLIENT;
        }
        //endregion
    }

    public static IMixin[] values() {
        val common = Common.values();
        val client = Client.values();
        val result = new IMixin[common.length + client.length];
        System.arraycopy(common, 0, result, 0, common.length);
        System.arraycopy(client, 0, result, common.length, client.length);
        return result;
    }

    //region boilerplate
    private static Predicate<List<ITargetedMod>> buildPredicate(Supplier<Boolean> condition) {
        return condition(condition);
    }

    private static String buildMixin(String name) {
        return name.replace('_', '.');
    }

    private static final Supplier<Boolean> EXT_BIOME = () -> GeneralConfig.extendBiome;
    private static final Supplier<Boolean> EXT_BLOCK_ITEM = () -> GeneralConfig.extendBlockItem;
    private static final Supplier<Boolean> EXT_DATA_WATCHER = () -> GeneralConfig.extendDataWatcher;
    private static final Supplier<Boolean> EXT_ENCHANTMENT = () -> GeneralConfig.extendEnchantment;
    private static final Supplier<Boolean> EXT_POTION = () -> GeneralConfig.extendPotion;
    private static final Supplier<Boolean> EXT_ENTITY = () -> GeneralConfig.extendEntity;
    private static final Supplier<Boolean> EXT_REDSTONE = () -> GeneralConfig.extendRedstone;

    @SafeVarargs
    private static Supplier<Boolean> all(Supplier<Boolean>... conds) {
        return () -> {
            for (Supplier<Boolean> cond : conds) {
                if (!cond.get()) {
                    return false;
                }
            }
            return true;
        };
    }
    //endregion
}

