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

package com.falsepattern.endlessids.mixin.mixins.common.biome.gadomancy;

import com.falsepattern.endlessids.mixin.helpers.BiomePatchHelper;
import makeo.gadomancy.common.utils.world.ChunkProviderTCOuter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;

import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

@Mixin(ChunkProviderTCOuter.class)
public abstract class ChunkProviderTCOuterMixin implements IChunkProvider {
    @Mutable
    @Shadow(remap = false)
    @Final
    private static byte[] biomeArr;

    @Inject(method = "<clinit>",
            at = @At("RETURN"),
            require = 1)
    private static void extendedBiomeArr(CallbackInfo ci) {
        biomeArr = new byte[0];
    }

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B"),
              require = 1)
    private byte[] setBiomesTweaked(Chunk instance) {
        return BiomePatchHelper.getBiomeArrayTweaked(instance, i -> ThaumcraftWorldGenerator.biomeEldritchLands);
    }
}
