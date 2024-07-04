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

package com.falsepattern.endlessids.mixin.mixins.common.potion.vanilla.network;

import com.falsepattern.endlessids.mixin.helpers.IS1DPacketEntityEffectMixin;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.netty.buffer.ByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;

@Mixin(value = S1DPacketEntityEffect.class)
public abstract class S1DPacketEntityEffectMixin implements IS1DPacketEntityEffectMixin {
    private int idExtended;

    @WrapOperation(method = "<init>(ILnet/minecraft/potion/PotionEffect;)V",
                   at = @At(value = "INVOKE",
                            target = "Lnet/minecraft/potion/PotionEffect;getPotionID()I"),
                   require = 1)
    private int getPotionID(PotionEffect effect, Operation<Integer> original) {
        idExtended = original.call(effect);
        return -1;
    }

    @Redirect(method = "readPacketData",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/network/PacketBuffer;readByte()B",
                       ordinal = 0),
              require = 1)
    private byte networkReadPacketData(PacketBuffer instance) {
        idExtended = instance.readInt();
        return -1;
    }

    @Redirect(method = "writePacketData",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/network/PacketBuffer;writeByte(I)Lio/netty/buffer/ByteBuf;",
                       ordinal = 0),
              require = 1)
    private ByteBuf networkWritePacketData(PacketBuffer instance, int p_writeByte_1_) {
        instance.writeInt(idExtended);
        return null;
    }

    @Override
    public int getIDExtended() {
        return this.idExtended;
    }
}
