package com.falsepattern.endlessids.mixin.plugin;

import com.falsepattern.endlessids.IEConfig;
import com.falsepattern.lib.mixin.IMixin;
import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.*;

@RequiredArgsConstructor
public enum Mixin implements IMixin {
    //region Minecraft
        //region common
            //region storage
                AnvilChunkLoaderMixin(Side.COMMON, always(), "vanilla.storage.AnvilChunkLoaderMixin"),
                ExtendedBlockStorageMixin(Side.COMMON, always(), "vanilla.storage.ExtendedBlockStorageMixin"),
                ItemStackMixin(Side.COMMON, always(), "vanilla.storage.ItemStackMixin"),
            //endregion storage
            //region networking
                PacketBufferMixin(Side.COMMON, always(), "vanilla.networking.PacketBufferMixin"),
                S21PacketChunkDataMixin(Side.COMMON, always(), "vanilla.networking.S21PacketChunkDataMixin"),
                S22PacketMultiBlockChangeMixin(Side.COMMON, always(), "vanilla.networking.S22PacketMultiBlockChangeMixin"),
                S24PacketBlockActionMixin(Side.COMMON, always(), "vanilla.networking.S24PacketBlockActionMixin"),
                S26PacketMapChunkBulkMixin(Side.COMMON, always(), "vanilla.networking.S26PacketMapChunkBulkMixin"),
            //endregion networking
            //region misc
                BlockFireMixin(Side.COMMON, always(), "vanilla.misc.BlockFireMixin"),
                DataWatcherMixin(Side.COMMON, condition(() -> IEConfig.extendDataWatcher), "vanilla.misc.DataWatcherMixin"),
                ItemInWorldManagerMixin(Side.COMMON, always(), "vanilla.misc.ItemInWorldManagerMixin"),
                StatListMixin(Side.COMMON, always(), "vanilla.misc.StatListMixin"),
                WorldMixin(Side.COMMON, always(), "vanilla.misc.WorldMixin"),
            //endregion misc
        //endregion common
        //region client
            ChunkMixin(Side.CLIENT, always(), "vanilla.ChunkMixin"),
            NetHandlerPlayClientMixin(Side.CLIENT, always(), "vanilla.NetHandlerPlayClientMixin"),
            PlayerControllerMPMixin(Side.CLIENT, always(), "vanilla.PlayerControllerMPMixin"),
            RenderGlobalMixin(Side.CLIENT, always(), "vanilla.RenderGlobalMixin"),
        //endregion client
    //endregion Minecraft
    //region UBC->common
        OreUBifierMixin(Side.COMMON, require(TargetedMod.UBC), "ubc.OreUBifierMixin"),
        BiomeUndergroundDecoratorMixin(Side.COMMON, require(TargetedMod.UBC), "ubc.BiomeUndergroundDecoratorMixin"),
    //endregion UBC->common
    //region CoFHLib->common
        CofhBlockHelperMixin(Side.COMMON, require(TargetedMod.COFHLIB), "cofhlib.BlockHelperMixin"),
    //endregion CoFHLib->common
    //region DragonAPI->common
        BlockPropertiesMixin(Side.COMMON, require(TargetedMod.DRAGONAPI), "dragonapi.BlockPropertiesMixin"),
    //endregion DragonAPI->common
    //region MFQM->common
        MFQMMixin(Side.COMMON, require(TargetedMod.MFQM), "mfqm.MFQMMixin"),
        MFQMDataWatcherMixin(Side.COMMON, require(TargetedMod.MFQM).and(condition(() -> IEConfig.extendDataWatcher)), "mfqm.MFQMDataWatcherMixin"),
    //endregion MFQM->common
    //region WorldEdit->common
        BaseBlockMixin(Side.COMMON, require(TargetedMod.WORLDEDIT), "worldedit.BaseBlockMixin"),
    //endregion WorldEdit->common
    ;
    @Getter
    private final Side side;
    @Getter
    private final Predicate<List<ITargetedMod>> filter;
    @Getter
    private final String mixin;
}

