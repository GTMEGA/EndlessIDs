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
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.World;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import cpw.mods.fml.common.FMLLog;

@Mixin(value = LOTRWorldChunkManager.class)
public abstract class LOTRWorldChunkManagerMixin extends WorldChunkManager {
    @Shadow(remap = false)
    private World worldObj;

    @Shadow(remap = false)
    public abstract LOTRBiomeVariant[] getBiomeVariants(LOTRBiomeVariant[] variants, int i, int k, int xSize, int zSize);

    /**
     * @author FalsePattern
     * @reason fix stuff
     */
    @Overwrite(remap = false)
    public LOTRBiomeVariant getBiomeVariantAt(int i, int k) {
        short[] variants;
        Chunk chunk = this.worldObj.getChunkFromBlockCoords(i, k);
        if (chunk != null &&
            (variants = LOTRBiomeVariantStorageShort.getChunkBiomeVariants(this.worldObj, chunk)) != null) {
            if (variants.length == 256) {
                int chunkX = i & 0xF;
                int chunkZ = k & 0xF;
                short variantID = variants[chunkX + chunkZ * 16];
                return LOTRBiomeVariant.getVariantForID(variantID);
            }
            FMLLog.severe("Found chunk biome variant array of unexpected length " + variants.length);
        }
        if (!this.worldObj.isRemote) {
            return this.getBiomeVariants(null, i, k, 1, 1)[0];
        }
        return LOTRBiomeVariant.STANDARD;
    }
}
