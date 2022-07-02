package com.falsepattern.endlessids.mixin.mixins.common.worldedit;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.sk89q.worldedit.blocks.BaseBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = BaseBlock.class,
       remap = false)
public abstract class BaseBlockMixin {
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

}
