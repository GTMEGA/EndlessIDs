package com.falsepattern.endlessids.mixin.mixins.common;

import net.minecraft.stats.StatList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(StatList.class)
public abstract class StatListMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = 4096),
                    require = 1)
    private static int modifyMineBlockStatArraySize(int constant) {
        return 32768;
    }
}
