package com.falsepattern.endlessids.mixin.mixins.common.vanilla.misc;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.world.World;

@Mixin(World.class)
public abstract class WorldMixin {
    @ModifyConstant(method = "func_147480_a",
                    constant = @Constant(intValue = VanillaConstants.bitsPerID),
                    require = 1)
    private int modifySoundShift(int original) {
        return ExtendedConstants.bitsPerID;
    }
}
