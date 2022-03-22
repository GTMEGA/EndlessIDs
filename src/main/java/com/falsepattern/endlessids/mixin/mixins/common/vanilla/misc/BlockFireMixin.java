package com.falsepattern.endlessids.mixin.mixins.common.vanilla.misc;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import net.minecraft.block.BlockFire;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlockFire.class)
public abstract class BlockFireMixin {
    @ModifyConstant(method = {"<init>", "rebuildFireInfo", "getFlammability", "getEncouragement"},
                    constant = @Constant(intValue = VanillaConstants.blockIDCount),
                    remap = false,
                    require = 1)
    private static int extendIDs(int constant) {
        return ExtendedConstants.blockIDCount;
    }
}
