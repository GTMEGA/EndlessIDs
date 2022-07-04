package com.falsepattern.endlessids.mixin.helpers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AIDCStringFixer {
    public static String fixString(String str) {
        str = str.replace("\\", "");
        str = str.replace("vaible", "vailable");
        return str;
    }
}
