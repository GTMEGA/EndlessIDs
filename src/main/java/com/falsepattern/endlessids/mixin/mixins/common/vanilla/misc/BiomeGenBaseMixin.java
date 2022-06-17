package com.falsepattern.endlessids.mixin.mixins.common.vanilla.misc;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import net.minecraft.world.biome.BiomeGenBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BiomeGenBase.class)
public abstract class BiomeGenBaseMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount))
    private static int extendBiomeList(int constant) {
        return ExtendedConstants.biomeIDCount;
    }
}
