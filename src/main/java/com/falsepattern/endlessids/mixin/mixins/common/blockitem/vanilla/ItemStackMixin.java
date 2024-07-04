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

import com.falsepattern.endlessids.constants.VanillaConstants;
import com.falsepattern.endlessids.util.DataUtil;
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
        DataUtil.writeIDToNBT(nbt, Item.getIdFromItem(this.field_151002_e));
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
        func_150996_a(Item.getItemById(DataUtil.readIDFromNBT(nbt)));
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
