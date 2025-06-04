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

package com.falsepattern.endlessids.mixin.mixins.client.potion.vanilla;

import com.falsepattern.endlessids.mixin.helpers.IS1DPacketEntityEffectMixin;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mixin(NetHandlerPlayClient.class)
public abstract class NetHandlerPlayClientMixin {
    private static int theID;

    @Inject(method = "handleEntityEffect",
            at = @At("HEAD"),
            require = 1)
    private void grabID(S1DPacketEntityEffect packetIn, CallbackInfo ci) {
        theID = ((IS1DPacketEntityEffectMixin) packetIn).endlessids$getIDExtended();
    }

    @Redirect(method = "handleEntityEffect",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/entity/EntityLivingBase;addPotionEffect(Lnet/minecraft/potion/PotionEffect;)V"),
              require = 1)
    private void addPotionEffect(EntityLivingBase entity, PotionEffect effect) {
        val newPotion = new PotionEffect(theID, effect.getDuration(), effect.getAmplifier());
        theID = 0;
        newPotion.setPotionDurationMax(effect.getIsPotionDurationMax());
        entity.addPotionEffect(newPotion);
    }
}
