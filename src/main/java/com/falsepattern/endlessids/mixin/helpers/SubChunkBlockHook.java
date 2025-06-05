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

package com.falsepattern.endlessids.mixin.helpers;

import net.minecraft.world.chunk.NibbleArray;

public interface SubChunkBlockHook {
    byte[] eid$getB1();

    void eid$setB1(byte[] data);

    NibbleArray eid$getB2Low();

    void eid$setB2Low(NibbleArray data);

    NibbleArray eid$createB2Low();

    NibbleArray eid$getB2High();

    void eid$setB2High(NibbleArray data);

    NibbleArray eid$createB2High();

    byte[] eid$getB3();

    void eid$setB3(byte[] data);

    byte[] eid$createB3();

    int eid$getBlockMask();

    int eid$getID(int x, int y, int z);

    void eid$setID(int x, int y, int z, int id);

    NibbleArray eid$getM1Low();

    void eid$setM1Low(NibbleArray m1Low);

    NibbleArray eid$getM1High();

    void eid$setM1High(NibbleArray m1High);

    NibbleArray eid$createM1High();

    byte[] eid$getM2();

    void eid$setM2(byte[] m2);

    byte[] eid$createM2();

    int eid$getMetadataMask();

    int eid$getMetadata(int x, int y, int z);

    void eid$setMetadata(int x, int y, int z, int id);
}
