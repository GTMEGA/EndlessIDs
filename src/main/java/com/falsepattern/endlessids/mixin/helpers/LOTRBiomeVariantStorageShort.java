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

package com.falsepattern.endlessids.mixin.helpers;

import com.falsepattern.endlessids.Hooks;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;
import lotr.common.LOTRDimension;
import lotr.common.network.LOTRPacketBiomeVariantsUnwatch;
import lotr.common.network.LOTRPacketBiomeVariantsWatch;
import lotr.common.network.LOTRPacketHandler;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import cpw.mods.fml.common.FMLLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LOTRBiomeVariantStorageShort {
    private static final Map<LOTRDimension, Map<ChunkCoordIntPair, short[]>> chunkVariantMap = new HashMap<>();
    private static final Map<LOTRDimension, Map<ChunkCoordIntPair, short[]>> chunkVariantMapClient = new HashMap<>();

    private static Map<ChunkCoordIntPair, short[]> getDimensionChunkMap(World world) {
        Map<LOTRDimension, Map<ChunkCoordIntPair, short[]>> sourcemap;
        if (!world.isRemote) {
            sourcemap = chunkVariantMap;
        } else {
            sourcemap = chunkVariantMapClient;
        }

        val dim = LOTRDimension.getCurrentDimensionWithFallback(world);

        return sourcemap.computeIfAbsent(dim, k -> new HashMap<>());
    }

    private static ChunkCoordIntPair getChunkKey(Chunk chunk) {
        return new ChunkCoordIntPair(chunk.xPosition, chunk.zPosition);
    }

    public static short[] getChunkBiomeVariants(World world, Chunk chunk) {
        return getChunkBiomeVariants(world, getChunkKey(chunk));
    }

    public static short[] getChunkBiomeVariants(World world, ChunkCoordIntPair chunk) {
        return getDimensionChunkMap(world).get(chunk);
    }

    public static void setChunkBiomeVariants(World world, Chunk chunk, short[] variants) {
        setChunkBiomeVariants(world, getChunkKey(chunk), variants);
    }

    public static void setChunkBiomeVariants(World world, ChunkCoordIntPair chunk, short[] variants) {
        getDimensionChunkMap(world).put(chunk, variants);
    }

    public static void clearChunkBiomeVariants(World world, Chunk chunk) {
        clearChunkBiomeVariants(world, getChunkKey(chunk));
    }

    public static void clearChunkBiomeVariants(World world, ChunkCoordIntPair chunk) {
        getDimensionChunkMap(world).remove(chunk);
    }

    public static void loadChunkVariants(World world, Chunk chunk, NBTTagCompound data) {
        if (getChunkBiomeVariants(world, chunk) == null) {
            short[] variants;
            if (data.hasKey("LOTRBiomeVariantsS")) {
                variants = Hooks.byteToShortArray(data.getByteArray("LOTRBiomeVariantsS"));
            } else {
                variants = new short[256];
            }

            setChunkBiomeVariants(world, chunk, variants);
        }

    }

    public static void saveChunkVariants(World world, Chunk chunk, NBTTagCompound data) {
        val variants = getChunkBiomeVariants(world, chunk);
        if (variants != null) {
            data.setByteArray("LOTRBiomeVariantsS", Hooks.shortToByteArray(variants));
        }

    }

    public static void clearAllVariants(World world) {
        getDimensionChunkMap(world).clear();
        FMLLog.info("Unloading LOTR biome variants in %s",
                    LOTRDimension.getCurrentDimensionWithFallback(world).dimensionName);
    }

    public static void performCleanup(WorldServer world) {
        val dimensionMap = getDimensionChunkMap(world);
        val removalChunks = new ArrayList<ChunkCoordIntPair>();

        for (val chunk : dimensionMap.keySet()) {
            if (!world.theChunkProviderServer.chunkExists(chunk.chunkXPos, chunk.chunkZPos)) {
                removalChunks.add(chunk);
            }
        }

        for (val chunk : removalChunks) {
            dimensionMap.remove(chunk);
        }
    }

    public static void sendChunkVariantsToPlayer(World world, Chunk chunk, EntityPlayerMP entityplayer) {
        short[] variants = getChunkBiomeVariants(world, chunk);
        if (variants != null) {
            val packet = new LOTRPacketBiomeVariantsWatch(chunk.xPosition, chunk.zPosition,
                                                          Hooks.shortToByteArray(variants));
            LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
        } else {
            val dimName = world.provider.getDimensionName();
            val posX = chunk.xPosition << 4;
            val posZ = chunk.zPosition << 4;
            val playerName = entityplayer.getCommandSenderName();
            FMLLog.severe("Could not find LOTR biome variants for %s chunk at %d, %d; requested by %s", dimName, posX,
                          posZ, playerName);
        }

    }

    public static void sendUnwatchToPlayer(World world, Chunk chunk, EntityPlayerMP entityplayer) {
        val packet = new LOTRPacketBiomeVariantsUnwatch(chunk.xPosition, chunk.zPosition);
        LOTRPacketHandler.networkWrapper.sendTo(packet, entityplayer);
    }

    public static int getSize(World world) {
        val map = getDimensionChunkMap(world);
        return map.size();
    }
}
