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
                    constant = {@Constant(intValue = VanillaConstants.blockIDCount)},
                    require = 4)
    private static int modifyMineBlockStatArraySize(int constant) {
        return ExtendedConstants.blockIDCount;
    }

    @ModifyConstant(method = "<clinit>",
                    constant = {@Constant(intValue = VanillaConstants.itemIDCount)},
                    require = 3)
    private static int modifyItemStatArraysSize(int constant) {
        return ExtendedConstants.itemIDCount;
    }

}
