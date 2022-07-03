package com.falsepattern.endlessids.mixin.mixins.common.icg;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import net.tclproject.mysteriumlib.asm.fixes.MysteriumPatchesFixesCave;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = MysteriumPatchesFixesCave.class,
       remap = false)
public abstract class MysteriumPatchesFixesCaveMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 1)
    private static int extendIDs(int constant) {
        return ExtendedConstants.biomeIDCount;
    }
}
