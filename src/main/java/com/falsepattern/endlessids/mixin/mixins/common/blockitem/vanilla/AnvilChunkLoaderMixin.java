package com.falsepattern.endlessids.mixin.mixins.common.blockitem.vanilla;

import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import net.minecraft.nbt.NBTTagCompound;
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
    private void redirectBlockDataWrite(NBTTagCompound instance, String key, byte[] value) {
        Hooks.writeBlockDataToNBT(instance, ebs);
        ebs = null;
    }

    @Redirect(method = "writeChunkToNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockMSBArray()Lnet/minecraft/world/chunk/NibbleArray;"),
              require = 1)
    private NibbleArray suppressVanillaBlockDataWrite(ExtendedBlockStorage instance) {
        return null;
    }

    @Redirect(method = "writeChunkToNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getMetadataArray()Lnet/minecraft/world/chunk/NibbleArray;"),
              require = 1)
    private NibbleArray grabEBSBlockMetaWrite(ExtendedBlockStorage instance) {
        ebs = (IExtendedBlockStorageMixin) instance;
        return null;
    }

    @Redirect(method = "writeChunkToNBT",
              at = @At(value = "FIELD",
                       target = "Lnet/minecraft/world/chunk/NibbleArray;data:[B",
                       ordinal = 0),
              slice = @Slice(from = @At(value = "INVOKE",
                                        target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getMetadataArray()Lnet/minecraft/world/chunk/NibbleArray;")),
              require = 1)
    private byte[] suppressEBSBlockMetaNibbleArrayAccess(NibbleArray instance) {
        return null;
    }

    @Redirect(method = "writeChunkToNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/nbt/NBTTagCompound;setByteArray(Ljava/lang/String;[B)V",
                       ordinal = 0),
              slice = @Slice(from = @At(value = "CONSTANT",
                                        args = "stringValue=Data")),
              require = 1)
    private void redirectBlockMetaWrite(NBTTagCompound instance, String p_74773_1_, byte[] p_74773_2_) {
        Hooks.writeBlockMetaToNBT(ebs, instance);
        ebs = null;
    }

    @Redirect(method = "readChunkFromNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/nbt/NBTTagCompound;getByteArray(Ljava/lang/String;)[B",
                       ordinal = 0),
              slice = @Slice(from = @At(value = "CONSTANT",
                                        args = "stringValue=Blocks")),
              require = 1)
    private byte[] grabNBTBlockDataRead(NBTTagCompound instance, String key) {
        nbt = instance;
        return null;
    }

    @Redirect(method = "readChunkFromNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;setBlockLSBArray([B)V"),
              require = 1)
    private void redirectBlockDataRead(ExtendedBlockStorage instance, byte[] p_76664_1_) {
        Hooks.readBlockDataFromNBT((IExtendedBlockStorageMixin) instance, nbt);
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
    private boolean suppressVanillaBlockDataRead(NBTTagCompound instance, String key, int type) {
        return false;
    }

    @Redirect(method = "readChunkFromNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/nbt/NBTTagCompound;getByteArray(Ljava/lang/String;)[B",
                       ordinal = 0),
              slice = @Slice(from = @At(value = "CONSTANT",
                                        args = "stringValue=Data")),
              require = 1)
    private byte[] grabNBTBlockMetaRead(NBTTagCompound instance, String key) {
        nbt = instance;
        return null;
    }

    @Redirect(method = "readChunkFromNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;setBlockMetadataArray(Lnet/minecraft/world/chunk/NibbleArray;)V"),
              require = 1)
    private void redirectBlockMetaRead(ExtendedBlockStorage instance, NibbleArray p_76668_1_) {
        Hooks.readBlockMetaFromNBT((IExtendedBlockStorageMixin)instance, nbt);
        nbt = null;
    }
}
