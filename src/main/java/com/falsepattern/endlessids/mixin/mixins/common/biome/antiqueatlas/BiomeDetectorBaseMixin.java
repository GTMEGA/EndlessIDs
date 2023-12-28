package com.falsepattern.endlessids.mixin.mixins.common.biome.antiqueatlas;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import com.falsepattern.endlessids.mixin.helpers.ShortUtil;
import hunternif.mc.atlas.core.BiomeDetectorBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.chunk.Chunk;

@Mixin(value = BiomeDetectorBase.class,
       remap = false)
public abstract class BiomeDetectorBaseMixin {
    private Chunk chunk;

    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 2)
    private static int extendIDs(int constant) {
        return ExtendedConstants.biomeIDCount;
    }

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
            return ShortUtil.unsignedShortToIntArray(((ChunkBiomeHook) chunk).getBiomeShortArray());
        } finally {
            chunk = null;
        }
    }
}
