package com.falsepattern.endlessids.mixin.mixins.common.biome.lotr;

import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.mixin.helpers.LOTRBiomeVariantStorageShort;
import lombok.val;
import lotr.common.LOTRMod;
import lotr.common.network.LOTRPacketBiomeVariantsWatch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

@Mixin(value = LOTRPacketBiomeVariantsWatch.Handler.class,
       remap = false)
public abstract class LOTRPacketBiomeVariantsWatchHandlerMixin implements IMessageHandler<LOTRPacketBiomeVariantsWatch, IMessage> {

    /**
     * @author FalsePattern
     * @reason fix stuff
     */
    @SuppressWarnings("ConstantConditions")
    @Overwrite
    public IMessage onMessage(LOTRPacketBiomeVariantsWatch pkt, MessageContext context) {
        val packet =
                (com.falsepattern.endlessids.mixin.helpers.stubpackage.lotr.common.network.LOTRPacketBiomeVariantsWatch) (Object) pkt;
        World world = LOTRMod.proxy.getClientWorld();
        int chunkX = packet.chunkX;
        int chunkZ = packet.chunkZ;
        if (world.blockExists(chunkX << 4, 0, chunkZ << 4)) {
            LOTRBiomeVariantStorageShort.setChunkBiomeVariants(world, new ChunkCoordIntPair(chunkX, chunkZ),
                                                               Hooks.byteToShortArray(packet.variants));
        } else {
            FMLLog.severe("Client received LOTR biome variant data for nonexistent chunk at %d, %d", chunkX << 4,
                          chunkZ << 4);
        }

        return null;
    }
}
