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

    @Shadow
    private byte[] blockLSBArray;
    @Shadow
    private NibbleArray blockMSBArray;
    private NibbleArray b2High;
    private byte[] b3;

    @Shadow
    private NibbleArray blockMetadataArray;
    private NibbleArray m1High;
    private byte[] m2;

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
                createB2Low();
            }
            if (id > 0xFFF) {
                if (b2High == null) {
                    createB2High();
                }
                if (id > 0xFFFF && b3 == null) {
                    createB3();
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
        int meta = blockMetadataArray.get(x, y, z);
        if (m1High != null) {
            meta |= m1High.get(x, y, z) << 4;
            if (m2 != null) {
                meta |= (m2[(y << 8) | (z << 4) | x] & 0xFF) << 8;
            }
        }
        return meta;
    }

    /**
     * @author FalsePattern
     * @reason meta ID extension
     */
    @Overwrite
    public void setExtBlockMetadata(int x, int y, int z, int meta) {
        blockMetadataArray.set(x, y, z, meta & 0xF);
        if (meta > 0xF) {
            if (m1High == null) {
                createM1High();
            }
            if (meta > 0xFF && m2 == null) {
                createM2();
            }
        }
        if (m1High != null) {
            m1High.set(x, y, z, (meta >>> 4) & 0xF);
            if (m2 != null) {
                m2[(y << 8) | (z << 4) | x] = (byte) ((meta >>> 8) & 0xFF);
            }
        }
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
    public byte[] createB3() {
        return b3 = new byte[blockLSBArray.length];
    }

    @Override
    public NibbleArray getM1Low() {
        return blockMetadataArray;
    }

    @Override
    public void setM1Low(NibbleArray m1Low) {
        blockMetadataArray = m1Low;
    }

    @Override
    public NibbleArray getM1High() {
        return m1High;
    }

    @Override
    public void setM1High(NibbleArray m1High) {
        this.m1High = m1High;
    }

    @Override
    public NibbleArray createM1High() {
        return (m1High = new NibbleArray(blockLSBArray.length, 4));
    }

    @Override
    public byte[] getM2() {
        return m2;
    }

    @Override
    public void setM2(byte[] m2) {
        this.m2 = m2;
    }

    @Override
    public byte[] createM2() {
        return (m2 = new byte[blockLSBArray.length]);
    }

    @Override
    public int getEBSMSBMask() {
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

    @Override
    public int getEBSMask() {
        if (m1High == null) {
            return 0b01;
        }
        if (m2 == null) {
            return 0b10;
        }
        return 0b11;
    }

    @Inject(method = {"getBlockMSBArray", "createBlockMSBArray"},
            at = @At("HEAD"),
            expect = 0)
    private void crashMSBArray(CallbackInfoReturnable<NibbleArray> cir) {
        throw emergencyCrash();
    }

    @Inject(method = "clearMSBArray",
            at = @At("HEAD"),
            expect = 0)
    private void crashMSBArray(CallbackInfo ci) {
        throw emergencyCrash();
    }

    @Inject(method = "setBlockMSBArray",
            at = @At("HEAD"),
            expect = 0)
    private void crashMSBArray(NibbleArray p_76673_1_, CallbackInfo ci) {
        throw emergencyCrash();
    }

    @Inject(method = "setBlockLSBArray",
            at = @At("HEAD"),
            expect = 0)
    private void crashLSBArray(byte[] p_76664_1_, CallbackInfo ci) {
        throw emergencyCrash();
    }

    @Inject(method = "getBlockLSBArray",
            at = @At("HEAD"),
            expect = 0)
    private void crashLSBArray(CallbackInfoReturnable<byte[]> cir) {
        throw emergencyCrash();
    }

    @Inject(method = "setBlockMetadataArray",
            at = @At("HEAD"),
            expect = 0)
    private void crashMetadataArray(NibbleArray p_76668_1_, CallbackInfo ci) {
        throw emergencyCrash();
    }

    @Inject(method = "getMetadataArray",
            at = @At("HEAD"),
            expect = 0)
    private void crashMetadataArray(CallbackInfoReturnable<NibbleArray> cir) {
        throw emergencyCrash();
    }
}
