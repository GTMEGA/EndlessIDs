package com.falsepattern.endlessids;

import com.falsepattern.endlessids.asm.IETransformer;
import com.falsepattern.lib.config.ConfigException;
import com.falsepattern.lib.config.ConfigurationManager;
import lombok.SneakyThrows;
import lombok.val;

import net.minecraft.launchwrapper.LaunchClassLoader;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;
import java.util.Set;

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

    @SneakyThrows
    public String[] getASMTransformerClass() {
        val cl = ((LaunchClassLoader) this.getClass().getClassLoader());
        val field = cl.getClass().getDeclaredField("transformerExceptions");
        field.setAccessible(true);
        val exceptions = (Set<String>) field.get(cl);
        if (exceptions.contains("code.elix_x.coremods")) {
            IETransformer.logger.info("AntiIDConflict detected!");
            IETransformer.logger.info("Removing ASM protections so that we can fix its code.");
            exceptions.remove("code.elix_x.coremods");
            exceptions.add("code.elix_x.coremods.antiidconflict.core");
            exceptions.add("code.elix_x.coremods.antiidconflict.ByteCodeTester");
        }
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
