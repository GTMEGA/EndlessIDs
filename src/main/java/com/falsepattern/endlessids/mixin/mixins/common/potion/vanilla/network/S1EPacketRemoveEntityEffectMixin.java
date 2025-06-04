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

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;

//Yes, this is wasteful, but other mods also touch these classes, so this is the most robust way to do this unfortunately
@Mixin(S1EPacketRemoveEntityEffect.class)
public abstract class S1EPacketRemoveEntityEffectMixin {
    @Shadow
    private int field_149078_b;

    @Inject(method = "readPacketData",
            at = @At("RETURN"),
            require = 1)
    private void readNewData(PacketBuffer buf, CallbackInfo ci) {
        field_149078_b = buf.readInt();
    }

    @Inject(method = "writePacketData",
            at = @At("RETURN"),
            require = 1)
    private void writeNewData(PacketBuffer buf, CallbackInfo ci) {
        buf.writeInt(field_149078_b);
    }
}
