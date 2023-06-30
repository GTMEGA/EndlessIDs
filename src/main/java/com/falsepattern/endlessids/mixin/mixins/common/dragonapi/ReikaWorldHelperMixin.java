package com.falsepattern.endlessids.mixin.mixins.common.dragonapi;

import Reika.DragonAPI.Libraries.World.ReikaWorldHelper;
import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

@Mixin(value = ReikaWorldHelper.class,
       remap = false)
public abstract class ReikaWorldHelperMixin {
    @Redirect(method = {"setBiomeForXZ", "setBiomeAndBlocksForXZ"},
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              require = 2)
    private static byte[] getFake(Chunk instance) {
        return EndlessIDs.FAKE_BIOME_ARRAY_PLACEHOLDER;
    }

    @Redirect(method = {"setBiomeForXZ", "setBiomeAndBlocksForXZ"},
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;setBiomeArray([B)V",
                       remap = true),
              require = 2)
    private static void noSet(Chunk instance, byte[] arr) {

    }

    @Inject(method = "setBiomeForXZ",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/chunk/Chunk;setChunkModified()V",
                     remap = true),
            locals = LocalCapture.CAPTURE_FAILHARD,
            require = 1)
    private static void customLogicSetBiome(World world, int x, int z, BiomeGenBase biome, boolean applyEnvironment, CallbackInfo ci, Chunk ch, int ax, int az, byte[] biomes, int index) {
        ((IChunkMixin)ch).getBiomeShortArray()[index] = (short) biome.biomeID;
    }

    private static int fromBiomeID;

    @Inject(method = "setBiomeAndBlocksForXZ",
            at = @At(value = "FIELD",
                     target = "Lnet/minecraft/world/biome/BiomeGenBase;biomeList:[Lnet/minecraft/world/biome/BiomeGenBase;",
                     remap = true),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            require = 1)
    private static void captureIndexSetBiomeAndBlocks(World world, int x, int z, BiomeGenBase biome, boolean applyEnvironment, CallbackInfo ci, Chunk ch, int ax, int az, byte[] biomes, int index) {
        fromBiomeID = ((IChunkMixin)ch).getBiomeShortArray()[index];
    }

    @ModifyVariable(method = "setBiomeAndBlocksForXZ",
                    at = @At("STORE"),
                    ordinal = 1)
    private static BiomeGenBase changeBiomeFrom(BiomeGenBase value) {
        return BiomeGenBase.getBiome(fromBiomeID);
    }

    @Inject(method = "setBiomeAndBlocksForXZ",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/chunk/Chunk;setBiomeArray([B)V",
                     remap = true),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            require = 1)
    private static void customLogicSetBiomeAndBlocks(World world, int x, int z, BiomeGenBase biome, boolean applyEnvironment, CallbackInfo ci, Chunk ch, int ax, int az, byte[] biomes, int index, BiomeGenBase from) {
        ((IChunkMixin)ch).getBiomeShortArray()[index] = (short) biome.biomeID;
    }
}
