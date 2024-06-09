package com.falsepattern.endlessids.mixin.mixins.common.blockitem.matteroverdrive;

import com.falsepattern.endlessids.util.DataUtil;
import matteroverdrive.data.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.nbt.NBTTagCompound;

@Mixin(Inventory.class)
public abstract class InventoryMixin {
    @Redirect(method = "readFromNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/nbt/NBTTagCompound;hasKey(Ljava/lang/String;I)Z"),
              require = 1)
    private boolean fixHasKey(NBTTagCompound instance, String p_150297_1_, int p_150297_2_) {
        return DataUtil.nbtHasID(instance);
    }
}
