package com.falsepattern.endlessids.mixin.mixins.common;

import net.minecraft.block.BlockFire;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlockFire.class)
public abstract class BlockFireMixin {
    @ModifyConstant(method = {"<init>", "rebuildFireInfo", "getFlammability", "getEncouragement"},
                    constant = @Constant(intValue = 4096),
                    remap = false,
                    require = 1)
    private static int extendIDs(int constant) {
        return 32768;
    }
}
