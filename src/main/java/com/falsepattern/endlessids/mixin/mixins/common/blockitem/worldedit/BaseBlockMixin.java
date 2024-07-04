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

package com.falsepattern.endlessids.mixin.mixins.common.blockitem.worldedit;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.sk89q.worldedit.blocks.BaseBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = BaseBlock.class,
       remap = false)
public abstract class BaseBlockMixin {
    @Shadow
    private short data;
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
            this.data = (short) data;
        }
    }

}
