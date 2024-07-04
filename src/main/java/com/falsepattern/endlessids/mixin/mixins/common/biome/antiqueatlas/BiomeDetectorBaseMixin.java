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

package com.falsepattern.endlessids.mixin.mixins.common.biome.antiqueatlas;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import com.falsepattern.endlessids.mixin.helpers.ShortUtil;
import hunternif.mc.atlas.core.BiomeDetectorBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.chunk.Chunk;

@Mixin(value = BiomeDetectorBase.class,
       remap = false)
public abstract class BiomeDetectorBaseMixin {
    private Chunk chunk;

    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 2)
    private static int extendIDs(int constant) {
        return ExtendedConstants.biomeIDCount;
    }

    @Redirect(method = "getBiomeID",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              require = 1)
    private byte[] disableGetter(Chunk instance) {
        chunk = instance;
        return null;
    }

    @Redirect(method = "getBiomeID",
              at = @At(value = "INVOKE",
                       target = "Lhunternif/mc/atlas/util/ByteUtil;unsignedByteToIntArray([B)[I"),
              require = 1)
    private int[] biomeIDs(byte[] i) {
        try {
            return ShortUtil.unsignedShortToIntArray(((ChunkBiomeHook) chunk).getBiomeShortArray());
        } finally {
            chunk = null;
        }
    }
}
