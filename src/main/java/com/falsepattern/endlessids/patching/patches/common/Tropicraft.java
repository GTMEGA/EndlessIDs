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
