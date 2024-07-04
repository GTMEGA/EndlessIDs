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

package com.falsepattern.endlessids.mixin.mixins.common.biome.thaumcraft;

import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.misc.PacketBiomeChange;
import thaumcraft.common.lib.utils.Utils;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mixin(value = Utils.class,
       remap = false)
public abstract class UtilsMixin {

    /**
     * @author FalsePattern
     * @reason Biome ID Extension
     */
    @Overwrite
    public static void setBiomeAt(World world, int x, int z, BiomeGenBase biome) {
        if (biome != null) {
            Chunk chunk = world.getChunkFromBlockCoords(x, z);
            short[] array = ((ChunkBiomeHook) chunk).getBiomeShortArray();
            array[(z & 15) << 4 | x & 15] = (short) (biome.biomeID);
            if (!world.isRemote) {
                PacketHandler.INSTANCE.sendToAllAround(new PacketBiomeChange(x, z, (short) biome.biomeID),
                                                       new NetworkRegistry.TargetPoint(world.provider.dimensionId, x,
                                                                                       world.getHeightValue(x, z), z,
                                                                                       32.0));
            }
        }
    }
}
