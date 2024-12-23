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

package com.falsepattern.endlessids.mixin.mixins.common.biome.ntm;

import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import com.falsepattern.endlessids.mixin.helpers.stubpackage.com.hbm.packet.BiomeSyncPacket;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

@Mixin(value = com.hbm.packet.BiomeSyncPacket.Handler.class,
       remap = false)
public abstract class BiomeSyncPacket_HandlerMixin implements IMessageHandler<com.hbm.packet.BiomeSyncPacket, IMessage> {
    @Inject(method = "onMessage(Lcom/hbm/packet/BiomeSyncPacket;Lcpw/mods/fml/common/network/simpleimpl/MessageContext;)Lcpw/mods/fml/common/network/simpleimpl/IMessage;",
            at = @At(value = "HEAD"),
            cancellable = true,
            require = 1)
    private void onMessage(com.hbm.packet.BiomeSyncPacket $m, MessageContext ctx, CallbackInfoReturnable<IMessage> cir) {
        val world = Minecraft.getMinecraft().theWorld;
        val m = (BiomeSyncPacket) $m;
        if (world.getChunkProvider().chunkExists(m.chunkX, m.chunkZ)) {
            val chunk = world.getChunkFromChunkCoords(m.chunkX, m.chunkZ);
            val chunkHook = (ChunkBiomeHook) chunk;
            chunk.isModified = true;
            val biomeArray = m.eid$getBiomeArray();
            val chunkBiomeArray = chunkHook.getBiomeShortArray();
            if (biomeArray == null) {
                val bX = m.blockX & 0xf;
                val bZ = m.blockZ & 0xf;
                chunkBiomeArray[bZ << 4 | bX] = m.eid$getBiome();
                val cX = (m.chunkX << 4) + bX;
                val cZ = (m.chunkZ << 4) + bZ;
                world.markBlockRangeForRenderUpdate(cX, 0, cZ, cX, 255, cZ);
            } else {
                System.arraycopy(biomeArray, 0, chunkBiomeArray, 0, 256);
                val cX = m.chunkX << 4;
                val cZ = m.chunkZ << 4;
                world.markBlockRangeForRenderUpdate(cX, 0, cZ, cX + 15, 255, cZ + 15);
            }
        }
        cir.setReturnValue(null);
    }
}
