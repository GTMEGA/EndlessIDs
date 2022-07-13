package com.falsepattern.endlessids;

import code.elix_x.coremods.antiidconflict.managers.BiomesManager;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Tags.MODID,
     version = Tags.VERSION,
     name = Tags.MODNAME,
     acceptedMinecraftVersions = "[1.7.10]",
     dependencies = "required-after:falsepatternlib@[0.9.2,);after:antiidconflict")
public class EndlessIDs {
    public static final byte[] BIOME_ARRAY_PLACEHOLDER = new byte[0];
    public static final Logger LOG = LogManager.getLogger(Tags.MODNAME);

    private static void antiIdConflictLatePatch() {
        BiomesManager.conflicts = new BiomesManager.ConflictingBiomes[ExtendedConstants.biomeIDCount];
    }

    /**
     * Trigger earlier biome registration for tropicraft so that it can be caught by AntiIDConflict.
     */
    @SneakyThrows
    private static void tropicraftInitPatch() {
        Class.forName("net.tropicraft.world.biomes.BiomeGenTropicraft");
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        if (Loader.isModLoaded("antiidconflict")) {
            antiIdConflictLatePatch();
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        if (Loader.isModLoaded("tropicraft")) {
            tropicraftInitPatch();
        }
    }
}
