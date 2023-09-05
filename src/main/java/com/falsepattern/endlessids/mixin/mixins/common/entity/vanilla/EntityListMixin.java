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
    @Shadow
    public static void addMapping(Class p_75618_0_, String p_75618_1_, int p_75618_2_) {
    }

    @Shadow
    public static void addMapping(Class p_75614_0_, String p_75614_1_, int p_75614_2_, int p_75614_3_, int p_75614_4_) {

    }

    @ModifyConstant(method = "addMapping(Ljava/lang/Class;Ljava/lang/String;I)V",
                    constant = @Constant(intValue = VanillaConstants.maxEntityID),
                    require = 1)
    private static int extendRange(int constant) {
        return ExtendedConstants.maxEntityID;
    }

    @Redirect(method = "<clinit>",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/entity/EntityList;addMapping(Ljava/lang/Class;Ljava/lang/String;I)V"),
              require = 1)
    private static void messUpMappings1(Class p_75618_0_, String p_75618_1_, int p_75618_2_) {
        addMapping(p_75618_0_, p_75618_1_, p_75618_2_ * 100);
    }


    @Redirect(method = "<clinit>",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/entity/EntityList;addMapping(Ljava/lang/Class;Ljava/lang/String;III)V"),
              require = 1)
    private static void messUpMappings2(Class p_75614_0_, String p_75614_1_, int p_75614_2_, int p_75614_3_, int p_75614_4_) {
        addMapping(p_75614_0_, p_75614_1_, p_75614_2_ * 100, p_75614_3_, p_75614_4_);
    }
}
