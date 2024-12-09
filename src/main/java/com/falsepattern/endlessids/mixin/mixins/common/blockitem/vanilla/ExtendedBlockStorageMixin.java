/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2024 FalsePattern
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.falsepattern.endlessids.mixin.mixins.common.blockitem.vanilla;

import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.endlessids.mixin.helpers.SubChunkBlockHook;
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

import static com.falsepattern.endlessids.constants.ExtendedConstants.blocksPerSubChunk;

@Mixin(ExtendedBlockStorage.class)
public abstract class ExtendedBlockStorageMixin implements SubChunkBlockHook {
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

    public int eid$getID(int x, int y, int z) {
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

    public void eid$setID(int x, int y, int z, int id) {
        int index = y << 8 | z << 4 | x;
        blockLSBArray[index] = (byte) (id & 0xFF);
        if (id > 0xFF) {
            if (blockMSBArray == null) {
                eid$createB2Low();
            }
            if (id > 0xFFF) {
                if (b2High == null) {
                    eid$createB2High();
                }
                if (id > 0xFFFF && b3 == null) {
                    eid$createB3();
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
        return Block.getBlockById(eid$getID(x, y, z));
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
        eid$setID(x, y, z, blockID);
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
                eid$createM1High();
            }
            if (meta > 0xFF && m2 == null) {
                eid$createM2();
            }
        }
        if (m1High != null) {
            m1High.set(x, y, z, (meta >>> 4) & 0xF);
            if (m2 != null) {
                m2[(y << 8) | (z << 4) | x] = (byte) ((meta >>> 8) & 0xFF);
            }
        }
    }

    @Override
    public int eid$getMetadata(int x, int y, int z) {
        return getExtBlockMetadata(x, y, z);
    }

    @Override
    public void eid$setMetadata(int x, int y, int z, int id) {
        setExtBlockMetadata(x, y, z, id);
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
    public byte[] eid$getB1() {
        return blockLSBArray;
    }

    @Override
    public void eid$setB1(byte[] data) {
        blockLSBArray = data;
    }

    @Override
    public NibbleArray eid$getB2Low() {
        return blockMSBArray;
    }

    @Override
    public void eid$setB2Low(NibbleArray data) {
        blockMSBArray = data;
    }

    @Override
    public NibbleArray eid$createB2Low() {
        return (blockMSBArray = new NibbleArray(blocksPerSubChunk, 4));
    }

    @Override
    public NibbleArray eid$getB2High() {
        return b2High;
    }

    @Override
    public void eid$setB2High(NibbleArray data) {
        b2High = data;
    }

    @Override
    public NibbleArray eid$createB2High() {
        return (b2High = new NibbleArray(blocksPerSubChunk, 4));
    }

    @Override
    public byte[] eid$getB3() {
        return b3;
    }

    @Override
    public void eid$setB3(byte[] data) {
        b3 = data;
    }

    @Override
    public byte[] eid$createB3() {
        return b3 = new byte[blocksPerSubChunk];
    }

    @Override
    public NibbleArray eid$getM1Low() {
        return blockMetadataArray;
    }

    @Override
    public void eid$setM1Low(NibbleArray m1Low) {
        blockMetadataArray = m1Low;
    }

    @Override
    public NibbleArray eid$getM1High() {
        return m1High;
    }

    @Override
    public void eid$setM1High(NibbleArray m1High) {
        this.m1High = m1High;
    }

    @Override
    public NibbleArray eid$createM1High() {
        return (m1High = new NibbleArray(blocksPerSubChunk, 4));
    }

    @Override
    public byte[] eid$getM2() {
        return m2;
    }

    @Override
    public void eid$setM2(byte[] m2) {
        this.m2 = m2;
    }

    @Override
    public byte[] eid$createM2() {
        return (m2 = new byte[blocksPerSubChunk]);
    }

    @Override
    public int eid$getBlockMask() {
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
    public int eid$getMetadataMask() {
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
