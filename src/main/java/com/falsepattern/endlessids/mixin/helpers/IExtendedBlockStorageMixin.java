package com.falsepattern.endlessids.mixin.helpers;

public interface IExtendedBlockStorageMixin {
    void setBlockRefCount(int value);
    void setTickRefCount(int value);
    short[] get16B();
}
