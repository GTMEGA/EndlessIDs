/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2025 FalsePattern, The MEGA Team
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the word "MEGA"
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

import com.falsepattern.endlessids.mixin.helpers.EntityRegistryAccessor;
import lombok.val;

import net.minecraft.entity.Entity;
import cpw.mods.fml.common.registry.EntityRegistry;

import java.util.HashMap;
import java.util.Map;

public class RemappingEntityIDMap extends HashMap<Integer, Class<? extends Entity>> {
    public RemappingEntityIDMap(Map<Integer, Class<? extends Entity>> idToClassMapping) {
        super(idToClassMapping);
    }

    @Override
    public Class<? extends Entity> put(Integer key, Class<? extends Entity> value) {
        val availableIndicies = ((EntityRegistryAccessor) EntityRegistry.instance()).eids$availableIndicies();
        availableIndicies.clear(key);
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Class<? extends Entity>> m) {
        val availableIndicies = ((EntityRegistryAccessor) EntityRegistry.instance()).eids$availableIndicies();
        for (val entry : m.entrySet()) {
            availableIndicies.clear(entry.getKey());
        }
        super.putAll(m);
    }
}
