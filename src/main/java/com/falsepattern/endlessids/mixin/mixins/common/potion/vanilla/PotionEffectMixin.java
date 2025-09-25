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

package com.falsepattern.endlessids.mixin.mixins.common.potion.vanilla;

import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import lombok.val;
import lombok.var;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@Mixin(value = PotionEffect.class,
       priority = 2000 ) //Hodgepodge fixPotionLimit compat (inject-head into overwrite in readCustomPotionEffectFromNBT)
public abstract class PotionEffectMixin {
    @Inject(method = "<init>(IIIZ)V",
            at = @At("RETURN"),
            require = 1)
    private void fixOutOfBoundsID(int p_i1576_1_, int p_i1576_2_, int p_i1576_3_, boolean p_i1576_4_, CallbackInfo ci) {
        var id = p_i1576_1_ & ExtendedConstants.potionIDMask;
        recovery:
        if (id != p_i1576_1_ && Potion.potionTypes[id] == null) {
            //Maybe vanilla code?
            val realId = id;
            id = id & 0xFF;
            if (Potion.potionTypes[id] == null) {
                //Nope
                id = realId;
            }
        }
        if (Potion.potionTypes[id] == null) {
            EndlessIDs.LOG.error("Encountered invalid potion ID: {}. See the following stacktrace for more info.", p_i1576_1_);
            EndlessIDs.LOG.error("Stacktrace:", new Throwable());
            EndlessIDs.LOG.error("Recovering to a valid potion ID (usually movement speed)");
            for (int i = 0; i < Potion.potionTypes.length; i++) {
                if (Potion.potionTypes[i] != null) {
                    id = i;
                    break;
                }
            }
        }
        this.potionID = id;
    }

    @Inject(method = "readCustomPotionEffectFromNBT",
            at = @At(value = "HEAD"),
            cancellable = true,
            require = 1)
    private static void readCustomPotionEffectFromNBT(NBTTagCompound nbt, CallbackInfoReturnable<PotionEffect> cir) {
        int id;
        if (nbt.hasKey("IdExtended")) {
            id = nbt.getInteger("IdExtended");
        } else {
            id = nbt.getByte("Id") & 0xFF;
        }

        if (id >= 0 && id < Potion.potionTypes.length && Potion.potionTypes[id] != null) {
            byte b1 = nbt.getByte("Amplifier");
            int i = nbt.getInteger("Duration");
            boolean flag = nbt.getBoolean("Ambient");
            cir.setReturnValue(new PotionEffect(id, i, b1, flag));
        } else {
            cir.setReturnValue(null);
        }
    }

    @Shadow
    public abstract int getPotionID();

    @Shadow
    public abstract int getAmplifier();

    @Shadow
    public abstract int getDuration();

    @Shadow
    public abstract boolean getIsAmbient();

    @Shadow private int potionID;

    @Inject(method = "writeCustomPotionEffectToNBT",
            at = @At(value = "HEAD"),
            cancellable = true,
            require = 1)
    private void writeCustomPotionEffectToNBTExtended(NBTTagCompound nbt, CallbackInfoReturnable<NBTTagCompound> cir) {
        val potionID = getPotionID();
        if (potionID > 127) {
            nbt.setInteger("IdExtended", potionID);
            nbt.setByte("Id", (byte) -1);
        } else {
            nbt.setByte("Id", (byte) potionID);
        }
        nbt.setByte("Amplifier", (byte) this.getAmplifier());
        nbt.setInteger("Duration", this.getDuration());
        nbt.setBoolean("Ambient", this.getIsAmbient());
        cir.setReturnValue(nbt);
    }
}
