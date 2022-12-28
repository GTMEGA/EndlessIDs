package com.falsepattern.endlessids.mixin.helpers;

public class PotionIDHelper {
    public static final ThreadLocal<Integer> theID = new ThreadLocal<>();
}
