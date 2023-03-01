package com.falsepattern.endlessids.mixin.mixins.common.biome.vanilla;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.network.play.server.S26PacketMapChunkBulk;

@Mixin(S26PacketMapChunkBulk.class)
public abstract class S26PacketMapChunkBulkMixin {

    @ModifyConstant(method = "readPacketData",
                    constant = @Constant(intValue = VanillaConstants.bytesPerBiome * 16 * 16),
                    require = 1)
    private int extend2(int contant) {
        return ExtendedConstants.bytesPerBiome * 16 * 16;
    }
}
