package com.falsepattern.endlessids;

import java.io.File;
import java.util.Map;

import com.falsepattern.endlessids.asm.IETransformer;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions({ "com.falsepattern.endlessids.asm" })
public class IEPlugin implements IFMLLoadingPlugin
{
    public IEPlugin() {
        super();
    }
    
    public String[] getASMTransformerClass() {
        return new String[] { IETransformer.class.getName() };
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
        IEConfig.init((File) data.get("coremodLocation"));
        IETransformer.isObfuscated = (Boolean) data.get("runtimeDeobfuscationEnabled");
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
}
