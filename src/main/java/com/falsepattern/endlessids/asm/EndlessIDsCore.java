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

package com.falsepattern.endlessids.asm;

import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.config.GeneralConfig;
import lombok.SneakyThrows;
import lombok.val;

import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;
import java.util.Set;

import static cpw.mods.fml.relauncher.IFMLLoadingPlugin.DependsOn;
import static cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import static cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import static cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@MCVersion("1.7.10")
@Name(Tags.MODID + "_core")
@TransformerExclusions({"com.falsepattern.endlessids.asm", "com.falsepattern.endlessids.config.GeneralConfig"})
@DependsOn("falsepatternlib")
public class EndlessIDsCore implements IFMLLoadingPlugin {
    public static boolean deobfuscated;
    public EndlessIDsCore() {
        super();
    }

    @SneakyThrows
    public String[] getASMTransformerClass() {
        deobfuscated = (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
        val cl = Launch.classLoader;
        val field = LaunchClassLoader.class.getDeclaredField("transformerExceptions");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        val exceptions = (Set<String>) field.get(cl);
        if (exceptions.contains("code.elix_x.coremods")) {
            EndlessIDsTransformer.logger.info("AntiIDConflict detected!");
            EndlessIDsTransformer.logger.info("Removing ASM protections so that we can fix its code.");
            exceptions.remove("code.elix_x.coremods");
            exceptions.add("code.elix_x.coremods.antiidconflict.core");
            exceptions.add("code.elix_x.coremods.antiidconflict.ByteCodeTester");
        }
        return new String[]{EndlessIDsTransformer.class.getName()};
    }

    public String getModContainerClass() {
        if (GeneralConfig.extendBiome) {
            FMLInjectionData.containers.add(Tags.GROUPNAME + ".containers.BiomeContainer");
        }
        if (GeneralConfig.extendBlockItem) {
            FMLInjectionData.containers.add(Tags.GROUPNAME + ".containers.BlockItemContainer");
        }
        if (GeneralConfig.extendDataWatcher) {
            FMLInjectionData.containers.add(Tags.GROUPNAME + ".containers.DataWatcherContainer");
        }
        if (GeneralConfig.extendEnchantment) {
            FMLInjectionData.containers.add(Tags.GROUPNAME + ".containers.EnchantmentContainer");
        }
        if (GeneralConfig.extendPotion) {
            FMLInjectionData.containers.add(Tags.GROUPNAME + ".containers.PotionContainer");
        }
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(final Map<String, Object> data) {
    }

    public String getAccessTransformerClass() {
        return null;
    }
}
