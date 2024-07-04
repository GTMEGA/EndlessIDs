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

package com.falsepattern.endlessids.patching;

import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.patching.patches.common.Futurepack;
import com.falsepattern.endlessids.patching.patches.common.Tropicraft;
import lombok.SneakyThrows;
import lombok.val;

import cpw.mods.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;

public class CommonManager {
    protected final List<Patch> patches = new ArrayList<>();

    public CommonManager() {
        patches.add(new Tropicraft());
        patches.add(new Futurepack());
    }

    @SneakyThrows
    public final void construct() {
        runPatches("construct", Patch::construct);
    }

    @SneakyThrows
    public final void preInit() {
        runPatches("preInit", Patch::preInit);
    }

    @SneakyThrows
    public final void init() {
        runPatches("init", Patch::init);
    }

    @SneakyThrows
    public final void postInit() {
        runPatches("postInit", Patch::postInit);
    }

    private void runPatches(String stageName, Stage stage) throws Exception {
        for (val patch : patches) {
            if (Loader.isModLoaded(patch.modid)) {
                try {
                    if (stage.run(patch)) {
                        EndlessIDs.LOG.info("Applied patch " + patch.getClass().getSimpleName() + " stage " + stageName + " for " + patch.modid);
                    }
                } catch (Exception e) {
                    EndlessIDs.LOG.fatal("Failed to apply patch " + patch.getClass().getSimpleName() + " stage " + stageName + " for " + patch.modid);
                    throw e;
                }
            }
        }
    }

    public interface Stage {
        boolean run(Patch patch) throws Exception;
    }
}
