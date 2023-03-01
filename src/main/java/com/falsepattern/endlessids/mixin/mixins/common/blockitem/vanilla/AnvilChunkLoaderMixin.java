package com.falsepattern.endlessids.mixin.mixins.common.blockitem.vanilla;

import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

@Mixin(AnvilChunkLoader.class)
public abstract class AnvilChunkLoaderMixin {

    //TODO not thread safe
    private IExtendedBlockStorageMixin ebs;

    private NBTTagCompound nbt;

    @Redirect(method = "writeChunkToNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLSBArray()[B"),
              require = 1)
    private byte[] grabEBSBlockDataWrite(ExtendedBlockStorage instance) {
        ebs = (IExtendedBlockStorageMixin) instance;
        return null;
    }

    @Redirect(method = "writeChunkToNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/nbt/NBTTagCompound;setByteArray(Ljava/lang/String;[B)V"),
              slice = @Slice(from = @At(value = "INVOKE",
                                        target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getYLocation()I"),
                             to = @At(value = "INVOKE",
                                      target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockMSBArray()Lnet/minecraft/world/chunk/NibbleArray;")),
              require = 1)
    private void redirectBlockDataWrite(NBTTagCompound instance, String p_74773_1_, byte[] p_74773_2_) {
        Hooks.writeChunkToNbt(instance, ebs);
        ebs = null;
    }

    @Redirect(method = "writeChunkToNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockMSBArray()Lnet/minecraft/world/chunk/NibbleArray;"),
              require = 1)
    private NibbleArray suppressVanillaBlockDataWrite(ExtendedBlockStorage instance) {
        return null;
    }

    @Redirect(method = "readChunkFromNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/nbt/NBTTagCompound;getByteArray(Ljava/lang/String;)[B",
                       ordinal = 0),
              slice = @Slice(from = @At(value = "CONSTANT",
                                        args = "stringValue=Blocks")),
              require = 1)
    private byte[] grabNBTBlockDataRead(NBTTagCompound instance, String p_74770_1_) {
        nbt = instance;
        return null;
    }

    @Redirect(method = "readChunkFromNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;setBlockLSBArray([B)V"),
              require = 1)
    private void redirectBlockDataRead(ExtendedBlockStorage instance, byte[] p_76664_1_) {
        Hooks.readChunkFromNbt((IExtendedBlockStorageMixin) instance, nbt);
        nbt = null;
    }

    @Redirect(method = "readChunkFromNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/nbt/NBTTagCompound;hasKey(Ljava/lang/String;I)Z",
                       ordinal = 0),
              slice = @Slice(from = @At(value = "CONSTANT",
                                        args = "stringValue=Add",
                                        ordinal = 0)),
              require = 1)
    private boolean suppressVanillaBlockDataRead(NBTTagCompound instance, String p_150297_1_, int p_150297_2_) {
        return false;
    }
}
