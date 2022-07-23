package com.falsepattern.endlessids.config;

import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.lib.config.Config;

@Config(modid = Tags.MODID, category = "DarkWorld")
public class DarkWorldIDConfig {
    @Config.Comment("Original: 100")
    @Config.RangeInt(min = 40, max = ExtendedConstants.biomeIDCount - 1)
    @Config.DefaultInt(10100)
    public static int darkOceanID;

    @Config.Comment("Original: 101")
    @Config.RangeInt(min = 40, max = ExtendedConstants.biomeIDCount - 1)
    @Config.DefaultInt(10101)
    public static int darkPlainsID;

    @Config.Comment("Original: 102")
    @Config.RangeInt(min = 40, max = ExtendedConstants.biomeIDCount - 1)
    @Config.DefaultInt(10102)
    public static int darkDesertID;

    @Config.Comment("Original: 103")
    @Config.RangeInt(min = 40, max = ExtendedConstants.biomeIDCount - 1)
    @Config.DefaultInt(10103)
    public static int darkHillsID;

    @Config.Comment("Original: 104")
    @Config.RangeInt(min = 40, max = ExtendedConstants.biomeIDCount - 1)
    @Config.DefaultInt(10104)
    public static int darkForestID;

    @Config.Comment("Original: 105")
    @Config.RangeInt(min = 40, max = ExtendedConstants.biomeIDCount - 1)
    @Config.DefaultInt(10105)
    public static int darkTaigaID;

    @Config.Comment("Original: 106")
    @Config.RangeInt(min = 40, max = ExtendedConstants.biomeIDCount - 1)
    @Config.DefaultInt(10106)
    public static int darkSwampID;


}
