package com.falsepattern.endlessids.mixin.mixins.common.biome.vanilla;

import com.falsepattern.endlessids.Hooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

@Mixin(AnvilChunkLoader.class)
public abstract class AnvilChunkLoaderMixin {

    //TODO not thread safe
    private Chunk chunk;

    private NBTTagCompound nbt;

    @Redirect(method = "writeChunkToNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBiomeArray()[B"),
              require = 1)
    private byte[] grabChunkBiomeDataWrite(Chunk instance) {
        chunk = instance;
        return null;
    }

    @Redirect(method = "writeChunkToNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/nbt/NBTTagCompound;setByteArray(Ljava/lang/String;[B)V"),
              slice = @Slice(from = @At(value = "CONSTANT",
                                        args = "stringValue=Sections"),
                             to = @At(value = "FIELD",
                                      target = "Lnet/minecraft/world/chunk/Chunk;hasEntities:Z",
                                      ordinal = 0)),
              require = 1)
    private void redirectBiomeDataWrite(NBTTagCompound instance, String p_74773_1_, byte[] p_74773_2_) {
        Hooks.writeChunkBiomeArrayToNbt(chunk, instance);
        chunk = null;
    }

    @Redirect(method = "readChunkFromNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/nbt/NBTTagCompound;hasKey(Ljava/lang/String;I)Z",
                       ordinal = 0),
              slice = @Slice(from = @At(value = "CONSTANT",
                                        args = "stringValue=Biomes",
                                        ordinal = 0)),
              require = 1)
    private boolean grabBiomeNBTRead(NBTTagCompound instance, String p_150297_1_, int p_150297_2_) {
        nbt = instance;
        return true;
    }

    @Redirect(method = "readChunkFromNBT",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;setBiomeArray([B)V"),
              require = 1)
    private void redirectBiomeDataRead(Chunk instance, byte[] p_76616_1_) {
        Hooks.readChunkBiomeArrayFromNbt(instance, nbt);
        nbt = null;
    }
}
