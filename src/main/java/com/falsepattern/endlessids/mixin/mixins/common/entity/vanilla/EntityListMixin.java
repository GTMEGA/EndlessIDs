package com.falsepattern.endlessids.mixin.mixins.common.entity.vanilla;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EntityList;


@Mixin(EntityList.class)
public abstract class EntityListMixin {

    @ModifyConstant(method = "addMapping(Ljava/lang/Class;Ljava/lang/String;I)V",
                    constant = @Constant(intValue = VanillaConstants.maxEntityID),
                    require = 1)
    private static int extendRange(int constant) {
        return ExtendedConstants.maxEntityID;
    }
}
