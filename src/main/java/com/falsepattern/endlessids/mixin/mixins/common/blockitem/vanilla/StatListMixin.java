package com.falsepattern.endlessids.mixin.mixins.common.blockitem.vanilla;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.stats.StatList;

@Mixin(StatList.class)
public abstract class StatListMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = {@Constant(intValue = VanillaConstants.blockIDCount), @Constant(intValue = 32000)},
                    require = 4)
    private static int modifyMineBlockStatArraySize(int constant) {
        return ExtendedConstants.blockIDCount;
    }

}
