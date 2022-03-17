package com.falsepattern.endlessids.mixin.mixins.common;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(World.class)
public abstract class WorldMixin {
    @ModifyConstant(method = "func_147480_a",
                    constant = @Constant(intValue = 12),
                    require = 1)
    private int modifySoundShift(int original) {
        return 16;
    }
}
