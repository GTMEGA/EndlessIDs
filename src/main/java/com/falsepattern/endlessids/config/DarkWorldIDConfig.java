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
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.lib.config.Config;
import com.falsepattern.lib.config.ConfigurationManager;

@Config(modid = Tags.MODID,
        category = "darkworld")
public class DarkWorldIDConfig {
    @Config.Comment("Original: 100")
    @Config.RangeInt(min = 40,
                     max = ExtendedConstants.biomeIDCount - 1)
    @Config.DefaultInt(1100)
    public static int darkOceanID;
    @Config.Comment("Original: 101")
    @Config.RangeInt(min = 40,
                     max = ExtendedConstants.biomeIDCount - 1)
    @Config.DefaultInt(1101)
    public static int darkPlainsID;
    @Config.Comment("Original: 102")
    @Config.RangeInt(min = 40,
                     max = ExtendedConstants.biomeIDCount - 1)
    @Config.DefaultInt(1102)
    public static int darkDesertID;
    @Config.Comment("Original: 103")
    @Config.RangeInt(min = 40,
                     max = ExtendedConstants.biomeIDCount - 1)
    @Config.DefaultInt(1103)
    public static int darkHillsID;
    @Config.Comment("Original: 104")
    @Config.RangeInt(min = 40,
                     max = ExtendedConstants.biomeIDCount - 1)
    @Config.DefaultInt(1104)
    public static int darkForestID;
    @Config.Comment("Original: 105")
    @Config.RangeInt(min = 40,
                     max = ExtendedConstants.biomeIDCount - 1)
    @Config.DefaultInt(1105)
    public static int darkTaigaID;
    @Config.Comment("Original: 106")
    @Config.RangeInt(min = 40,
                     max = ExtendedConstants.biomeIDCount - 1)
    @Config.DefaultInt(1106)
    public static int darkSwampID;

    static {
        ConfigurationManager.selfInit();
    }


}
