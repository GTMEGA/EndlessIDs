/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2025 FalsePattern, The MEGA Team
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the word "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, only version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.falsepattern.endlessids.asm;

import com.falsepattern.endlessids.util.ConfigFixUtil;

public class FCCompat {
    public static void patchConfig() {
        //Breaks with the blockitem module
        ConfigFixUtil.fixConfig("fastcraft.ini", (line) -> {
                                    if (line.contains("asyncCulling") || line.contains("enableCullingTweaks")) {
                                        return line.replace("true", "false");
                                    }
                                    return line;
                                }, () -> "[extra tweaks]\nasyncCulling = false\n[transparent tweaks]\nenableCullingTweaks=false\n",
                                e -> {
                                    System.err.println(
                                            "Failed to apply FastCraft occlusion tweak compatibility patches!");
                                    e.printStackTrace();
                                });
    }
}
