package com.falsepattern.endlessids.mixin.mixins.common.vanilla.biome;

import com.falsepattern.endlessids.PlaceholderBiome;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(BiomeGenBase.class)
public abstract class BiomeGenBasePlaceholderMixin {
    @Shadow
    @Final
    private static BiomeGenBase[] biomeList;

    @Mutable
    @Shadow
    @Final
    public int biomeID;

    private static ThreadLocal<Boolean> wantsRegister;

    @Redirect(method = "<init>(IZ)V",
              at = @At(value = "FIELD",
                       opcode = Opcodes.PUTFIELD,
                       target = "Lnet/minecraft/world/biome/BiomeGenBase;biomeID:I"),
              require = 1)
    private void removePlaceholders(BiomeGenBase instance, int id) {
        if (wantsRegister.get()) {
            if (biomeList[id] instanceof PlaceholderBiome) {
                biomeList[id] = null;
            }
        }
        biomeID = id;
    }

    @ModifyVariable(method = "<init>(IZ)V",
                    at = @At(value = "HEAD"),
                    index = 2,
                    require = 1,
                    argsOnly = true)
    private static boolean modifyArg(boolean register) {
        if (wantsRegister == null) wantsRegister = new ThreadLocal<>();
        wantsRegister.set(register);
        return register;
    }
}
