package com.falsepattern.endlessids.mixin.mixins.common.blockitem.ubc;

import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import exterminatorJeff.undergroundBiomes.worldGen.BiomeUndergroundDecorator;
import exterminatorJeff.undergroundBiomes.worldGen.OreUBifier;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.util.logging.Logger;

@Mixin(value = BiomeUndergroundDecorator.class,
       remap = false)
public abstract class BiomeUndergroundDecoratorMixin {
    private static NibbleArray fakeArray;
    private IExtendedBlockStorageMixin ebs;

    @Redirect(method = {"replaceChunkOres(IILnet/minecraft/world/World;)V",
                        "replaceChunkOres(Lnet/minecraft/world/chunk/IChunkProvider;II)V"},
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLSBArray()[B",
                       remap = true),
              require = 2)
    private byte[] returnLSB(ExtendedBlockStorage instance) {
        IExtendedBlockStorageMixin iebs = (IExtendedBlockStorageMixin) instance;
        ebs = iebs;
        return iebs.getB1();
    }

    @Redirect(method = {"replaceChunkOres(IILnet/minecraft/world/World;)V",
                        "replaceChunkOres(Lnet/minecraft/world/chunk/IChunkProvider;II)V"},
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockMSBArray()Lnet/minecraft/world/chunk/NibbleArray;",
                       remap = true),
              require = 4)
    private NibbleArray returnFakeArray(ExtendedBlockStorage instance) {
        return fakeArray == null ? (fakeArray = new NibbleArray(4096, 4)) : fakeArray;
    }

    @Redirect(method = {"replaceChunkOres(IILnet/minecraft/world/World;)V",
                        "replaceChunkOres(Lnet/minecraft/world/chunk/IChunkProvider;II)V"},
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/NibbleArray;get(III)I",
                       remap = true),
              require = 2)
    private int returnRestOfID(NibbleArray instance, int x, int y, int z) {
        val id = ebs.getID(x, y, z) >>> 8;
        ebs = null;
        return id;
    }

    @Redirect(method = "correctBiomeDecorators",
              at = @At(value = "INVOKE",
                       target = "Ljava/util/logging/Logger;info(Ljava/lang/String;)V"),
              require = 2)
    private void noLog(Logger instance, String s) {

    }
}
