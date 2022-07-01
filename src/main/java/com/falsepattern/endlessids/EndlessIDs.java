package com.falsepattern.endlessids;

import code.elix_x.coremods.antiidconflict.managers.BiomesManager;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MODID,
     version = Tags.VERSION,
     name = Tags.MODNAME,
     acceptedMinecraftVersions = "[1.7.10]",
     dependencies = "required-after:falsepatternlib@[0.8.2,);")
public class EndlessIDs {
    public static final Logger LOG = LogManager.getLogger(Tags.MODNAME);

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent e) {
        if (Loader.isModLoaded("antiidconflict")) {
            antiIdConflictLatePatch();
        }
    }

    private static void antiIdConflictLatePatch() {
        BiomesManager.conflicts = new BiomesManager.ConflictingBiomes[ExtendedConstants.biomeIDCount];
    }
}
