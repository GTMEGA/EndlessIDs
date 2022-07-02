package com.falsepattern.endlessids;

import com.falsepattern.endlessids.asm.IETransformer;
import com.falsepattern.lib.config.ConfigException;
import com.falsepattern.lib.config.ConfigurationManager;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

import static cpw.mods.fml.relauncher.IFMLLoadingPlugin.DependsOn;
import static cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import static cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import static cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@MCVersion("1.7.10")
@Name(Tags.MODID)
@TransformerExclusions({"com.falsepattern.endlessids.asm"})
@DependsOn("falsepatternlib")
public class IEPlugin implements IFMLLoadingPlugin {
    static {
        try {
            ConfigurationManager.registerConfig(IEConfig.class);
        } catch (ConfigException e) {
            System.err.println("Failed to load EndlessIDs config. Using defaults.");
        }
    }

    public IEPlugin() {
        super();
    }

    public String[] getASMTransformerClass() {
        return new String[]{IETransformer.class.getName()};
    }

    public String getModContainerClass() {
        return null;
    }

    public String getSetupClass() {
        return null;
    }

    public void injectData(final Map<String, Object> data) {
        IETransformer.isObfuscated = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }

    public String getAccessTransformerClass() {
        return null;
    }
}
