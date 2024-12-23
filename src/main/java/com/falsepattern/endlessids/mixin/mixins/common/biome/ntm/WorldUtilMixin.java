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

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import com.falsepattern.endlessids.mixin.helpers.IHBMBiomeSyncPacket;
import com.hbm.packet.BiomeSyncPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.world.WorldUtil;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mixin(value = WorldUtil.class,
       remap = false)
public abstract class WorldUtilMixin {
    @Inject(method = "setBiome",
            at = @At("HEAD"),
            cancellable = true,
            require = 1)
    private static void extendSetBiome(World world, int x, int z, BiomeGenBase biome, CallbackInfo ci) {
        ci.cancel();
        val chunk = world.getChunkFromBlockCoords(x, z);
        ((ChunkBiomeHook)chunk).getBiomeShortArray()[(z & 0xf) << 4 | x & 0xf] =
                (short) (biome.biomeID & ExtendedConstants.biomeIDMask);
        chunk.isModified = true;
    }

    @Inject(method = "syncBiomeChange(Lnet/minecraft/world/World;II)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 1)
    private static void extendSyncBiomeChange(World world, int x, int z, CallbackInfo ci) {
        ci.cancel();
        Chunk chunk = world.getChunkFromBlockCoords(x, z);
        val packet = new BiomeSyncPacket(x >> 4, z >> 4, null);
        ((IHBMBiomeSyncPacket)packet).eid$setBiomeArray(((ChunkBiomeHook)chunk).getBiomeShortArray());
        PacketDispatcher.wrapper.sendToAllAround(packet, new NetworkRegistry.TargetPoint(world.provider.dimensionId,
                                                                                         x, 128.0F, z,
                                                                                         1024.0F));
    }

    @Inject(method = "syncBiomeChangeBlock",
            at = @At("HEAD"),
            cancellable = true,
            require = 1)
    private static void extendSyncBiomeChangeBlock(World world, int x, int z, CallbackInfo ci) {
        ci.cancel();
        Chunk chunk = world.getChunkFromBlockCoords(x, z);
        short biome = ((ChunkBiomeHook)chunk).getBiomeShortArray()[(z & 15) << 4 | x & 15];
        val packet = new BiomeSyncPacket(x, z, (byte)0);
        ((IHBMBiomeSyncPacket)packet).eid$setBiome(biome);
        PacketDispatcher.wrapper.sendToAllAround(packet, new NetworkRegistry.TargetPoint(world.provider.dimensionId,
                                                                                         x, 128.0F, z,
                                                                                         1024.0F));
    }

    @Inject(method = "syncBiomeChange(Lnet/minecraft/world/World;Lnet/minecraft/world/chunk/Chunk;)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 1)
    private static void extendSyncBiomeChange(World world, Chunk chunk, CallbackInfo ci) {
        ci.cancel();
        ChunkCoordIntPair coord = chunk.getChunkCoordIntPair();
        val packet = new BiomeSyncPacket(coord.chunkXPos, coord.chunkZPos, null);
        ((IHBMBiomeSyncPacket)packet).eid$setBiomeArray(((ChunkBiomeHook)chunk).getBiomeShortArray());
        PacketDispatcher.wrapper.sendToAllAround(packet, new NetworkRegistry.TargetPoint(world.provider.dimensionId,
                                                                                         coord.getCenterXPos(),
                                                                                         128.0F,
                                                                                         coord.getCenterZPosition(),
                                                                                         1024.0F));
    }
}
