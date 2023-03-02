package com.falsepattern.endlessids.mixin.helpers;

import net.minecraft.world.chunk.NibbleArray;

public interface IExtendedBlockStorageMixin {
    byte[] getB1();

    void setB1(byte[] data);

    NibbleArray getB2Low();

    void setB2Low(NibbleArray data);

    void clearB2Low();

    NibbleArray createB2Low();

    NibbleArray getB2High();

    void setB2High(NibbleArray data);

    void clearB2High();

    NibbleArray createB2High();

    byte[] getB3();

    void setB3(byte[] data);

    void clearB3();

    byte[] createB3();

    int getStorageFlag();

    int getID(int x, int y, int z);

    short[] getMetaArray();
}
