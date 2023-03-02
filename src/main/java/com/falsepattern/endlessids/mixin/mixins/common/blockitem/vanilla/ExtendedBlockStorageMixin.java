package com.falsepattern.endlessids.mixin.mixins.common.blockitem.vanilla;

import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.endlessids.mixin.helpers.IExtendedBlockStorageMixin;
import lombok.val;
import lombok.var;
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

    @Shadow private NibbleArray blockMSBArray;
    private NibbleArray b2High;
    private byte[] b3;
    private short[] metaArray;

    @Override
    public short[] getMetaArray() {
        return metaArray;
    }

    @Inject(method = "<init>",
            at = @At(value = "RETURN"),
            require = 1)
    private void init(CallbackInfo ci) {
        blockMetadataArray = null;
        metaArray = new short[16 * 16 * 16];
    }

    public int getID(int x, int y, int z) {
        int index = y << 8 | z << 4 | x;
        int id = blockLSBArray[index] & 0xFF;
        if (blockMSBArray != null) {
            id |= blockMSBArray.get(x, y, z) << 8;
            if (b2High != null) {
                id |= b2High.get(x, y, z) << 12;
                if (b3 != null) {
                    id |= (b3[index] & 0xFF) << 16;
                }
            }
        }
        return id;
    }

    private void setID(int x, int y, int z, int id) {
        int index = y << 8 | z << 4 | x;
        blockLSBArray[index] = (byte) (id & 0xFF);
        if (id > 0xFF) {
            if (blockMSBArray == null) {
                blockMSBArray = new NibbleArray(blockLSBArray.length, 4);
            }
            if (id > 0xFFF) {
                if (b2High == null) {
                    b2High = new NibbleArray(blockLSBArray.length, 4);
                }
                if (id > 0xFFFF && b3 == null) {
                    b3 = new byte[blockLSBArray.length];
                }
            }
        }
        if (blockMSBArray != null) {
            blockMSBArray.set(x, y, z, (id >>> 8) & 0xF);
            if (b2High != null) {
                b2High.set(x, y, z, (id >>> 12) & 0xF);
                if (b3 != null) {
                    b3[index] = (byte) ((id >>> 16) & 0xFF);
                }
            }
        }
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

    @Redirect(method = "removeInvalidBlocks",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockByExtId(III)Lnet/minecraft/block/Block;"),
              require = 1)
    private Block removeInvalidBlocks(ExtendedBlockStorage instance, int x, int y, int z) {
        var block = instance.getBlockByExtId(x, y, z);
        if (block == null && GeneralConfig.removeInvalidBlocks) {
            instance.func_150818_a(x, y, z, Blocks.air);
            block = Blocks.air;
        }
        return block;
    }

    private UnsupportedOperationException emergencyCrash() {
        val crashMSG = "A mod that is incompatible with " + Tags.MODNAME + " has tried to access the block array of a" +
                       " chunk like in vanilla! Crashing in fear of potential world corruption!\n" +
                       "Please report this issue on https://github.com/FalsePattern/EndlessIDs ASAP!";
        EndlessIDs.LOG.fatal(crashMSG);
        return new UnsupportedOperationException(crashMSG);
    }

    @Override
    public byte[] getB1() {
        return blockLSBArray;
    }

    @Override
    public void setB1(byte[] data) {
        blockLSBArray = data;
    }

    @Override
    public NibbleArray getB2Low() {
        return blockMSBArray;
    }

    @Override
    public void clearB2Low() {
        blockMSBArray = null;
    }

    @Override
    public void setB2Low(NibbleArray data) {
        blockMSBArray = data;
    }

    @Override
    public NibbleArray createB2Low() {
        return (blockMSBArray = new NibbleArray(blockLSBArray.length, 4));
    }

    @Override
    public NibbleArray getB2High() {
        return b2High;
    }

    @Override
    public void setB2High(NibbleArray data) {
        b2High = data;
    }

    @Override
    public void clearB2High() {
        b2High = null;
    }

    @Override
    public NibbleArray createB2High() {
        return (b2High = new NibbleArray(blockLSBArray.length, 4));
    }

    @Override
    public byte[] getB3() {
        return b3;
    }

    @Override
    public void setB3(byte[] data) {
        b3 = data;
    }

    @Override
    public void clearB3() {
        b3 = null;
    }

    @Override
    public byte[] createB3() {
        return b3 = new byte[blockLSBArray.length];
    }

    @Override
    public int getStorageFlag() {
        if (blockMSBArray == null) {
            return 0b00;
        }
        if (b2High == null) {
            return 0b01;
        }
        if (b3 == null) {
            return 0b10;
        }
        return 0b11;
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
