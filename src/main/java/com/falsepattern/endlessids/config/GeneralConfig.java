package com.falsepattern.endlessids.config;

import com.falsepattern.endlessids.Tags;
import com.falsepattern.lib.config.Config;
import com.falsepattern.lib.config.ConfigurationManager;

@Config(modid = Tags.MODID)
public class GeneralConfig {
    static {
        ConfigurationManager.selfInit();
    }
    @Config.DefaultBoolean(false)
    public static boolean catchUnregisteredBlocks;

    @Config.Comment("Remove invalid (corrupted) blocks from the game.")
    @Config.DefaultBoolean(false)
    public static boolean removeInvalidBlocks;

    @Config.Comment("Extend DataWatcher IDs. Vanilla limit is 31, new limit is 127.")
    @Config.DefaultBoolean(false)
    public static boolean extendDataWatcher;

    @Config.Comment(
            "Setting this value greater than 0 will reduce RAM usage, at the cost of less available Block IDs.\n" +
            "Notice: This does not affect ITEM IDs.\n" +
            "By default, block IDs can also go up to 16 Million, and each additional bit in this setting halves\n" +
            "this value.\n" +
            "(0: 16M, 1: 8M, 2: 4M, 3: 2M, 4: 1M, 5: 512K, 6: 256K, 7: 128K, 8: 64K, 9: 32K, 10: 16K, 11: 8K, 12: 4K)\n")
    @Config.RangeInt(min = 0,
                     max = 12)
    @Config.DefaultInt(0)
    public static int countCorrectionBits;
}
