package com.falsepattern.endlessids.config;

import com.falsepattern.endlessids.Tags;
import com.falsepattern.lib.config.Config;
import com.falsepattern.lib.config.ConfigurationManager;

@Config(modid = Tags.MODID)
public class GeneralConfig {
    @Config.Comment(
            "WARNING: THIS CONFIG IS EXTREMELY SENSITIVE TO CHANGES, DISABLING STUFF ONCE THEY HAVE BEEN ENABLED\n" +
            "CAN CORRUPT YOUR WORLD! ONLY TOUCH THIS FILE IF YOU KNOW WHAT YOU'RE DOING!\n" +
            "This file should only be edited by modpack developers to finetune it for the modpack. Once an option\n" +
            "has been enabled, it modifies the chunk save data format, which is (partially) compatible with\n" +
            "vanilla, but removing the mod might cause block/item corruption for higher IDs.\n" +
            "Additional note: Every client and server must have the exact same config, this also affects netcode.")
    @Config.DefaultString("")
    public static String __NOTICE__;
    @Config.Comment("Throw an exception when an invalid block is registered. FOR DEBUGGING PURPOSES")
    @Config.DefaultBoolean(false)
    public static boolean catchUnregisteredBlocks;
    @Config.Comment("Remove invalid (corrupted) blocks from the game. FOR DEBUGGING PURPOSES")
    @Config.DefaultBoolean(false)
    public static boolean removeInvalidBlocks;
    @Config.Comment("Extend Biome IDs. Vanilla limit is 256, new limit is 65536.")
    @Config.DefaultBoolean(true)
    public static boolean extendBiome;
    @Config.Comment("Extend Block and Item IDs. Vanilla limit is 4096/32000, new limit is 16777216 for both.\n" +
                    "See also: countCorrectionBits")
    @Config.DefaultBoolean(true)
    public static boolean extendBlockItem;
    @Config.Comment("Extend DataWatcher IDs. Vanilla limit is 31, new limit is 127.\n" +
                    "Disabled by default because of uncertain/weak implementation (taken from NotEnoughIDs)")
    @Config.DefaultBoolean(false)
    public static boolean extendDataWatcher;
    @Config.Comment("Extend Enchantment IDs. Vanilla limit is 256, new limit is 32768.")
    @Config.DefaultBoolean(true)
    public static boolean extendEnchantment;
    @Config.Comment("Extend Potion IDs. Vanilla limit is 32, new limit is 65536.")
    @Config.DefaultBoolean(true)
    public static boolean extendPotion;
    @Config.Comment(
            "Extends the maximum redstone signal strength.\n" + "Only has effect with extendBlockItem enabled.\n" +
            "WARNING: THIS IS HERE ONLY FOR FUN, IT WILL DEFINITELY CORRUPT YOUR WORLD!")
    @Config.DefaultBoolean(false)
    public static boolean extendRedstone;
    @Config.Comment("Increases the max signal strength of redstone.\n" +
                    "Only has effect with both extendBlockItem and extendRedstone enabled.\n" + "Vanilla value is 15.")
    @Config.RangeInt(min = 15,
                     max = 127)
    @Config.DefaultInt(15)
    public static int maxRedstone;
    @Config.Comment("Use this to tune the amount of available block IDs.\n" +
                    "Minecraft contains some internal code that uses a HUGE amount of RAM with too many block IDs available.\n" +
                    "Notice: This does not affect ITEM IDs.\n" +
                    "The vanilla default is 4k block IDs, while the maximum is 16 million.\n" +
                    "The default setting sets it to 4k (vanilla).\n" +
                    "increase if necessary (when running out of BLOCK IDs in your modpack)\n" +
                    "Only effective if extendBlockItem is enabled.\n" +
                    "(0: 16M, 1: 8M, 2: 4M, 3: 2M, 4: 1M, 5: 512K, 6: 256K, 7: 128K, 8: 64K, 9: 32K, 10: 16K, 11: 8K, 12: 4K)\n")
    @Config.RangeInt(min = 0,
                     max = 12)
    @Config.DefaultInt(12)
    public static int countCorrectionBits;

    static {
        ConfigurationManager.selfInit();
    }
}
