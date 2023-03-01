package com.falsepattern.endlessids.mixin.mixins.common.biome.vanilla;

import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.world.chunk.Chunk;

@Mixin(S21PacketChunkData.class)
public abstract class S21PacketChunkDataMixin {
    @Redirect(method = "func_149269_a",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B"),
              require = 1)
    private static byte[] getBiomesArrayShort(Chunk instance) {
        return Hooks.shortToByteArray(((IChunkMixin) instance).getBiomeShortArray());
    }
}
