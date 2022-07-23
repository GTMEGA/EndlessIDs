package com.falsepattern.endlessids.config;

import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.lib.config.Config;
import com.falsepattern.lib.config.ConfigException;
import com.falsepattern.lib.config.ConfigurationManager;

@Config(modid = Tags.MODID, category = "futurepack")
public class FuturepackIDConfig {
    static {
        try {
            ConfigurationManager.registerConfig(FuturepackIDConfig.class);
        } catch (ConfigException e) {
            throw new RuntimeException(e);
        }
    }
    @Config.Comment("Original: 97")
    @Config.RangeInt(min = 40, max = ExtendedConstants.biomeIDCount - 1)
    @Config.DefaultInt(11097)
    public static int spaceID;
}
