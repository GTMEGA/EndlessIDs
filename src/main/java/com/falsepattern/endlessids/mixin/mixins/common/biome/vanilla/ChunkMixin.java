package com.falsepattern.endlessids.mixin.mixins.common.biome.vanilla;

import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import lombok.val;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;

import java.util.Arrays;

@Mixin(value = Chunk.class,
       priority = 900)
public abstract class ChunkMixin implements ChunkBiomeHook {
    @Shadow
    @Final
    public int xPosition;
    @Shadow
    @Final
    public int zPosition;
    @Shadow
    public World worldObj;
    @Shadow
    private byte[] blockBiomeArray;
    @Unique
    private short[] eid$blockBiomeShortArray;

    @Inject(method = "<init>(Lnet/minecraft/world/World;II)V",
            at = @At(value = "RETURN"),
            require = 1)
    private void initShortArray(World p_i1995_1_, int p_i1995_2_, int p_i1995_3_, CallbackInfo ci) {
        blockBiomeArray = null;
        eid$blockBiomeShortArray = new short[16 * 16];
        Arrays.fill(eid$blockBiomeShortArray, (short) -1);
    }

    @Unique
    private void eid$emergencyCrash() {
        val crashMSG = "A mod that is incompatible with " + Tags.MODNAME + " has tried to access the biome array of a" +
                       " chunk like in vanilla! Crashing in fear of potential world corruption!\n" +
                       "Please report this issue on https://github.com/FalsePattern/EndlessIDs ASAP!";
        EndlessIDs.LOG.fatal(crashMSG);
        throw new UnsupportedOperationException(crashMSG);
    }

    @Inject(method = "setBiomeArray",
            at = @At(value = "HEAD"),
            require = 1)
    private void crashSetBiomeArray(byte[] p_76616_1_, CallbackInfo ci) {
        eid$emergencyCrash();
    }

    @Inject(method = "getBiomeArray",
            at = @At(value = "HEAD"),
            require = 1)
    private void crashGetBiomeArray(CallbackInfoReturnable<byte[]> cir) {
        eid$emergencyCrash();
    }

    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    public BiomeGenBase getBiomeGenForWorldCoords(int x, int z, WorldChunkManager manager) {
        int id = this.eid$blockBiomeShortArray[z << 4 | x] & ExtendedConstants.biomeIDMask;
        if (id == ExtendedConstants.biomeIDNull) {
            //Source: https://github.com/embeddedt/ArchaicFix/blob/3d1392f4db5a7221534a6f9b00c0c36a49d9be59/src/main/java/org/embeddedt/archaicfix/mixins/common/core/MixinChunk.java#L52
            if (this.worldObj.isRemote) {
                return BiomeGenBase.ocean;
            }
            BiomeGenBase gen = manager.getBiomeGenAt((this.xPosition << 4) + x, (this.zPosition << 4) + z);
            id = gen.biomeID;
            this.eid$blockBiomeShortArray[z << 4 | x] = (short) (id & ExtendedConstants.biomeIDMask);
        }

        return BiomeGenBase.getBiome(id) == null ? BiomeGenBase.plains : BiomeGenBase.getBiome(id);
    }

    @Override
    public short[] getBiomeShortArray() {
        return eid$blockBiomeShortArray;
    }

    @Override
    public void setBiomeShortArray(short[] data) {
        eid$blockBiomeShortArray = data;
    }
}
