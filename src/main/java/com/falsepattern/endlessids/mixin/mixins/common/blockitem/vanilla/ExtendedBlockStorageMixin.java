package com.falsepattern.endlessids.mixin.mixins.common.blockitem.vanilla;

import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import lombok.val;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

@Mixin(ExtendedBlockStorage.class)
public abstract class ExtendedBlockStorageMixin implements IExtendedBlockStorageMixin {
    @Shadow
    private int blockRefCount;
    @Shadow
    private int tickRefCount;
    @Shadow private byte[] blockLSBArray;
    @Shadow private NibbleArray blockMetadataArray;
    private short[] lsbArray;
    private byte[] msbArray;
    private short[] metaArray;

    @Override
    public void setBlockRefCount(int value) {
        blockRefCount = value;
    }

    @Override
    public void setTickRefCount(int value) {
        tickRefCount = value;
    }

    @Override
    public short[] getLSB() {
        return lsbArray;
    }

    @Override
    public byte[] getMSB() {
        return msbArray;
    }

    @Override
    public short[] getMetaArray() {
        return metaArray;
    }

    @Inject(method = "<init>",
            at = @At(value = "RETURN"),
            require = 1)
    private void init(CallbackInfo ci) {
        blockLSBArray = null;
        blockMetadataArray = null;
        lsbArray = new short[16 * 16 * 16];
        msbArray = new byte[16 * 16 * 16];
        metaArray = new short[16 * 16 * 16];
    }

    private int getID(int x, int y, int z) {
        int index = y << 8 | z << 4 | x;
        int lsb = lsbArray[index] & 0xFFFF;
        int msb = (msbArray[index] & 0xFF) << 16;
        return lsb | msb;
    }

    private void setID(int x, int y, int z, int id) {
        int index = y << 8 | z << 4 | x;
        int lsb = id & 0xFFFF;
        int msb = (id >>> 16) & 0xFF;
        lsbArray[index] = (short) lsb;
        msbArray[index] = (byte) msb;
    }

    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    public Block getBlockByExtId(int x, int y, int z) {
        return Block.getBlockById(getID(x, y, z));
    }

    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    public void func_150818_a(int x, int y, int z, Block newBlock) {
        Block oldBlock = this.getBlockByExtId(x, y, z);
        if (oldBlock != Blocks.air) {
            --this.blockRefCount;
            if (oldBlock.getTickRandomly()) {
                --this.tickRefCount;
            }
        }

        if (newBlock != Blocks.air) {
            ++this.blockRefCount;
            if (newBlock.getTickRandomly()) {
                ++this.tickRefCount;
            }
        }

        int blockID = Hooks.getIdFromBlockWithCheck(newBlock, oldBlock);
        setID(x, y, z, blockID);
    }

    /**
     * @author FalsePattern
     * @reason meta ID extension
     */
    @Overwrite
    public int getExtBlockMetadata(int x, int y, int z) {
        int index = y << 8 | z << 4 | x;
        return metaArray[index] & 0xFFFF;
    }

    /**
     * @author FalsePattern
     * @reason meta ID extension
     */
    @Overwrite
    public void setExtBlockMetadata(int x, int y, int z, int meta) {
        int index = y << 8 | z << 4 | x;
        metaArray[index] = (short)(meta & 0xFFFF);
    }

    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    public void removeInvalidBlocks() {
        Hooks.removeInvalidBlocksHook(this);
    }

    private UnsupportedOperationException emergencyCrash() {
        val crashMSG = "A mod that is incompatible with " + Tags.MODNAME + " has tried to access the block array of a" +
                       " chunk like in vanilla! Crashing in fear of potential world corruption!\n" +
                       "Please report this issue on https://github.com/FalsePattern/EndlessIDs ASAP!";
        EndlessIDs.LOG.fatal(crashMSG);
        return new UnsupportedOperationException(crashMSG);
    }

    @Inject(method = {"getBlockMSBArray", "createBlockMSBArray"},
            at = @At("HEAD"))
    private void crashMSBArray(CallbackInfoReturnable<NibbleArray> cir) {
        throw emergencyCrash();
    }

    @Inject(method = "clearMSBArray",
            at = @At("HEAD"))
    private void crashMSBArray(CallbackInfo ci) {
        throw emergencyCrash();
    }

    @Inject(method = "setBlockMSBArray",
            at = @At("HEAD"))
    private void crashMSBArray(NibbleArray p_76673_1_, CallbackInfo ci) {
        throw emergencyCrash();
    }

    @Inject(method = "setBlockLSBArray",
            at = @At("HEAD"))
    private void crashLSBArray(byte[] p_76664_1_, CallbackInfo ci) {
        throw emergencyCrash();
    }

    @Inject(method = "getBlockLSBArray",
            at = @At("HEAD"))
    private void crashLSBArray(CallbackInfoReturnable<byte[]> cir) {
        throw emergencyCrash();
    }

    @Inject(method = "setBlockMetadataArray",
            at = @At("HEAD"))
    private void crashMetadataArray(NibbleArray p_76668_1_, CallbackInfo ci) {
        throw emergencyCrash();
    }

    @Inject(method = "getMetadataArray",
            at = @At("HEAD"))
    private void crashMetadataArray(CallbackInfoReturnable<NibbleArray> cir) {
        throw emergencyCrash();
    }
}
