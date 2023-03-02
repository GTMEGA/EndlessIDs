package com.falsepattern.endlessids.mixin.mixins.common.blockitem.worldedit;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.sk89q.worldedit.blocks.BaseBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = BaseBlock.class,
       remap = false)
public abstract class BaseBlockMixin {
    @Shadow private short data;
    private int idExtended;

    /**
     * @author FalsePattern
     * @reason ID limit extension
     */
    @Overwrite
    protected final void internalSetId(int id) {
        if (id > ExtendedConstants.maxBlockID) {
            throw new IllegalArgumentException(
                    "Can't have a block ID above " + ExtendedConstants.maxBlockID + " (" + id + " given)");
        } else if (id < 0) {
            throw new IllegalArgumentException("Can't have a block ID below 0");
        } else {
            this.idExtended = id;
        }
    }

    /**
     * @author FalsePattern
     * @reason ID limit extension
     */
    @Overwrite
    public int getId() {
        return idExtended;
    }

    /**
     * @author FalsePattern
     * @reason ID limit extension
     */
    @Overwrite
    public int getData() {
        return this.data & 0xFFFF;
    }


    /**
     * @author FalsePattern
     * @reason ID limit extension
     */
    @Overwrite
    protected final void internalSetData(int data) {
        if (data > 0xFFFF) {
            throw new IllegalArgumentException("Can't have a block data value above 65535 (" + data + " given)");
        } else if (data < -1) {
            throw new IllegalArgumentException("Can't have a block data value below -1");
        } else {
            this.data = (short)data;
        }
    }

}
