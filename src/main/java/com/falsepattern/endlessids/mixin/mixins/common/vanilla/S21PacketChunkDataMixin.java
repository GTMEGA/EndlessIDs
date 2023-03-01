package com.falsepattern.endlessids.mixin.mixins.common.vanilla;

import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

@Mixin(S21PacketChunkData.class)
public abstract class S21PacketChunkDataMixin {
    @ModifyConstant(method = {"<clinit>", "func_149275_c"},
                    constant = @Constant(intValue = VanillaConstants.bytesPerChunk),
                    require = 1)
    private static int increasePacketSize(int constant) {
        return ExtendedConstants.bytesPerChunk;
    }
}
