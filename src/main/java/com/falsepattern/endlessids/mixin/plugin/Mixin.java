package com.falsepattern.endlessids.mixin.plugin;

import com.falsepattern.endlessids.IEConfig;
import cpw.mods.fml.relauncher.FMLLaunchHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public enum Mixin {
    //region Minecraft
        //region common
            //region storage
                AnvilChunkLoaderMixin(builder(Side.COMMON).mixin("vanilla.storage.AnvilChunkLoaderMixin")),
                ExtendedBlockStorageMixin(builder(Side.COMMON).mixin("vanilla.storage.ExtendedBlockStorageMixin")),
                ItemStackMixin(builder(Side.COMMON).mixin("vanilla.storage.ItemStackMixin")),
            //endregion storage
            //region networking
                PacketBufferMixin(builder(Side.COMMON).mixin("vanilla.networking.PacketBufferMixin")),
                PlayerControllerMPMixin(builder(Side.COMMON).mixin("vanilla.networking.PlayerControllerMPMixin")),
                S21PacketChunkDataMixin(builder(Side.COMMON).mixin("vanilla.networking.S21PacketChunkDataMixin")),
                S22PacketMultiBlockChangeMixin(builder(Side.COMMON).mixin("vanilla.networking.S22PacketMultiBlockChangeMixin")),
                S24PacketBlockActionMixin(builder(Side.COMMON).mixin("vanilla.networking.S24PacketBlockActionMixin")),
                S26PacketMapChunkBulkMixin(builder(Side.COMMON).mixin("vanilla.networking.S26PacketMapChunkBulkMixin")),
            //endregion networking
            //region misc
                BlockFireMixin(builder(Side.COMMON).mixin("vanilla.misc.BlockFireMixin")),
                DataWatcherMixin(builder(Side.COMMON).condition(() -> IEConfig.extendDataWatcher).mixin("vanilla.misc.DataWatcherMixin")),
                ItemInWorldManagerMixin(builder(Side.COMMON).mixin("vanilla.misc.ItemInWorldManagerMixin")),
                StatListMixin(builder(Side.COMMON).mixin("vanilla.misc.StatListMixin")),
                WorldMixin(builder(Side.COMMON).mixin("vanilla.misc.WorldMixin")),
            //endregion misc
        //endregion common
        //region client
            ChunkMixin(builder(Side.CLIENT).mixin("vanilla.ChunkMixin")),
            NetHandlerPlayClientMixin(builder(Side.CLIENT).mixin("vanilla.NetHandlerPlayClientMixin")),
            RenderGlobalMixin(builder(Side.CLIENT).mixin("vanilla.RenderGlobalMixin")),
        //endregion client
    //endregion Minecraft
    //region UBC->common
        OreUBifierMixin(builder(Side.COMMON).target(TargetedMod.UBC).mixin("ubc.OreUBifierMixin")),
        BiomeUndergroundDecoratorMixin(builder(Side.COMMON).target(TargetedMod.UBC).mixin("ubc.BiomeUndergroundDecoratorMixin")),
    //endregion UBC->common
    //region CoFHLib->common
        CofhBlockHelperMixin(builder(Side.COMMON).target(TargetedMod.COFHLIB).mixin("cofhlib.BlockHelperMixin")),
    //endregion CoFHLib->common
    //region DragonAPI->common
        BlockPropertiesMixin(builder(Side.COMMON).target(TargetedMod.DRAGONAPI).mixin("dragonapi.BlockPropertiesMixin")),
    //endregion DragonAPI->common
    //region MFQM->common
        MFQMMixin(builder(Side.COMMON).target(TargetedMod.MFQM).mixin("mfqm.MFQMMixin")),
        MFQMDataWatcherMixin(builder(Side.COMMON).target(TargetedMod.MFQM).condition(() -> IEConfig.extendDataWatcher).mixin("mfqm.MFQMDataWatcherMixin")),
    //endregion MFQM->common
    //region WorldEdit->common
        BaseBlockMixin(builder(Side.COMMON).target(TargetedMod.WORLDEDIT).mixin("worldedit.BaseBlockMixin")),
    //endregion WorldEdit->common
    ;

    public final String mixin;
    public final Set<TargetedMod> targetedMods;
    public final Set<TargetedMod> avoidedMods;
    public final Set<Supplier<Boolean>> allowConditions;
    private final Side side;

    Mixin(Builder builder) {
        this.mixin = builder.mixin;
        this.targetedMods = builder.targetedMods;
        this.avoidedMods = builder.avoidedMods;
        this.allowConditions = builder.allowConditions;
        this.side = builder.side;
    }

    public boolean shouldLoad(List<TargetedMod> loadedMods) {
        return (side == Side.COMMON
                || side == Side.SERVER && FMLLaunchHandler.side().isServer()
                || side == Side.CLIENT && FMLLaunchHandler.side().isClient())
               && loadedMods.containsAll(targetedMods)
               && avoidedMods.stream().noneMatch(loadedMods::contains)
               && allowConditions.stream().allMatch(Supplier::get);
    }


    @SuppressWarnings("SameParameterValue")
    private static Builder builder(Side side) {
        return new Builder(side);
    }

    private static class Builder {
        public String mixin;
        public Side side;
        public final Set<TargetedMod> targetedMods = new HashSet<>();
        public final Set<TargetedMod> avoidedMods = new HashSet<>();
        public final Set<Supplier<Boolean>> allowConditions = new HashSet<>();

        public Builder(Side side) {
            this.side = side;
        }

        public Builder mixin(String mixinClass) {
            mixin = side.name().toLowerCase() + "." + mixinClass;
            return this;
        }

        public Builder target(TargetedMod mod) {
            targetedMods.add(mod);
            return this;
        }

        public Builder avoid(TargetedMod mod) {
            avoidedMods.add(mod);
            return this;
        }

        public Builder condition(Supplier<Boolean> cond) {
            allowConditions.add(cond);
            return this;
        }
    }

    private enum Side {
        COMMON,
        CLIENT,
        SERVER
    }
}

