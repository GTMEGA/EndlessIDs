package com.falsepattern.endlessids.mixin.util;

import com.falsepattern.endlessids.mixin.plugin.MixinPlugin;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Loader;

import java.lang.reflect.Field;
import java.util.Collections;

public class DragonAPICompat {
    private static boolean activated = false;

    public static void activate() {
        if (activated) return;
        activated = true;
        Logger LOG = LogManager.getLogger(MixinPlugin.LOG.getName() + " DragonAPI Compat Patcher");
        LOG.info("DragonAPI detected. Activating very early compatibility patches.");
        int total = 1;
        int complete = 0;
        try {
            patchFML(LOG);
            complete++;
        } catch (Throwable t) {
            LOG.fatal("Failed to load early DragonAPI compat patches! Crash imminent! Successful: " + complete + ", Total: " + total, t);
        }
        LOG.info("DragonAPI early compat patching finished! Resuming normal loading.");
    }

    @SneakyThrows
    private static void patchFML(Logger baseLog) {
        Logger LOG = LogManager.getLogger(baseLog.getName() + " FML Tweaker");
        LOG.info("Patching FML loader class...");
        try {
            LOG.info("Attempting to get a reference to " + Loader.class.getName() +
                                  ".namedMods with reflection...");
            Field f;
            try {
                f = Loader.class.getDeclaredField("namedMods");
                LOG.info("[OK] Reference retrieved!");
            } catch (Throwable t) {
                LOG.error("[FAIL] Failed to retrieve the field!");
                throw t;
            }
            LOG.info("[INFO] Making the reference accessible...");
            try {
                f.setAccessible(true);
                LOG.info("[OK] Reference is now accessible!");
            } catch (Throwable t) {
                LOG.error("[FAIL] Failed to make the reference accessible!");
                throw t;
            }

            LOG.info("[INFO] Inserting placeholder Collections.emptyMap() object into the field...");
            try {
                if (f.get(Loader.instance()) != null) {
                    LOG.info("[OK] Field already had a value, not replacing.");
                } else {
                    f.set(Loader.instance(), Collections.emptyMap());
                    LOG.info("[OK] Successfully inserted placeholder object!");
                }
            } catch (Throwable t) {
                LOG.error("[FAIL] Failed to insert placeholder object!");
                throw t;
            }
            LOG.info("FML Loader class patched successfully!");
        } catch (Throwable t) {
            LOG.error("Failed to patch FML Loader class! Check the log file for debug info!");
            throw t;
        }
    }
}
