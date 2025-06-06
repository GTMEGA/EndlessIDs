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

package com.falsepattern.endlessids.util;

import com.falsepattern.endlessids.constants.VanillaConstants;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.NibbleArray;

import net.minecraftforge.common.util.Constants;

import java.util.Arrays;

import static com.falsepattern.endlessids.constants.ExtendedConstants.blocksPerSubChunk;
import static net.minecraftforge.common.util.Constants.NBT.TAG_INT;
import static net.minecraftforge.common.util.Constants.NBT.TAG_SHORT;

public class DataUtil {
    public static byte[] ensureSubChunkByteArray(byte[] arr) {
        return arr == null ? null : arr.length == blocksPerSubChunk ? arr : Arrays.copyOf(arr, blocksPerSubChunk);
    }

    public static NibbleArray ensureSubChunkNibbleArray(byte[] data) {
        return data == null ? null : new NibbleArray(data.length == (blocksPerSubChunk >>> 1) ? data : Arrays.copyOf(data, (blocksPerSubChunk >>> 1)), 4);
    }

    public static int readIDFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("idExt", TAG_INT))
            return nbt.getInteger("idExt");
        else if (nbt.hasKey("id", TAG_INT))
            return nbt.getInteger("id");
        else if (nbt.hasKey("id", TAG_SHORT))
            return nbt.getShort("id");
        else
            return 0;
    }

    public static void writeIDToNBT(NBTTagCompound nbt, int id) {
        if (id > VanillaConstants.maxItemID) {
            nbt.setShort("id", (short)0);
            nbt.setInteger("idExt", id);
        } else {
            nbt.setShort("id", (short) id);
        }
    }

    public static boolean nbtHasID(NBTTagCompound nbt) {
        return nbt.hasKey("id", TAG_INT) || nbt.hasKey("idExt", TAG_INT) || nbt.hasKey("id", TAG_SHORT);
    }
}
