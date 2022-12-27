package com.falsepattern.endlessids.mixin.mixins.common.ubc;

import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import exterminatorJeff.undergroundBiomes.worldGen.BiomeUndergroundDecorator;
import exterminatorJeff.undergroundBiomes.worldGen.OreUBifier;
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
    private ThreadLocal<IExtendedBlockStorageMixin> ebs;

    @Redirect(method = {"replaceChunkOres(IILnet/minecraft/world/World;)V",
                        "replaceChunkOres(Lnet/minecraft/world/chunk/IChunkProvider;II)V"},
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLSBArray()[B",
                       remap = true),
              require = 2)
    private byte[] returnMSBasLSB(ExtendedBlockStorage instance) {
        if (ebs == null) {
            ebs = new ThreadLocal<>();
        }
        IExtendedBlockStorageMixin iebs = (IExtendedBlockStorageMixin) instance;
        ebs.set(iebs);
        return iebs.getMSB();
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
        return ebs.get().getLSB()[y << 8 | z << 4 | x] & 0xFFFF;
    }

    @Redirect(method = {"replaceChunkOres(IILnet/minecraft/world/World;)V",
                        "replaceChunkOres(Lnet/minecraft/world/chunk/IChunkProvider;II)V"},
              at = @At(value = "INVOKE",
                       target = "LexterminatorJeff/undergroundBiomes/worldGen/OreUBifier;blockReplacer(I)LexterminatorJeff/undergroundBiomes/worldGen/OreUBifier$BlockReplacer;"),
              require = 2)
    private OreUBifier.BlockReplacer reshuffleID(OreUBifier instance, int blockID) {
        blockID = ((blockID & 0xFF) << 16) | ((blockID & 0xFFFF00) >>> 8);
        return instance.blockReplacer(blockID);
    }

    @Redirect(method = "correctBiomeDecorators",
              at = @At(value = "INVOKE",
                       target = "Ljava/util/logging/Logger;info(Ljava/lang/String;)V"),
              require = 2)
    private void noLog(Logger instance, String s) {

    }
}
