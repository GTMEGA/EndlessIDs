/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2025 FalsePattern, The MEGA Team
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the word "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, only version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.falsepattern.endlessids.mixin.mixins.common.biome.witchery;

import com.emoniph.witchery.brewing.action.effect.BrewActionBiomeChange;
import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import com.falsepattern.endlessids.mixin.helpers.stubpackage.com.emoniph.witchery.brewing.action.effect.BrewActionBiomeChange$ChunkCoord;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Mixin(BrewActionBiomeChange.class)
public abstract class BrewActionBiomeChangeMixin {
    @WrapOperation(method = "changeBiome",
                   at = @At(value = "INVOKE",
                            target = "Ljava/util/Map$Entry;getValue()Ljava/lang/Object;"),
                   remap = false,
                   require = 1)
    private Object removeByteArrayGet(Map.Entry instance, Operation<Object> original,
                                      @Share("biomeArray") LocalRef<short[]> biome) {
        biome.set((short[]) original.call(instance));
        return null;
    }

    @Redirect(method = "changeBiome",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;setBiomeArray([B)V",
                       remap = true),
              remap = false,
              require = 1)
    private void extendedSetBiomeArray(Chunk instance, byte[] ignored, @Share("biomeArray") LocalRef<short[]> biome) {
        ((ChunkBiomeHook)instance).setBiomeShortArray(biome.get());
    }

    /**
     * @author FalsePattern
     * @reason Doing this with normal mixins would be extremely tedious because of the local variable shuffling.
     * Nobody else's patches should be landing here anyway.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Overwrite(remap = false)
    private void drawLine(World world, int x1, int x2, int z, HashMap chunkMap, BiomeGenBase biome) {
        for(int x = x1; x <= x2; ++x) {
            BrewActionBiomeChange$ChunkCoord coord = new BrewActionBiomeChange$ChunkCoord(x >> 4, z >> 4);
            short[] map = (short[])chunkMap.get(coord);
            if (map == null) {
                Chunk chunk = world.getChunkFromBlockCoords(x, z);
                val sarr = ((ChunkBiomeHook) chunk).getBiomeShortArray();
                map = Arrays.copyOf(sarr, sarr.length);
                chunkMap.put(coord, map);
            }

            map[(z & 15) << 4 | x & 15] = (short) biome.biomeID;
            if (biome.rainfall == 0.0F) {
                int y = world.getTopSolidOrLiquidBlock(x, z);
                if (world.getBlock(x, y, z) == Blocks.snow_layer) {
                    world.setBlockToAir(x, y, z);
                }
            }
        }
    }
}
