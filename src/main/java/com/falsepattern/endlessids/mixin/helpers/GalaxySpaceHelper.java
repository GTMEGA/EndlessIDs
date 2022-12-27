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
