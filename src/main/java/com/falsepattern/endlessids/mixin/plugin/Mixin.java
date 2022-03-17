package com.falsepattern.endlessids.mixin.plugin;

import com.falsepattern.endlessids.IEConfig;
import cpw.mods.fml.relauncher.FMLLaunchHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public enum Mixin {
    AnvilChunkLoaderMixin(builder(Side.COMMON).mixin("AnvilChunkLoaderMixin")),
    ChunkMixin(builder(Side.COMMON).mixin("ChunkMixin")),
    DataWatcherMixin(builder(Side.COMMON).condition(() -> IEConfig.extendDataWatcher).mixin("DataWatcherMixin")),
    StatListMixin(builder(Side.COMMON).mixin("StatListMixin")),
    BlockFireMixin(builder(Side.COMMON).mixin("BlockFireMixin")),
    WorldMixin(builder(Side.COMMON).mixin("WorldMixin")),
    ExtendedBlockStorageMixin(builder(Side.COMMON).mixin("ExtendedBlockStorageMixin")),
    S21PacketChunkDataMixin(builder(Side.COMMON).mixin("S21PacketChunkDataMixin")),
    S22PacketMultiBlockChangeMixin(builder(Side.COMMON).mixin("S22PacketMultiBlockChangeMixin")),
    S24PacketBlockActionMixin(builder(Side.COMMON).mixin("S24PacketBlockActionMixin")),
    S26PacketMapChunkBulkMixin(builder(Side.COMMON).mixin("S26PacketMapChunkBulkMixin")),
    PlayerControllerMPMixin(builder(Side.COMMON).mixin("PlayerControllerMPMixin")),
    ItemInWorldManagerMixin(builder(Side.COMMON).mixin("ItemInWorldManagerMixin")),
    NetHandlerPlayClientMixin(builder(Side.CLIENT).mixin("NetHandlerPlayClientMixin")),
    RenderGlobalMixin(builder(Side.CLIENT).mixin("RenderGlobalMixin")),
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

