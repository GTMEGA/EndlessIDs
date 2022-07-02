package com.falsepattern.endlessids;

import code.elix_x.coremods.antiidconflict.managers.BiomesManager;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.world.biome.BiomeGenBase;

@Mod(modid = Tags.MODID,
     version = Tags.VERSION,
     name = Tags.MODNAME,
     acceptedMinecraftVersions = "[1.7.10]",
     dependencies = "required-after:falsepatternlib@[0.8.2,);\n" +
                    "after:antiidconflict")
public class EndlessIDs {
    public static final Logger LOG = LogManager.getLogger(Tags.MODNAME);
    public static BiomeGenBase[] fakeBiomeArray;
    public static boolean postInitFinished = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        if (Loader.isModLoaded("antiidconflict")) {
            antiIdConflictLatePatch();
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        postInitFinished = true;
        fakeBiomeArray = null;
    }

    private static void antiIdConflictLatePatch() {
        BiomesManager.conflicts = new BiomesManager.ConflictingBiomes[ExtendedConstants.biomeIDCount];
    }
}
