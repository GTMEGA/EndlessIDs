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

package com.falsepattern.endlessids.mixin.mixins.common.blockitem.ubc;

import com.falsepattern.endlessids.mixin.helpers.SubChunkBlockHook;
import exterminatorJeff.undergroundBiomes.worldGen.BiomeUndergroundDecorator;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.util.logging.Logger;

@Mixin(value = BiomeUndergroundDecorator.class,
       remap = false)
public abstract class BiomeUndergroundDecoratorMixin {
    private static NibbleArray fakeArray;
    private SubChunkBlockHook subChunk;

    @Redirect(method = {"replaceChunkOres(IILnet/minecraft/world/World;)V",
                        "replaceChunkOres(Lnet/minecraft/world/chunk/IChunkProvider;II)V"},
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLSBArray()[B",
                       remap = true),
              require = 2)
    private byte[] returnLSB(ExtendedBlockStorage subChunkVanilla) {
        SubChunkBlockHook subChunk = (SubChunkBlockHook) subChunkVanilla;
        this.subChunk = subChunk;
        return subChunk.eid$getB1();
    }

    @Redirect(method = {"replaceChunkOres(IILnet/minecraft/world/World;)V",
                        "replaceChunkOres(Lnet/minecraft/world/chunk/IChunkProvider;II)V"},
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockMSBArray()Lnet/minecraft/world/chunk/NibbleArray;",
                       remap = true),
              require = 4)
    private NibbleArray returnFakeArray(ExtendedBlockStorage instance) {
        return fakeArray == null ? (fakeArray = new NibbleArray(4096, 4)) : fakeArray;
    }

    @Redirect(method = {"replaceChunkOres(IILnet/minecraft/world/World;)V",
                        "replaceChunkOres(Lnet/minecraft/world/chunk/IChunkProvider;II)V"},
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/NibbleArray;get(III)I",
                       remap = true),
              require = 2)
    private int returnRestOfID(NibbleArray instance, int x, int y, int z) {
        val id = subChunk.eid$getID(x, y, z) >>> 8;
        subChunk = null;
        return id;
    }

    @Redirect(method = "correctBiomeDecorators",
              at = @At(value = "INVOKE",
                       target = "Ljava/util/logging/Logger;info(Ljava/lang/String;)V"),
              expect = 0,
              require = 0) // https://github.com/LITW-Refined/UndergroundBiomesConstructs1.7 fork removes these
    private void noLog(Logger instance, String s) {

    }
}
