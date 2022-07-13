package com.falsepattern.endlessids.patching;

import com.falsepattern.endlessids.EndlessIDs;
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
    }

    @SneakyThrows
    public final void preInit() {
        for (val patch : patches) {
            if (Loader.isModLoaded(patch.modid)) {
                try {
                    if (patch.preInit()) {
                        EndlessIDs.LOG.info("Applied preInit patches for " + patch.modid);
                    }
                } catch (Exception e) {
                    EndlessIDs.LOG.fatal("Failed to apply preInit patches for " + patch.modid, e);
                    throw e;
                }
            }
        }
    }

    @SneakyThrows
    public final void init() {
        for (val patch : patches) {
            if (Loader.isModLoaded(patch.modid)) {
                try {
                    if (patch.init()) {
                        EndlessIDs.LOG.info("Applied init patches for " + patch.modid);
                    }
                } catch (Exception e) {
                    EndlessIDs.LOG.fatal("Failed to apply init patches for " + patch.modid, e);
                    throw e;
                }
            }
        }

    }

    @SneakyThrows
    public final void postInit() {
        for (val patch : patches) {
            if (Loader.isModLoaded(patch.modid)) {
                try {
                    if (patch.postInit()) {
                        EndlessIDs.LOG.info("Applied postInit patches for " + patch.modid);
                    }
                } catch (Exception e) {
                    EndlessIDs.LOG.fatal("Failed to apply postInit patches for " + patch.modid, e);
                    throw e;
                }
            }
        }

    }
}
