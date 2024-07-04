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

package com.falsepattern.endlessids.containers;

import com.falsepattern.endlessids.Tags;
import com.google.common.eventbus.EventBus;
import lombok.val;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;

public class _Dummy extends DummyModContainer {
    public _Dummy(String id, String name) {
        super(createMetadata(id, name));
    }

    private static ModMetadata createMetadata(String id, String name) {
        val meta = new ModMetadata();
        meta.modId = Tags.MODID + "_" + id;
        meta.name = Tags.MODNAME + " " + name + " Module";
        meta.version = Tags.VERSION;
        meta.dependencies.add(new DefaultArtifactVersion(Tags.MODID, Tags.VERSION));
        meta.parent = Tags.MODID;
        return meta;
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        return true;
    }
}
