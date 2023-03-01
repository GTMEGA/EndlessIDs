package com.falsepattern.endlessids.patching.patches.common;

import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.endlessids.patching.Patch;

public class Futurepack extends Patch {
    public Futurepack() {
        super("fp");
    }

    @Override
    public boolean construct() throws Exception {
        if (!GeneralConfig.extendBiome) {
            return false;
        }
        Class.forName("futurepack.common.dim.BiomeGenSpace");
        return true;
    }
}
