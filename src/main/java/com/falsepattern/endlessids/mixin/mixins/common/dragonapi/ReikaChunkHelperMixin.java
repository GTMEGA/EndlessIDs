package com.falsepattern.endlessids.mixin.mixins.common.dragonapi;

import Reika.DragonAPI.Libraries.World.ReikaChunkHelper;
import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.chunk.Chunk;

@Mixin(value = ReikaChunkHelper.class,
       remap = false)
public abstract class ReikaChunkHelperMixin {
    private static short[] biomeArray;
    @Redirect(method = "copyChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;setBiomeArray([B)V",
                       remap = true),
              require = 1)
    private static void customSet(Chunk chunk, byte[] p_76616_1_) {
        ((IChunkMixin)chunk).setBiomeShortArray(biomeArray);
        biomeArray = null;
    }

    @Redirect(method = "copyChunk",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              require = 1)
    private static byte[] customGet(Chunk chunk) {
        biomeArray = ((IChunkMixin)chunk).getBiomeShortArray();
        return EndlessIDs.ZERO_LENGTH_BIOME_ARRAY_PLACEHOLDER;
    }
}
