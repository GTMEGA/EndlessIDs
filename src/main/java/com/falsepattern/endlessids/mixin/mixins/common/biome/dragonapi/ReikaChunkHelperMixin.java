/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2025 FalsePattern, The MEGA Team
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
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

package com.falsepattern.endlessids.mixin.mixins.common.biome.dragonapi;

import Reika.DragonAPI.Libraries.World.ReikaChunkHelper;
import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.chunk.Chunk;

@Mixin(value = ReikaChunkHelper.class,
       remap = false)
public abstract class ReikaChunkHelperMixin {
    private static short[] biomeArray;

    @Redirect(method = "copyChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;setBiomeArray([B)V",
                       remap = true),
              require = 1)
    private static void customSet(Chunk chunk, byte[] p_76616_1_) {
        ((ChunkBiomeHook) chunk).setBiomeShortArray(biomeArray);
        biomeArray = null;
    }

    @Redirect(method = "copyChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              require = 1)
    private static byte[] customGet(Chunk chunk) {
        biomeArray = ((ChunkBiomeHook) chunk).getBiomeShortArray();
        return EndlessIDs.ZERO_LENGTH_BIOME_ARRAY_PLACEHOLDER;
    }
}
