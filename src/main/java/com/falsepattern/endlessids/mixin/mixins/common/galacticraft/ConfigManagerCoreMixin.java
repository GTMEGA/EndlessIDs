package com.falsepattern.endlessids.mixin.mixins.common.galacticraft;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import micdoodle8.mods.galacticraft.core.util.ConfigManagerCore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = ConfigManagerCore.class,
       remap = false)
public abstract class ConfigManagerCoreMixin {
    @ModifyConstant(method = {"<clinit>", "syncConfig"},
                    constant = @Constant(intValue = 102),
                    require = 3)
    private static int shiftBiomeIDsUp(int id) {
        return id + 18000;
    }
    @ModifyConstant(method = "syncConfig",
                    constant = @Constant(stringValue = "Biome ID for Moon (Mars will be this + 1, Asteroids + 2 etc). Allowed range 40-250."),
                    require = 1)
    private static String modifyHint(String constant) {
        return "Biome ID for Moon (Mars will be this + 1, Asteroids + 2 etc). Allowed range 40-" +
               (ExtendedConstants.biomeIDCount - 6);
    }

    @ModifyConstant(method = "syncConfig",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount - 6),
                    require = 1)
    private static int modifyLimit(int constant) {
        return ExtendedConstants.biomeIDCount - 6;
    }
}
