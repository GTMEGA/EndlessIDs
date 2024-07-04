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

package com.falsepattern.endlessids.mixin.helpers;

import net.minecraft.world.chunk.NibbleArray;

public interface SubChunkBlockHook {
    byte[] getB1();

    void setB1(byte[] data);

    NibbleArray getB2Low();

    void setB2Low(NibbleArray data);

    NibbleArray createB2Low();

    NibbleArray getB2High();

    void setB2High(NibbleArray data);

    NibbleArray createB2High();

    byte[] getB3();

    void setB3(byte[] data);

    byte[] createB3();

    int getMetadataMask();

    int getBlockMask();

    int getID(int x, int y, int z);

    NibbleArray getM1Low();

    void setM1Low(NibbleArray m1Low);

    NibbleArray getM1High();

    void setM1High(NibbleArray m1High);

    NibbleArray createM1High();

    byte[] getM2();

    void setM2(byte[] m2);

    byte[] createM2();
}
