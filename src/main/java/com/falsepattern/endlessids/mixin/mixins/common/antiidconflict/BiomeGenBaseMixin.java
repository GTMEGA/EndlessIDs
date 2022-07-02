package com.falsepattern.endlessids.mixin.mixins.common.antiidconflict;

import com.falsepattern.endlessids.EndlessIDs;
import lombok.val;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import sun.reflect.CallerSensitive;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(BiomeGenBase.class)
public abstract class BiomeGenBaseMixin {
    @Shadow @Final private static BiomeGenBase[] biomeList;

    @CallerSensitive
    @Redirect(method = "getBiomeGenArray",
              at = @At(value = "FIELD",
                       target = "Lnet/minecraft/world/biome/BiomeGenBase;biomeList:[Lnet/minecraft/world/biome/BiomeGenBase;",
                       opcode = Opcodes.GETSTATIC),
              require = 1)
    private static BiomeGenBase[] returnFakeArray() {
        if (!EndlessIDs.postInitFinished) {
            val trace = Thread.currentThread().getStackTrace()[3];
            if (trace.getMethodName().equals("updateBiomesFolder") &&
                trace.getClassName().equals("code.elix_x.coremods.antiidconflict.managers.BiomesManager")) {
                return EndlessIDs.fakeBiomeArray;
            }
        }
        return biomeList;
    }
}
