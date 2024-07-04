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

package com.falsepattern.endlessids.mixin.mixins.common.biome.extrautilities;

import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import com.rwtema.extrautils.worldgen.endoftime.ChunkProviderEndOfTime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.Arrays;

@Mixin(value = ChunkProviderEndOfTime.class,
       remap = false)
public abstract class ChunkProviderEndOfTimeMixin {
    private int id;
    private Chunk chunk;

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              remap = true,
              require = 1)
    private byte[] setBiomesTweaked1(Chunk instance) {
        chunk = instance;
        return EndlessIDs.ZERO_LENGTH_BIOME_ARRAY_PLACEHOLDER;
    }

    @Redirect(method = "provideChunk",
              at = @At(value = "FIELD",
                       target = "Lnet/minecraft/world/biome/BiomeGenBase;biomeID:I",
                       remap = true),
              remap = true)
    private int setBiomesTweaked2(BiomeGenBase instance) {
        return id = instance.biomeID;
    }

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Ljava/util/Arrays;fill([BB)V"),
              remap = true,
              require = 1)
    private void setBiomesTweaked3(byte[] bytes, byte b) {
        try {
            Arrays.fill(((ChunkBiomeHook) chunk).getBiomeShortArray(), (short) id);
        } finally {
            chunk = null;
            id = -1;
        }
    }
}
