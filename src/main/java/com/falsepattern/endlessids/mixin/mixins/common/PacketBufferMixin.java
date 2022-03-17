package com.falsepattern.endlessids.mixin.mixins.common;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.io.IOException;

@Mixin(PacketBuffer.class)
public abstract class PacketBufferMixin {

    @Shadow public abstract int readInt();

    @Shadow public abstract short readShort();

    @Shadow public abstract byte readByte();

    @Shadow public abstract NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException;

    @Shadow public abstract ByteBuf writeInt(int p_writeInt_1_);

    @Shadow public abstract ByteBuf writeByte(int p_writeByte_1_);

    @Shadow public abstract ByteBuf writeShort(int p_writeShort_1_);

    @Shadow public abstract void writeNBTTagCompoundToBuffer(NBTTagCompound p_150786_1_) throws IOException;

    /**
     * @author FalsePattern
     * @reason ID extension
     */
    @Overwrite
    public void writeItemStackToBuffer(ItemStack p_150788_1_) throws IOException
    {
        if (p_150788_1_ == null)
        {
            this.writeInt(-1);
        }
        else
        {
            this.writeInt(Item.getIdFromItem(p_150788_1_.getItem()));
            this.writeByte(p_150788_1_.stackSize);
            this.writeShort(p_150788_1_.getItemDamage());
            NBTTagCompound nbttagcompound = null;

            if (p_150788_1_.getItem().isDamageable() || p_150788_1_.getItem().getShareTag())
            {
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
    public ItemStack readItemStackFromBuffer() throws IOException
    {
        ItemStack itemstack = null;
        int id = this.readInt();

        if (id >= 0)
        {
            byte b0 = this.readByte();
            short damage = this.readShort();
            itemstack = new ItemStack(Item.getItemById(id), b0, damage);
            itemstack.stackTagCompound = this.readNBTTagCompoundFromBuffer();
        }

        return itemstack;
    }
}
