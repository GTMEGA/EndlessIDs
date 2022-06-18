package com.falsepattern.endlessids.mixin.helpers;

import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public interface IChunkMixin {
    short[] getBiomeShortArray();
    void setBiomeShortArray(short[] data);
}
