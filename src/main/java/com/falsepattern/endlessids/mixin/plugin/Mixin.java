package com.falsepattern.endlessids.mixin.plugin;

import com.falsepattern.endlessids.IEConfig;
import cpw.mods.fml.relauncher.FMLLaunchHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public enum Mixin {
    //BEGIN Minecraft
        //BEGIN common
            //BEGIN storage
                AnvilChunkLoaderMixin(builder(Side.COMMON).mixin("vanilla.storage.AnvilChunkLoaderMixin")),
                ExtendedBlockStorageMixin(builder(Side.COMMON).mixin("vanilla.storage.ExtendedBlockStorageMixin")),
                ItemStackMixin(builder(Side.COMMON).mixin("vanilla.storage.ItemStackMixin")),
            //END storage
            //BEGIN networking
                PlayerControllerMPMixin(builder(Side.COMMON).mixin("vanilla.networking.PlayerControllerMPMixin")),
                S21PacketChunkDataMixin(builder(Side.COMMON).mixin("vanilla.networking.S21PacketChunkDataMixin")),
                S22PacketMultiBlockChangeMixin(builder(Side.COMMON).mixin("vanilla.networking.S22PacketMultiBlockChangeMixin")),
                S24PacketBlockActionMixin(builder(Side.COMMON).mixin("vanilla.networking.S24PacketBlockActionMixin")),
                S26PacketMapChunkBulkMixin(builder(Side.COMMON).mixin("vanilla.networking.S26PacketMapChunkBulkMixin")),
            //END networking
            //BEGIN misc
                BlockFireMixin(builder(Side.COMMON).mixin("vanilla.misc.BlockFireMixin")),
                DataWatcherMixin(builder(Side.COMMON).condition(() -> IEConfig.extendDataWatcher).mixin("vanilla.misc.DataWatcherMixin")),
                ItemInWorldManagerMixin(builder(Side.COMMON).mixin("vanilla.misc.ItemInWorldManagerMixin")),
                PacketBufferMixin(builder(Side.COMMON).mixin("vanilla.misc.PacketBufferMixin")),
                StatListMixin(builder(Side.COMMON).mixin("vanilla.misc.StatListMixin")),
                WorldMixin(builder(Side.COMMON).mixin("vanilla.misc.WorldMixin")),
            //END misc
        //END common
        //BEGIN client
            ChunkMixin(builder(Side.CLIENT).mixin("vanilla.ChunkMixin")),
            NetHandlerPlayClientMixin(builder(Side.CLIENT).mixin("vanilla.NetHandlerPlayClientMixin")),
            RenderGlobalMixin(builder(Side.CLIENT).mixin("vanilla.RenderGlobalMixin")),
        //END client
    //END Minecraft
    //BEGIN UBC
        //BEGIN common
            OreUBifierMixin(builder(Side.COMMON).target(TargetedMod.UBC).mixin("ubc.OreUBifierMixin"))
        //END common
    //END ubc
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

