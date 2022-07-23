package com.falsepattern.endlessids.mixin.mixins.common.extraplanets;

import com.mjr.extraplanets.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

@Mixin(value = Config.class,
       remap = false)
public abstract class ConfigMixin {
    @Redirect(method = "load",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraftforge/common/config/Configuration;get(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)Lnet/minecraftforge/common/config/Property;"))
    private static Property shiftBiomeIDsUp(Configuration config, String category, String key, int defaultValue, String comment) {
        if (category.equals("biomeID")) {
            defaultValue += 17000;
            comment = "[range: 0 ~ 65535, default: " + defaultValue + "]";
        }
        return config.get(category, key, defaultValue, comment);
    }
}
