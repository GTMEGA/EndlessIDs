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
    private short[] block16BArray;

    @Override
    public void setBlockRefCount(int value) {
        blockRefCount = value;
    }

    @Override
    public void setTickRefCount(int value) {
        tickRefCount = value;
    }

    @Override
    public short[] get16B() {
        return block16BArray;
    }

    @Inject(method = "<init>",
            at = @At(value = "RETURN"),
            require = 1)
    private void init(CallbackInfo ci) {
        block16BArray = Hooks.create16BArray();
    }

    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    public Block getBlockByExtId(int var1, int var2, int var3) {
        return Hooks.getBlock(this, var1, var2, var3);
    }

    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    public void func_150818_a(int var1, int var2, int var3, Block var4) {
        Block var5 = this.getBlockByExtId(var1, var2, var3);
        if (var5 != Blocks.air) {
            --this.blockRefCount;
            if (var5.getTickRandomly()) {
                --this.tickRefCount;
            }
        }

        if (var4 != Blocks.air) {
            ++this.blockRefCount;
            if (var4.getTickRandomly()) {
                ++this.tickRefCount;
            }
        }

        int var6 = Hooks.getIdFromBlockWithCheck(var4, var5);
        Hooks.setBlockId(this, var1, var2, var3, var6);
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
