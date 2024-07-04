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

package com.falsepattern.endlessids.mixin.mixins.common.biome.biomewand;

import com.falsepattern.endlessids.PlaceholderBiome;
import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import com.spacechase0.minecraft.biomewand.BiomeWandMod;
import com.spacechase0.minecraft.biomewand.item.BiomeWandItem;
import com.spacechase0.minecraft.spacecore.util.TranslateUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.List;

@Mixin(value = BiomeWandItem.class,
       remap = false)
public abstract class BiomeWandItemMixin {
    /**
     * @author FalsePattern
     * @reason Biome ID extension
     */
    @Overwrite
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int blockX, int blockY, int blockZ, int side, float par8, float par9, float par10) {
        NBTTagCompound tag = stack.getTagCompound();
        boolean sample = true;
        if (tag != null) {
            if (tag.hasKey("sampledBiomeS")) {
                sample = false;
            }
        } else {
            tag = new NBTTagCompound();
        }

        if (sample) {
            int relBlockX = blockX & 15;
            int relBlockZ = blockZ & 15;
            Chunk chunk = world.getChunkFromBlockCoords(blockX, blockZ);
            short biome = ((ChunkBiomeHook) chunk).getBiomeShortArray()[relBlockZ * 16 + relBlockX];
            tag.setShort("sampledBiomeS", biome);
        } else {
            short biome = tag.getShort("sampledBiomeS");

            for (int ix = blockX - 3; ix <= blockX + 3; ++ix) {
                for (int iz = blockZ - 3; iz <= blockZ + 3; ++iz) {
                    int relBlockX = ix & 15;
                    int relBlockZ = iz & 15;
                    Chunk chunk = world.getChunkFromBlockCoords(blockX, blockZ);
                    ((ChunkBiomeHook) chunk).getBiomeShortArray()[relBlockZ << 4 | relBlockX] = biome;
                    chunk.isModified = true;
                    BiomeWandMod.proxy.updateRendererAt(ix, iz);
                }
            }

            if (!player.capabilities.isCreativeMode) {
                tag.removeTag("sampledBiomeS");
                stack.damageItem(1, player);
            }
        }

        stack.setTagCompound(tag);
        return true;
    }

    /**
     * @author FalsePattern
     * @reason Biome ID extension
     */
    @Overwrite
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean par4) {
        NBTTagCompound tag = stack.getTagCompound();
        String str = TranslateUtils.translate("biomewand.tooltip.biome") + ": ";
        if (tag == null || !tag.hasKey("sampledBiomeS")) {
            str = str + TranslateUtils.translate("biomewand.tooltip.none");
        } else {
            BiomeGenBase b = BiomeGenBase.getBiomeGenArray()[Short.toUnsignedInt(tag.getShort("sampledBiomeS"))];
            if (b == null) {
                str = str + TranslateUtils.translate("biomewand.tooltip.unknown");
            } else {
                str = str + b.biomeName;
            }
        }

        list.add(str);
    }

    /**
     * @author FalsePattern
     * @reason Biome ID extension
     */
    @Overwrite
    public void getSubItems(Item item, CreativeTabs tabs, List<ItemStack> list) {
        list.add(new ItemStack((BiomeWandItem) (Object) this, 1));
        for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
            if (biome != null && !(biome instanceof PlaceholderBiome)) {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setShort("sampledBiomeS", (short) biome.biomeID);
                ItemStack wand = new ItemStack((BiomeWandItem) (Object) this, 1);
                wand.setTagCompound(compound);
                list.add(wand);
            }
        }

    }
}
