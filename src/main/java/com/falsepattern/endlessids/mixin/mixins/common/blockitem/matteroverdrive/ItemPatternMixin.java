package com.falsepattern.endlessids.mixin.mixins.common.blockitem.matteroverdrive;

import com.falsepattern.endlessids.constants.VanillaConstants;
import io.netty.buffer.ByteBuf;
import matteroverdrive.data.ItemPattern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

@Mixin(value = ItemPattern.class,
       remap = false)
public abstract class ItemPatternMixin {
    @Shadow private int itemID;

    @Shadow private int progress;

    @Shadow private int damage;

    @Shadow private int count;

    @Inject(method = "writeToNBT",
            at = @At("RETURN"),
            require = 1)
    private void writeToNBT$writeInt(NBTTagCompound nbtTagCompound, CallbackInfo ci) {
        int id = this.itemID;
        if (id > VanillaConstants.maxItemID) {
            nbtTagCompound.setShort("id", (short) 0);
        }
        nbtTagCompound.setInteger("idExt", id);
    }

    @Inject(method = "readFromNBT",
            at = @At("RETURN"),
            require = 1)
    private void readFromNBT$readInt(NBTTagCompound nbtTagCompound, CallbackInfo ci) {
        if (nbtTagCompound.hasKey("idExt", Constants.NBT.TAG_INT))
            this.itemID = nbtTagCompound.getInteger("idExt");
    }

    /**
     * @author FalsePattern
     * @reason Extend IDs
     */
    @Overwrite
    public void writeToBuffer(ByteBuf byteBuf) {
        byteBuf.writeInt(this.itemID);
        byteBuf.writeByte(this.progress);
        byteBuf.writeShort(this.damage);
        byteBuf.writeByte(this.count);
    }


    /**
     * @author FalsePattern
     * @reason Extend IDs
     */
    @Overwrite
    public void readFromBuffer(ByteBuf byteBuf) {
        this.itemID = byteBuf.readInt();
        this.progress = byteBuf.readByte();
        this.damage = byteBuf.readShort();
        this.count = byteBuf.readByte();
    }
}
