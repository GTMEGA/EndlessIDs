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

package com.falsepattern.endlessids.mixin.mixins.common.biome.dragonapi;

import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

@Mixin(value = ReikaWorldHelper.class,
       remap = false)
public abstract class ReikaWorldHelperMixin {
    private static int fromBiomeID;

    @Redirect(method = {"setBiomeForXZ", "setBiomeAndBlocksForXZ"},
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              require = 2)
    private static byte[] getFake(Chunk instance) {
        return EndlessIDs.FAKE_BIOME_ARRAY_PLACEHOLDER;
    }

    @Redirect(method = {"setBiomeForXZ", "setBiomeAndBlocksForXZ"},
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;setBiomeArray([B)V",
                       remap = true),
              require = 2)
    private static void noSet(Chunk instance, byte[] arr) {

    }

    @Inject(method = "setBiomeForXZ",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/chunk/Chunk;setChunkModified()V",
                     remap = true),
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1)
    private static void customLogicSetBiome(World world, int x, int z, BiomeGenBase biome, boolean applyEnvironment, CallbackInfo ci, Chunk ch, int ax, int az, byte[] biomes, int index) {
        ((ChunkBiomeHook) ch).getBiomeShortArray()[index] = (short) biome.biomeID;
    }

    @Inject(method = "setBiomeAndBlocksForXZ",
            at = @At(value = "FIELD",
                     target = "Lnet/minecraft/world/biome/BiomeGenBase;biomeList:[Lnet/minecraft/world/biome/BiomeGenBase;",
                     remap = true),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            require = 1)
    private static void captureIndexSetBiomeAndBlocks(World world, int x, int z, BiomeGenBase biome, boolean applyEnvironment, CallbackInfo ci, Chunk ch, int ax, int az, byte[] biomes, int index) {
        fromBiomeID = ((ChunkBiomeHook) ch).getBiomeShortArray()[index];
    }

    @ModifyVariable(method = "setBiomeAndBlocksForXZ",
                    at = @At("STORE"),
                    ordinal = 1)
    private static BiomeGenBase changeBiomeFrom(BiomeGenBase value) {
        return BiomeGenBase.getBiome(fromBiomeID);
    }

    @Inject(method = "setBiomeAndBlocksForXZ",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/chunk/Chunk;setBiomeArray([B)V",
                     remap = true),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            require = 1)
    private static void customLogicSetBiomeAndBlocks(World world, int x, int z, BiomeGenBase biome, boolean applyEnvironment, CallbackInfo ci, Chunk ch, int ax, int az, byte[] biomes, int index, BiomeGenBase from) {
        ((ChunkBiomeHook) ch).getBiomeShortArray()[index] = (short) biome.biomeID;
    }
}
