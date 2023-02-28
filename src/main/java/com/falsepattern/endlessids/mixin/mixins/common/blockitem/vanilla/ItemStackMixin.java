package com.falsepattern.endlessids.mixin.mixins.common.blockitem.vanilla;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow
    public int stackSize;
    @Shadow
    public NBTTagCompound stackTagCompound;
    @Shadow
    int itemDamage;
    @Shadow
    private Item field_151002_e;

    @Shadow
    public abstract void func_150996_a(Item p_150996_1_);

    /**
     * @author FalsePattern
     * @reason ID extension
     */
    @Overwrite
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("id", Item.getIdFromItem(this.field_151002_e));
        nbt.setByte("Count", (byte) this.stackSize);
        nbt.setShort("Damage", (short) this.itemDamage);

        if (this.stackTagCompound != null) {
            nbt.setTag("tag", this.stackTagCompound);
        }

        return nbt;
    }

    /**
     * @author FalsePattern
     * @reason ID extension
     */
    @Overwrite
    public void readFromNBT(NBTTagCompound nbt) {
        func_150996_a(Item.getItemById(nbt.getInteger("id")));
        this.stackSize = nbt.getByte("Count");
        this.itemDamage = nbt.getShort("Damage");

        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }

        if (nbt.hasKey("tag", 10)) {
            this.stackTagCompound = nbt.getCompoundTag("tag");
        }
    }
}
