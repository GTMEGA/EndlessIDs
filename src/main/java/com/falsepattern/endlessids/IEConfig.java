package com.falsepattern.endlessids;

import com.falsepattern.lib.config.Config;

@Config(modid = Tags.MODID)
public class IEConfig {
    public static boolean catchUnregisteredBlocks = false;

    @Config.Comment("Remove invalid (corrupted) blocks from the game.")
    public static boolean removeInvalidBlocks = false;

    @Config.Comment("Extend DataWatcher IDs. Vanilla limit is 31, new limit is 127.")
    public static boolean extendDataWatcher = false;

    @Config.Comment(
            "Setting this value greater than 0 will reduce RAM usage, at the cost of less available Block IDs.\n" +
            "Notice: This does not affect ITEM IDs.\n" +
            "By default, block IDs can also go up to 16 Million, and each additional bit in this setting halves\n" +
            "this value.\n" +
            "(0: 16M, 1: 8M, 2: 4M, 3: 2M, 4: 1M, 5: 512K, 6: 256K, 7: 128K, 8: 64K, 9: 32K, 10: 16K, 11: 8K, 12: 4K)\n")
    @Config.RangeInt(min = 0,
                     max = 12)
    public static int countCorrectionBits = 0;
}
