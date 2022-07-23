package com.falsepattern.endlessids.mixin.mixins.common.darkworld;

import com.falsepattern.endlessids.config.DarkWorldIDConfig;
import matthbo.mods.darkworld.init.ModBiomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = ModBiomes.class,
       remap = false)
public abstract class ModBiomesMixin {
    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 100),
                    require = 1)
    private static int darkOceanID(int id) {
        return DarkWorldIDConfig.darkOceanID;
    }

    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 101),
                    require = 1)
    private static int darkPlainsID(int id) {
        return DarkWorldIDConfig.darkPlainsID;
    }

    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 102),
                    require = 1)
    private static int darkDesertID(int id) {
        return DarkWorldIDConfig.darkDesertID;
    }

    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 103),
                    require = 1)
    private static int darkHillsID(int id) {
        return DarkWorldIDConfig.darkHillsID;
    }

    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 104),
                    require = 1)
    private static int darkForestID(int id) {
        return DarkWorldIDConfig.darkForestID;
    }

    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 105),
                    require = 1)
    private static int darkTaigaID(int id) {
        return DarkWorldIDConfig.darkTaigaID;
    }

    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 106),
                    require = 1)
    private static int darkSwampID(int id) {
        return DarkWorldIDConfig.darkSwampID;
    }
}
