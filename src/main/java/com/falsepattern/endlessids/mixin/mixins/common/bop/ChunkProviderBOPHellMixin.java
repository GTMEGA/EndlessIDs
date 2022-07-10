package com.falsepattern.endlessids.mixin.mixins.common.bop;

import biomesoplenty.common.world.ChunkProviderBOPHell;
import com.falsepattern.endlessids.mixin.helpers.BiomePatchHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

@Mixin(value = ChunkProviderBOPHell.class,
       remap = false)
public abstract class ChunkProviderBOPHellMixin {
    private BiomeGenBase[] bgb;

    @Inject(method = "provideChunk",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                     shift = At.Shift.BEFORE,
                     remap = true),
            locals = LocalCapture.CAPTURE_FAILHARD,
            remap = true,
            require = 1)
    private void setBiomesTweaked1(int par1, int par2, CallbackInfoReturnable<Chunk> cir, byte[] abyte, Block[] blocks, Chunk chunk, BiomeGenBase[] abiomegenbase) {
        bgb = abiomegenbase;

    }

    @Redirect(method = "provideChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              remap = true,
              require = 1)
    private byte[] setBiomesTweaked2(Chunk chunk) {
        try {
            return BiomePatchHelper.getBiomeArrayTweaked(chunk, bgb);
        } finally {
            bgb = null;
        }
    }
}
