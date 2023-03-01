package com.falsepattern.endlessids.mixin.mixins.common.blockitem.vanilla;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.network.play.server.S26PacketMapChunkBulk;

@Mixin(S26PacketMapChunkBulk.class)
public abstract class S26PacketMapChunkBulkMixin {
    @ModifyConstant(method = "readPacketData",
                    constant = @Constant(intValue = VanillaConstants.bytesPerIDPlusMetadata * 16 * 16 * 16),
                    require = 1)
    private int extend1(int constant) {
        return ExtendedConstants.bytesPerIDPlusMetadata * 16 * 16 * 16;
    }
}
