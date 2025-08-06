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

package com.falsepattern.endlessids.mixin.mixins.common.biome.worldedit;

import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.forge.ForgeWorld;
import com.sk89q.worldedit.world.biome.BaseBiome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import static com.google.common.base.Preconditions.checkNotNull;

@Mixin(ForgeWorld.class)
public abstract class ForgeWorldMixin {
    @Shadow(remap = false) public abstract World getWorld();

    /**
     * @author FalsePattern
     * @reason ID Extension
     */
    @Overwrite(remap = false)
    public boolean setBiome(Vector2D position, BaseBiome biome) {
        checkNotNull(position);
        checkNotNull(biome);

        Chunk chunk = getWorld().getChunkFromBlockCoords(position.getBlockX(), position.getBlockZ());
        if ((chunk != null) && (chunk.isChunkLoaded)) {
            ((ChunkBiomeHook)chunk).getBiomeShortArray()[((position.getBlockZ() & 0xF) << 4 | position.getBlockX() & 0xF)] = (short) biome.getId();
            return true;
        }

        return false;
    }
}
