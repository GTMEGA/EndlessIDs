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

    int getEBSMask();

    int getEBSMSBMask();

    int getID(int x, int y, int z);

    NibbleArray getM1Low();

    void setM1Low(NibbleArray m1Low);

    NibbleArray getM1High();

    void setM1High(NibbleArray m1High);

    void clearM1High();

    NibbleArray createM1High();

    byte[] getM2();

    void setM2(byte[] m2);

    void clearM2();

    byte[] createM2();
}
