/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2025 FalsePattern, The MEGA Team
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
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

package com.falsepattern.endlessids;

import com.falsepattern.chunk.api.DataRegistry;
import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.endlessids.managers.BiomeManager;
import com.falsepattern.endlessids.managers.BlockIDManager;
import com.falsepattern.endlessids.managers.BlockMetaManager;
import com.falsepattern.endlessids.patching.CommonManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Tags.MODID,
     version = Tags.VERSION,
     name = Tags.MODNAME,
     acceptedMinecraftVersions = "[1.7.10]",
     dependencies = "required-after:chunkapi@[0.6.4,);" +
                    "required-after:falsepatternlib@[1.8.1,);" +
                    "after:antiidconflict")
public class EndlessIDs {
    public static final byte[] ZERO_LENGTH_BIOME_ARRAY_PLACEHOLDER = new byte[0];
    public static final byte[] FAKE_BIOME_ARRAY_PLACEHOLDER = new byte[256];
    public static final Logger LOG = LogManager.getLogger(Tags.MODNAME);

    @SidedProxy(clientSide = Tags.GROUPNAME + ".patching.ClientManager",
                serverSide = Tags.GROUPNAME + ".patching.ServerManager",
                modId = Tags.MODID)
    private static CommonManager patchManager;

    @Mod.EventHandler
    public void construct(FMLConstructionEvent e) {
        patchManager.construct();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        LOG.info("---------------------------------------------------------");
        LOG.info("Current EndlessIDs configuration:");
        logConfig("  Biome Extension", GeneralConfig.extendBiome);
        logConfig("  Block/Item Extension", GeneralConfig.extendBlockItem);
        if (GeneralConfig.extendBlockItem) {
            logConfig("    Extra Item Bits", GeneralConfig.extraItemIDBits);
            logConfig("    Extra Block Bits", GeneralConfig.extraBlockIDBits);
        }
        logConfig("  DataWatcher Extension", GeneralConfig.extendDataWatcher);
        logConfig("  Enchantment Extension", GeneralConfig.extendEnchantment);
        logConfig("  Potion Extension", GeneralConfig.extendPotion);
        logConfig("  Redstone Extension", GeneralConfig.extendRedstone);
        if (GeneralConfig.extendRedstone) {
            LOG.warn("    (Redstone extension is an experimental and dangerous feature, and should NEVER be used in worlds that you worry about getting corrupted!)");
            logConfig("    Max redstone level", GeneralConfig.maxRedstone);
        }
        logConfig("  Registry performance tweak", GeneralConfig.enableRegistryPerformanceTweak);
        if (GeneralConfig.enableRegistryPerformanceTweak) {
            LOG.warn("    (This tweak has not gone through proper testing yet, so it might corrupt worlds. USE BACKUPS!)");
        }
        LOG.info("---------------------------------------------------------");
        patchManager.preInit();
    }

    private static void logConfig(String name, boolean value) {
        LOG.info(name + (value ? ": ENABLED" : ": disabled"));
    }

    private static void logConfig(String name, int value) {
        LOG.info(name + ": " + value);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        patchManager.init();
        if (GeneralConfig.extendBiome) {
            DataRegistry.registerDataManager(new BiomeManager());
        }
        if (GeneralConfig.extendBlockItem) {
            DataRegistry.registerDataManager(new BlockIDManager());
            DataRegistry.registerDataManager(new BlockMetaManager());
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        patchManager.postInit();
        if (GeneralConfig.extendBiome) {
            DataRegistry.disableDataManager("minecraft", "biome");
        }
        if (GeneralConfig.extendBlockItem) {
            DataRegistry.disableDataManager("minecraft", "blockid");
            DataRegistry.disableDataManager("minecraft", "metadata");
        }
    }
}
