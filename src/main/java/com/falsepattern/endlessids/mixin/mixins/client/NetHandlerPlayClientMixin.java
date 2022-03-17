package com.falsepattern.endlessids.mixin.mixins.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

@SideOnly(Side.CLIENT)
@Mixin(NetHandlerPlayClient.class)
public abstract class NetHandlerPlayClientMixin {

    @Shadow private WorldClient clientWorldController;

    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    public void handleMultiBlockChange(S22PacketMultiBlockChange p_147287_1_) throws IOException {
        int var2 = p_147287_1_.func_148920_c().chunkXPos * 16;
        int var3 = p_147287_1_.func_148920_c().chunkZPos * 16;
        if (p_147287_1_.func_148921_d() != null) {
            DataInputStream var4 = new DataInputStream(new ByteArrayInputStream(p_147287_1_.func_148921_d()));

            for(int var5 = 0; var5 < p_147287_1_.func_148922_e(); ++var5) {
                short var6 = var4.readShort();
                short var8 = var4.readShort();
                int var9 = var4.readByte() & 15;
                int var10 = var6 >> 12 & 15;
                int var11 = var6 >> 8 & 15;
                int var12 = var6 & 255;
                this.clientWorldController.func_147492_c(var10 + var2, var12, var11 + var3, Block.getBlockById(var8), var9);
            }
        }

    }
}
