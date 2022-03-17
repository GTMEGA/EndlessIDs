package com.falsepattern.endlessids.mixin.mixins.common;

import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExtendedBlockStorage.class)
public abstract class ExtendedBlockStorageMixin implements IExtendedBlockStorageMixin {
    @Shadow private int blockRefCount;
    @Shadow private int tickRefCount;
    private short[] lsbArray;
    private byte[] msbArray;

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
        return new short[0];
    }

    @Override
    public byte[] getMSB() {
        return new byte[0];
    }

    @Inject(method = "<init>",
            at = @At(value = "RETURN"),
            require = 1)
    private void init(CallbackInfo ci) {
        lsbArray = new short[16 * 16 * 16];
        msbArray = new byte[16 * 16 * 16];
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
        lsbArray[index] = (short)lsb;
        msbArray[index] = (byte)msb;
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
        Hooks.setBlockId(this, x, y, z, blockID);
    }

    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    public void removeInvalidBlocks() {
        Hooks.removeInvalidBlocksHook(this);
    }
}
