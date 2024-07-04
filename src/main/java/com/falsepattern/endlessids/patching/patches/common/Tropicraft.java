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

package com.falsepattern.endlessids.patching.patches.common;

import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.endlessids.patching.Patch;

public class Tropicraft extends Patch {
    public Tropicraft() {
        super("tropicraft");
    }

    @Override
    public boolean init() throws ClassNotFoundException {
        if (!GeneralConfig.extendBiome) {
            return false;
        }
        Class.forName("net.tropicraft.world.biomes.BiomeGenTropicraft");
        return true;
    }
}
