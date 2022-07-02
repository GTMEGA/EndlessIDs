package com.falsepattern.endlessids.mixin.mixins.common.vanilla.biome;

import com.falsepattern.endlessids.PlaceholderBiome;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(BiomeGenBase.class)
public abstract class BiomeGenBaseMixin {
    @Shadow
    @Final
    private static BiomeGenBase[] biomeList;

    @Shadow
    @Final
    public int biomeID;

    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 1)
    private static int extendBiomeList(int constant) {
        return ExtendedConstants.biomeIDCount;
    }

    @Inject(method = "<init>(IZ)V",
            at = @At(value = "RETURN"),
            require = 1)
    private void generatePlaceholders(int p_i1971_1_, boolean register, CallbackInfo ci) {
        if (((Object) this) instanceof PlaceholderBiome) {
            return;
        }
        if (biomeID >= 128 && biomeList[biomeID - 128] == null) {
            new PlaceholderBiome(biomeID - 128, (BiomeGenBase) (Object) this);
        }
        if (biomeID <= ExtendedConstants.biomeIDCount - 129 && biomeList[biomeID + 128] == null) {
            new PlaceholderBiome(biomeID + 128, (BiomeGenBase) (Object) this);
        }
    }
}
