package com.falsepattern.endlessids;

import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class IEConfig
{
    static Configuration config;
    public static boolean catchUnregisteredBlocks;
    public static boolean removeInvalidBlocks;
    public static boolean postNeidWorldsSupport;
    public static boolean extendDataWatcher;
    
    public IEConfig() {
        super();
    }
    
    public static void init(final File file) {
        File newFile;
        if (file.getPath().contains("bin")) {
            newFile = new File(file.getParent() + "/eclipse/config", "NEID.cfg");
        }
        else {
            newFile = new File(file.getParentFile().getParent() + "/config", "NEID.cfg");
        }
        IEConfig.config = new Configuration(newFile);
        IEConfig.catchUnregisteredBlocks = IEConfig.config.getBoolean("CatchUnregisteredBlocks", "NEID", false, "");
        IEConfig.removeInvalidBlocks = IEConfig.config.getBoolean("RemoveInvalidBlocks", "NEID", false, "Remove invalid (corrupted) blocks from the game.");
        IEConfig.postNeidWorldsSupport = IEConfig.config.getBoolean("PostNeidWorldsSupport", "NEID", true, "If true, only blocks with IDs > 4095 will disappear after removing NEID.");
        IEConfig.extendDataWatcher = IEConfig.config.getBoolean("ExtendDataWatcher", "NEID", false, "Extend DataWatcher IDs. Vanilla limit is 31, new limit is 127.");
        IEConfig.config.save();
    }
    
    static {
        IEConfig.catchUnregisteredBlocks = false;
        IEConfig.removeInvalidBlocks = false;
        IEConfig.postNeidWorldsSupport = true;
        IEConfig.extendDataWatcher = false;
    }
}
