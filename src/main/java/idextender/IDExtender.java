package idextender;

import com.falsepattern.endlessids.Tags;

import cpw.mods.fml.common.Mod;

@Mod(modid = "idextender", name = "ID Extender", version = "1.0.0")
public class IDExtender {

    public static String parentModId() {
        return Tags.MODID;
    }

}
