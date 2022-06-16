package com.falsepattern.endlessids.mixin.mixins.common.vanilla.misc;

import com.falsepattern.endlessids.constants.biome.ExtendedConstants;
import com.falsepattern.endlessids.constants.biome.VanillaConstants;
import net.minecraft.world.biome.BiomeGenBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BiomeGenBase.class)
public abstract class BiomeGenBaseMixin {
    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = VanillaConstants.BIOME_ID_COUNT))
    private static int extendBiomeList(int constant) {
        return ExtendedConstants.BIOME_ID_COUNT;
    }
}
