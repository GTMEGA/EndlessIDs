package com.falsepattern.endlessids.mixin.helpers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ShortUtil {
    public static int unsignedShortToInt(short s) {
        return s & 65535;
    }

    public static int[] unsignedShortToIntArray(short[] shorts) {
        int[] ints = new int[shorts.length];

        for(int i = 0; i < shorts.length; ++i) {
            ints[i] = unsignedShortToInt(shorts[i]);
        }

        return ints;
    }
}
