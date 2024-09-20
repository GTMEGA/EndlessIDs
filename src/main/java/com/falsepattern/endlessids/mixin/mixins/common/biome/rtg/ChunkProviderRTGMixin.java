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

package com.falsepattern.endlessids.mixin.mixins.common.biome.rtg;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import com.falsepattern.endlessids.mixin.helpers.BiomePatchHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rtg.util.CellNoise;
import rtg.util.OpenSimplexNoise;
import rtg.world.biome.RTGBiomeProvider;
import rtg.world.biome.realistic.RealisticBiomeBase;
import rtg.world.gen.ChunkProviderRTG;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.Random;

@Mixin(value = ChunkProviderRTG.class,
       remap = false)
public abstract class ChunkProviderRTGMixin {
    @Shadow
    private BiomeGenBase[] baseBiomesList;

    @Shadow
    private int[] xyinverted;

    @Shadow private long worldSeed;

    @Shadow private World worldObj;

    @Shadow protected RTGBiomeProvider cmr;

    @Shadow private Random mapRand;

    @Shadow private OpenSimplexNoise simplex;

    @Shadow private CellNoise cell;

    @Shadow private float[] borderNoise;

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              remap = true,
              require = 1)
    private byte[] setBiomesTweaked(Chunk chunk) {
        return BiomePatchHelper.getBiomeArrayTweaked(chunk, (i) -> baseBiomesList[xyinverted[i]]);
    }

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;setBiomeArray([B)V",
                       remap = true),
              remap = true,
              require = 1)
    private void removeUnnecessarySet(Chunk chunk, byte[] bytes) {

    }

    @ModifyConstant(method = "provideChunk",
                    constant = @Constant(intValue = 256,
                                         ordinal = 0),
                    slice = @Slice(from = @At(value = "INVOKE",
                                              target = "Lrtg/world/gen/ChunkProviderRTG;generateTerrain(Lrtg/world/biome/RTGBiomeProvider;II[Lnet/minecraft/block/Block;[B[Lrtg/world/biome/realistic/RealisticBiomeBase;[F)V",
                                              shift = At.Shift.AFTER)),
                    require = 1)
    private int noDefaultLoop(int constant) {
        return 0;
    }

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Lrtg/world/gen/ChunkProviderRTG;generateTerrain(Lrtg/world/biome/RTGBiomeProvider;II[Lnet/minecraft/block/Block;[B[Lrtg/world/biome/realistic/RealisticBiomeBase;[F)V"),
              require = 1)
    private void injectTheFunny(ChunkProviderRTG instance,
                                RTGBiomeProvider cmr,
                                int cx,
                                int cy,
                                Block[] blocks,
                                byte[] metadata,
                                RealisticBiomeBase[] biomes,
                                float[] noise) {
        instance.generateTerrain(cmr, cx, cy, blocks, metadata, biomes, noise);
        for (int ci = 0; ci < 256; ci++) {
            int biomeID = biomes[ci].baseBiome.biomeID;
            RealisticBiomeBase.getBiome(biomeID).generateMapGen(blocks, metadata, worldSeed, worldObj, cmr, mapRand, cx, cy, simplex, cell, noise);
        }
    }

    @Inject(method = "<init>",
            at = @At(value = "RETURN"),
            require = 1)
    private void extendBorderNoise(World world, long l, CallbackInfo ci) {
        this.borderNoise = new float[ExtendedConstants.biomeIDCount];
    }

    @ModifyConstant(method = "doPopulate",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount,
                                         ordinal = 0),
                    slice = @Slice(from = @At(value = "INVOKE",
                                              target = "Lrtg/world/biome/realistic/RealisticBiomeBase;rDecorateClay(Lnet/minecraft/world/World;Ljava/util/Random;IIFII)V")),
                    require = 1)
    private int allBorderNoise(int constant) {
        return ExtendedConstants.biomeIDCount;
    }
}
