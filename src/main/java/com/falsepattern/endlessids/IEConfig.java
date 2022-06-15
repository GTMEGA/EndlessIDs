package com.falsepattern.endlessids;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class IEConfig {
    static Configuration config;
    public static boolean catchUnregisteredBlocks;
    public static boolean removeInvalidBlocks;
    public static boolean extendDataWatcher;
    public static int countCorrectionBits;
    
    public static void init(final File file) {
        File newFile;
        if (file.getPath().contains("bin")) {
            newFile = new File(file.getParent() + "/eclipse/config", Tags.MODID + ".cfg");
        }
        else {
            newFile = new File(file.getParentFile().getParent() + "/config", Tags.MODID + ".cfg");
        }
        IEConfig.config = new Configuration(newFile);
        IEConfig.catchUnregisteredBlocks = IEConfig.config.getBoolean("CatchUnregisteredBlocks", Tags.MODNAME, false, "");
        IEConfig.removeInvalidBlocks = IEConfig.config.getBoolean("RemoveInvalidBlocks", Tags.MODNAME, false, "Remove invalid (corrupted) blocks from the game.");
        IEConfig.extendDataWatcher = IEConfig.config.getBoolean("ExtendDataWatcher", Tags.MODNAME, false, "Extend DataWatcher IDs. Vanilla limit is 31, new limit is 127.");
        IEConfig.countCorrectionBits = IEConfig.config.getInt("UnusedIDBits", Tags.MODNAME, 0, 0, 12, "Setting this value greater than 0 will reduce RAM usage, at the cost of less available IDs.");
        IEConfig.config.save();
    }
    
    static {
        IEConfig.catchUnregisteredBlocks = false;
        IEConfig.removeInvalidBlocks = false;
        IEConfig.extendDataWatcher = false;
        IEConfig.countCorrectionBits = 0;
    }
}
