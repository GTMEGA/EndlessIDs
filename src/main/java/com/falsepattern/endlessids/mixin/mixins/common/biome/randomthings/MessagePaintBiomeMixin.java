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

package com.falsepattern.endlessids.mixin.mixins.common.biome.randomthings;

import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import lumien.randomthings.Items.ItemBiomeCapsule;
import lumien.randomthings.Network.Messages.MessagePaintBiome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.awt.Color;
import java.util.Random;

@Mixin(value = MessagePaintBiome.class,
       remap = false)
public abstract class MessagePaintBiomeMixin {

    @Shadow
    int posZ;

    @Shadow
    int posX;

    @Shadow
    int biomeID;

    @Shadow
    int dimensionID;

    @Shadow
    int posY;

    /**
     * @author FalsePattern
     * @reason getBiomeArray fix
     */
    @Overwrite
    @SideOnly(Side.CLIENT)
    public void onMessage(MessageContext ctx) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player != null && player.worldObj.provider.dimensionId == this.dimensionID) {
            Chunk c = player.worldObj.getChunkFromBlockCoords(this.posX, this.posZ);
            short[] biomeArray = ((ChunkBiomeHook) c).getBiomeShortArray(); //changed
            biomeArray[(this.posZ & 15) << 4 | this.posX & 15] = (short) (this.biomeID & 0xFFFF); //changed
            ((ChunkBiomeHook) c).setBiomeShortArray(biomeArray); //changed
            Minecraft.getMinecraft().thePlayer.worldObj.markBlocksDirtyVertical(this.posX, this.posZ, 0,
                                                                                player.worldObj.getActualHeight());
            BiomeGenBase biome = BiomeGenBase.getBiome(this.biomeID);
            int colorInt = ItemBiomeCapsule.getColorForBiome(biome);
            Random rng = new Random();
            Color color = new Color(colorInt);

            for (int i = 0; i < 64; ++i) {
                EntityFX smoke = new EntitySmokeFX(player.worldObj,
                                                   (float) this.posX + rng.nextFloat(),
                                                   (float) this.posY + rng.nextFloat(),
                                                   (float) this.posZ + rng.nextFloat(),
                                                   0.0, 0.0, 0.0);
                smoke.setRBGColorF(0.003921569F * (float) color.getRed(),
                                   0.003921569F * (float) color.getGreen(),
                                   0.003921569F * (float) color.getBlue());
                Minecraft.getMinecraft().effectRenderer.addEffect(smoke);
            }
        }

    }
}
