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

package com.falsepattern.endlessids.mixin.mixins.common.biome.lotr;

import com.falsepattern.endlessids.mixin.helpers.LOTRBiomeVariantStorageShort;
import lotr.common.world.LOTRChunkProviderUtumno;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

@Mixin(value = LOTRChunkProviderUtumno.class)
public abstract class LOTRChunkProviderUtumnoMixin implements IChunkProvider {
    @Shadow(remap = false)
    private LOTRBiomeVariant[] variantsForGeneration;

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Llotr/common/world/biome/variant/LOTRBiomeVariantStorage;setChunkBiomeVariants(Lnet/minecraft/world/World;Lnet/minecraft/world/chunk/Chunk;[B)V",
                       remap = false),
              require = 1)
    private void storeShorts(World world, Chunk chunk, byte[] variantsB) {
        short[] variants = new short[256];

        for (int l = 0; l < variants.length; ++l) {
            variants[l] = (short) this.variantsForGeneration[l].variantID;
        }

        LOTRBiomeVariantStorageShort.setChunkBiomeVariants(world, chunk, variants);
    }
}
