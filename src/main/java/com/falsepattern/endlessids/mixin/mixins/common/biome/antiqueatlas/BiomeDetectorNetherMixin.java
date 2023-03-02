package com.falsepattern.endlessids.mixin.mixins.common.biome.antiqueatlas;

import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import com.falsepattern.endlessids.mixin.helpers.ShortUtil;
import hunternif.mc.atlas.core.BiomeDetectorNether;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.chunk.Chunk;

@Mixin(value = BiomeDetectorNether.class,
       remap = false)
public abstract class BiomeDetectorNetherMixin {
    private Chunk chunk;

    @Redirect(method = "getBiomeID",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B",
                       remap = true),
              require = 1)
    private byte[] disableGetter(Chunk instance) {
        chunk = instance;
        return null;
    }

    @Redirect(method = "getBiomeID",
              at = @At(value = "INVOKE",
                       target = "Lhunternif/mc/atlas/util/ByteUtil;unsignedByteToIntArray([B)[I"),
              require = 1)
    private int[] biomeIDs(byte[] i) {
        try {
            return ShortUtil.unsignedShortToIntArray(((IChunkMixin) chunk).getBiomeShortArray());
        } finally {
            chunk = null;
        }
    }
}
