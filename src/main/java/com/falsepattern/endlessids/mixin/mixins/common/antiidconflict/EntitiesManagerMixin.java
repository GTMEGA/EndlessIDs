package com.falsepattern.endlessids.mixin.mixins.common.antiidconflict;

import code.elix_x.coremods.antiidconflict.managers.EntitiesManager;
import com.falsepattern.endlessids.mixin.helpers.AIDCStringFixer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = EntitiesManager.class,
       remap = false)
public abstract class EntitiesManagerMixin {

    @ModifyConstant(method = "*",
                    constant = {@Constant(stringValue = "\\entities"),
                                @Constant(stringValue = "\\main.cfg"),
                                @Constant(stringValue = "\\avaibleIDs.txt"),
                                @Constant(stringValue = "\\occupiedIDs.txt"),
                                @Constant(stringValue = "\\AllIDs.txt")})
    private static String fixPaths(String original) {
        return AIDCStringFixer.fixString(original);
    }
}
