/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2024 FalsePattern
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
    @Config.Comment("Extend DataWatcher IDs. Vanilla limit is 32, new limit is 65536.\n")
    @Config.DefaultBoolean(true)
    public static boolean extendDataWatcher;
    @Config.Comment("Extend Enchantment IDs. Vanilla limit is 256, new limit is 32768.")
    @Config.DefaultBoolean(true)
    public static boolean extendEnchantment;
    @Config.Comment("Extend Potion IDs. Vanilla limit is 32, new limit is 65536.")
    @Config.DefaultBoolean(true)
    public static boolean extendPotion;
    @Config.Comment("Extend Entity IDs. Vanilla limit is 256, new limit is 32768.")
    @Config.DefaultBoolean(true)
    public static boolean extendEntity;

    @Config.Comment("Improves the speed of the block registry by multiple orders of magnitudes. (not thoroughly tested yet)\n" +
                    "It's highly recommended to leave this enabled, especially when using a lot of IDs.\n" +
                    "With this disabled, registering 1 million blocks on a test system took 80 minutes\n" +
                    "With this enabled, registering the same million blocks took 7 seconds")
    @Config.DefaultBoolean(true)
    public static boolean enableRegistryPerformanceTweak;
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
                    "Effective values: (0: 4k, 1: 8k, 2: 16k, 3: 32k, 4: 64k, 5: 128k, 6: 256k, 7: 512k, 8: 1M, 9: 2M, 10: 4M, 11: 8M, 12: 16M)\n" +
                    "The effective value of this variable must always be smaller or equal to the effective value of extraItemIDBits")
    @Config.RangeInt(min = 0,
                     max = 12)
    @Config.DefaultInt(0)
    public static int extraBlockIDBits;
    @Config.Comment("Use this to tune the amount of available item IDs.\n" +
                    "The vanilla default is 32k item IDs, while the maximum is 16 million.\n" +
                    "The default setting sets it to 32k (vanilla).\n" +
                    "increase if necessary (when running out of ITEM IDs in your modpack)\n" +
                    "Only effective if extendBlockItem is enabled.\n" +
                    "Effective values: (0: 32k, 1: 64k, 2: 128k, 3: 256k, 4: 512k, 5: 1M, 6: 2M, 7: 4M, 8: 8M, 9: 16M)\n" +
                    "The effective value of this variable must always be equal or larger than the effective value of extraBlockIDBits")
    @Config.RangeInt(min = 0,
                     max = 9)
    @Config.DefaultInt(0)
    public static int extraItemIDBits;


    @Config.Comment("DEPRECATED, will be removed in the next version of EndlessIDs!\n" +
                    "This property does nothing.\n" +
                    "Use extraBlockIDBits and extraItemIDBits instead.")
    @Config.DefaultInt(0)
    @Deprecated
    public static int countCorrectionBits;

    static {
        ConfigurationManager.selfInit();
    }
}
