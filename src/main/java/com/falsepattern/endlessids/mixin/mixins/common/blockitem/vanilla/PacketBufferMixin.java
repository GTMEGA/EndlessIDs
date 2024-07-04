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

package com.falsepattern.endlessids.mixin.mixins.common.blockitem.vanilla;

import io.netty.buffer.ByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

@Mixin(value = PacketBuffer.class, priority = 999)
public abstract class PacketBufferMixin {

    @Shadow
    public abstract int readInt();

    @Shadow
    public abstract short readShort();

    @Shadow
    public abstract byte readByte();

    @Shadow
    public abstract NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException;

    @Shadow
    public abstract ByteBuf writeInt(int p_writeInt_1_);

    @Shadow
    public abstract ByteBuf writeByte(int p_writeByte_1_);

    @Shadow
    public abstract ByteBuf writeShort(int p_writeShort_1_);

    @Shadow
    public abstract void writeNBTTagCompoundToBuffer(NBTTagCompound p_150786_1_) throws IOException;

    /**
     * @author FalsePattern
     * @reason ID extension
     */
    @Overwrite
    public void writeItemStackToBuffer(ItemStack p_150788_1_) throws IOException {
        if (p_150788_1_ == null) {
            this.writeInt(-1);
        } else {
            this.writeInt(Item.getIdFromItem(p_150788_1_.getItem()));
            this.writeByte(p_150788_1_.stackSize);
            this.writeShort(p_150788_1_.getItemDamage());
            NBTTagCompound nbttagcompound = null;

            if (p_150788_1_.getItem().isDamageable() || p_150788_1_.getItem().getShareTag()) {
                nbttagcompound = p_150788_1_.stackTagCompound;
            }

            this.writeNBTTagCompoundToBuffer(nbttagcompound);
        }
    }

    /**
     * @author FalsePattern
     * @reason ID extension
     */
    @Overwrite
    public ItemStack readItemStackFromBuffer() throws IOException {
        ItemStack itemstack = null;
        int id = this.readInt();

        if (id >= 0) {
            byte b0 = this.readByte();
            short damage = this.readShort();
            itemstack = new ItemStack(Item.getItemById(id), b0, damage);
            itemstack.stackTagCompound = this.readNBTTagCompoundFromBuffer();
        }

        return itemstack;
    }
}
