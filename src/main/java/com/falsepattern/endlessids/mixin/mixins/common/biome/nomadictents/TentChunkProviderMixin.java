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

package com.falsepattern.endlessids.mixin.mixins.common.biome.nomadictents;

import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import com.yurtmod.dimension.TentChunkProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.Arrays;

@Mixin(value = TentChunkProvider.class,
       remap = false)
public abstract class TentChunkProviderMixin {
    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       remap = true,
                       target = "Lnet/minecraft/world/chunk/Chunk;setBiomeArray([B)V"),
              remap = true,
              require = 1)
    private void setBiomesTweaked(Chunk chunk, byte[] p_76616_1_) {
        short[] biomeMap = new short[256];
        Arrays.fill(biomeMap, (short) BiomeGenBase.ocean.biomeID);
        ((ChunkBiomeHook) chunk).setBiomeShortArray(biomeMap);
    }
}
