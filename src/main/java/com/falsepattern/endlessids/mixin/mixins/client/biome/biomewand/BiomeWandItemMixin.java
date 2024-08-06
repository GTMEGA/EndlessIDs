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

package com.falsepattern.endlessids.mixin.mixins.client.biome.biomewand;

import com.spacechase0.minecraft.biomewand.item.BiomeWandItem;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mixin(value = BiomeWandItem.class,
       remap = false)
public abstract class BiomeWandItemMixin {
    /**
     * @author FalsePattern
     * @reason Biome ID extension
     */
    @SideOnly(Side.CLIENT)
    @Overwrite
    public int getColorFromItemStack(ItemStack stack, int pass) {
        NBTTagCompound tag = stack.getTagCompound();
        int biomeID = -1;
        if (tag != null && tag.hasKey("sampledBiomeS")) {
            biomeID = Short.toUnsignedInt(tag.getShort("sampledBiomeS"));
        }
        if (pass != 1) {
            return -1;
        }
        if (biomeID < 0) {
            return 0;
        }
        val biome = BiomeGenBase.getBiomeGenArray()[biomeID];
        if (biome == null) {
            return 0;
        }
        return biome.color;
    }
}
