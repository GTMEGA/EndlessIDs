package com.falsepattern.endlessids.patching;

import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.patching.patches.common.Futurepack;
import com.falsepattern.endlessids.patching.patches.common.Tropicraft;
import lombok.SneakyThrows;
import lombok.val;

import cpw.mods.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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

    private void runPatches(String stageName, Function<Patch, Boolean> stageRunner) {
        for (val patch: patches) {
            if (Loader.isModLoaded(patch.modid)) {
                try {
                    if (stageRunner.apply(patch)) {
                        EndlessIDs.LOG.info("Applied patch " + patch.getClass().getSimpleName() + " stage " + stageName + " for " + patch.modid);
                    }
                } catch (Exception e) {
                    EndlessIDs.LOG.fatal("Failed to apply patch " + patch.getClass().getSimpleName() + " stage " + stageName + " for " + patch.modid);
                    throw e;
                }
            }
        }
    }
}
