package com.falsepattern.endlessids.mixin.mixins.common.redstone;

import com.falsepattern.endlessids.config.GeneralConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.world.World;

@Mixin(World.class)
public abstract class WorldMixin {
    @ModifyConstant(method = {"getStrongestIndirectPower", "getBlockPowerInput"},
                    constant = @Constant(intValue = 15),
                    require = 8)
    private int bigRedstone(int constant) {
        return GeneralConfig.maxRedstone;
    }
}
