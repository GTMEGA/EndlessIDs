package com.falsepattern.endlessids.mixin.mixins.client.blockitem.vanilla;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

@SideOnly(Side.CLIENT)
@Mixin(NetHandlerPlayClient.class)
public abstract class NetHandlerPlayClientMixin {
    @Shadow
    private WorldClient clientWorldController;

    /**
     * @author FalsePattern
     * @reason Ported code, TODO figure out a proper mixin for this (difficult)
     */
    @Overwrite
    public void handleMultiBlockChange(S22PacketMultiBlockChange packetIn) {
        int chunkX = packetIn.func_148920_c().chunkXPos * 16;
        int chunkZ = packetIn.func_148920_c().chunkZPos * 16;
        if (packetIn.func_148921_d() != null) {
            DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(packetIn.func_148921_d()));

            try {
                for (int var5 = 0; var5 < packetIn.func_148922_e(); ++var5) {
                    short pos = dataInput.readShort();
                    short idLSB = dataInput.readShort();
                    byte idMSB = dataInput.readByte();
                    int id = (idLSB & 0xFFFF) | ((idMSB & 0xFF) << 16);
                    int meta = dataInput.readShort() & 0xFFFF;
                    int x = pos >> 12 & 15;
                    int z = pos >> 8 & 15;
                    int y = pos & 255;
                    this.clientWorldController.func_147492_c(x + chunkX, y, z + chunkZ, Block.getBlockById(id), meta);
                }
            } catch (IOException ignored) {
            }
        }
    }
}
