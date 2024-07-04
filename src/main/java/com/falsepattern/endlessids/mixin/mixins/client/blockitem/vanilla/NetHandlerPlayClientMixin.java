/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2024 FalsePattern
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
