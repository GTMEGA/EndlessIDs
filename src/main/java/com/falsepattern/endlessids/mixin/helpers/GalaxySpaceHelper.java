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

import com.falsepattern.endlessids.EndlessIDs;

import net.minecraft.world.biome.BiomeGenBase;

import java.lang.reflect.Field;

public class GalaxySpaceHelper {
    private static final Field GSSpaceField;

    static {
        Class<?> clazz;
        try {
            clazz = Class.forName("galaxyspace.core.world.gen.GSBiomeGenBase");
        } catch (ClassNotFoundException ignored) {
            EndlessIDs.LOG.warn("Could not find GalaxySpace's GSBiomeGenBase class in the default package. Checking it in the MEGA package.");
            try {
                clazz = Class.forName("galaxyspace.core.world.GSBiomeGenBase");
            } catch (ClassNotFoundException ignored2) {
                EndlessIDs.LOG.warn("Could not find GalaxySpace's GSBiomeGenBase class in the MEGA package either. Throwing an exception.");
                throw new IllegalStateException("Could not find GSBiomeGenBase class for GalaxySpace!");
            }
        }
        try {
            GSSpaceField = clazz.getDeclaredField("GSSpace");
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("Could not find GSSpace field in GSBiomeGenBase class for GalaxySpace!");
        }
    }

    public static BiomeGenBase GSSpace() {
        try {
            return (BiomeGenBase) GSSpaceField.get(null);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Could not access GSSpace field in GSBiomeGenBase class for GalaxySpace!");
        }
    }
}
