package com.falsepattern.endlessids.mixin.mixins.common;

import net.minecraft.block.Block;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.DataOutputStream;
import java.io.IOException;

@Mixin(S22PacketMultiBlockChange.class)
public abstract class S22PacketMultiBlockChangeMixin {
    private int id;
    private int meta;
    @Redirect(method = "<init>(I[SLnet/minecraft/world/chunk/Chunk;)V",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBlock(III)Lnet/minecraft/block/Block;"),
              require = 1)
    private Block extractBlockInfo(Chunk instance, int x, int y, int z) {
        Block block = instance.getBlock(x, y, z);
        id = Block.getIdFromBlock(block);
        meta = instance.getBlockMetadata(x, y, z);
        return block;
    }

    @Redirect(method = "<init>(I[SLnet/minecraft/world/chunk/Chunk;)V",
              at = @At(value = "INVOKE",
                       target = "Ljava/io/DataOutputStream;writeShort(I)V",
                       ordinal = 1),
              require = 1)
    private void hackWrite(DataOutputStream instance, int value) throws IOException {
        instance.writeShort(id);
        instance.writeByte(meta);
    }
}
