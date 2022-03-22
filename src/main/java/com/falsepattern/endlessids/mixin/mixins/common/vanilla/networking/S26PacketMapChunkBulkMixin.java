package com.falsepattern.endlessids.mixin.mixins.common.vanilla.networking;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(S26PacketMapChunkBulk.class)
public abstract class S26PacketMapChunkBulkMixin {
    //TODO magic number
    @ModifyConstant(method = "readPacketData",
                    constant = @Constant(intValue = VanillaConstants.bytesPerIDPlusMetadata * 0x1000),
                    require = 1)
    private int extend1(int constant) {
        return ExtendedConstants.bytesPerIDPlusMetadata * 0x1000;
    }
}
