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

package com.falsepattern.endlessids.mixin.mixins.common.biome.lotr;

import com.falsepattern.endlessids.mixin.helpers.LOTRBiomeVariantStorageShort;
import lotr.common.world.biome.variant.LOTRBiomeVariantStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

@Mixin(value = LOTRBiomeVariantStorage.class,
       remap = false)
public abstract class LOTRBiomeVariantStorageMixin {

    private static UnsupportedOperationException crash() {
        return new UnsupportedOperationException("Deprecated method.");
    }

    /**
     * @author FalsePattern
     * @reason Fix stuff
     */
    @Overwrite
    public static byte[] getChunkBiomeVariants(World world, Chunk chunk) {
        throw crash();
    }

    /**
     * @author FalsePattern
     * @reason Fix stuff
     */
    @Overwrite
    public static byte[] getChunkBiomeVariants(World world, ChunkCoordIntPair chunk) {
        throw crash();
    }

    /**
     * @author FalsePattern
     * @reason Fix stuff
     */
    @Overwrite
    public static void setChunkBiomeVariants(World world, Chunk chunk, byte[] variants) {
        throw crash();
    }


    /**
     * @author FalsePattern
     * @reason Fix stuff
     */
    @Overwrite
    public static void setChunkBiomeVariants(World world, ChunkCoordIntPair chunk, byte[] variants) {
        throw crash();
    }

    /**
     * @author FalsePattern
     * @reason Fix stuff
     */
    @Overwrite
    public static void clearChunkBiomeVariants(World world, Chunk chunk) {
        LOTRBiomeVariantStorageShort.clearChunkBiomeVariants(world, chunk);
    }

    /**
     * @author FalsePattern
     * @reason Fix stuff
     */
    @Overwrite
    public static void clearChunkBiomeVariants(World world, ChunkCoordIntPair chunk) {
        LOTRBiomeVariantStorageShort.clearChunkBiomeVariants(world, chunk);
    }

    /**
     * @author FalsePattern
     * @reason Fix stuff
     */
    @Overwrite
    public static void loadChunkVariants(World world, Chunk chunk, NBTTagCompound data) {
        LOTRBiomeVariantStorageShort.loadChunkVariants(world, chunk, data);
    }

    /**
     * @author FalsePattern
     * @reason Fix stuff
     */
    @Overwrite
    public static void saveChunkVariants(World world, Chunk chunk, NBTTagCompound data) {
        LOTRBiomeVariantStorageShort.saveChunkVariants(world, chunk, data);
    }

    /**
     * @author FalsePattern
     * @reason Fix stuff
     */
    @Overwrite
    public static void clearAllVariants(World world) {
        LOTRBiomeVariantStorageShort.clearAllVariants(world);
    }

    /**
     * @author FalsePattern
     * @reason Fix stuff
     */
    @Overwrite
    public static void performCleanup(WorldServer world) {
        LOTRBiomeVariantStorageShort.performCleanup(world);
    }

    /**
     * @author FalsePattern
     * @reason Fix stuff
     */
    @Overwrite
    public static void sendChunkVariantsToPlayer(World world, Chunk chunk, EntityPlayerMP entityPlayer) {
        LOTRBiomeVariantStorageShort.sendChunkVariantsToPlayer(world, chunk, entityPlayer);
    }

    /**
     * @author FalsePattern
     * @reason Fix stuff
     */
    @Overwrite
    public static void sendUnwatchToPlayer(World world, Chunk chunk, EntityPlayerMP entityPlayer) {
        LOTRBiomeVariantStorageShort.sendUnwatchToPlayer(world, chunk, entityPlayer);
    }

    /**
     * @author FalsePattern
     * @reason Fix stuff
     */
    @Overwrite
    public static int getSize(World world) {
        return LOTRBiomeVariantStorageShort.getSize(world);
    }
}
