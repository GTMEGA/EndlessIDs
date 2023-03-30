package com.falsepattern.endlessids;

import com.falsepattern.chunk.api.ChunkDataRegistry;
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
     dependencies = "required-after:falsepatternlib@[0.10.13,);required-after:gasstation@[0.5.0,);after:antiidconflict")
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
        patchManager.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        patchManager.init();
        if (GeneralConfig.extendBiome) {
            ChunkDataRegistry.registerDataManager(new BiomeManager());
        }
        if (GeneralConfig.extendBlockItem) {
            ChunkDataRegistry.registerDataManager(new BlockIDManager());
            ChunkDataRegistry.registerDataManager(new BlockMetaManager());
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        patchManager.postInit();
        if (GeneralConfig.extendBiome) {
            ChunkDataRegistry.disableDataManager("minecraft", "biome");
        }
        if (GeneralConfig.extendBlockItem) {
            ChunkDataRegistry.disableDataManager("minecraft", "blockid");
            ChunkDataRegistry.disableDataManager("minecraft", "metadata");
        }
    }
}
