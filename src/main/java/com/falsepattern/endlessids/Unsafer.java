package com.falsepattern.endlessids;

import lombok.val;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

public class Unsafer {
    public static final Unsafe UNSAFE;
    static {
        Field unsafeField = null;
        Unsafe unsafe = null;
        for (val field: Unsafe.class.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && field.getType().equals(Unsafe.class)) {
                unsafeField = field;
                break;
            }
        }
        if (unsafeField != null) {
            unsafeField.setAccessible(true);
            try {
                unsafe = (Unsafe) unsafeField.get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (unsafe == null) {
            UNSAFE = null;
            new IllegalStateException("Could not retrieve Unsafe! Falling back to bytebuffers!").printStackTrace();
        } else {
            UNSAFE = unsafe;
        }
    }

    public static void arraycopy(short[] source, int sourceOffset, byte[] target, int targetOffset, int shorts) {
        if (UNSAFE != null) {
            val sourceOffsetBytes = sourceOffset * 2;
            val arrayOffsetSource = UNSAFE.arrayBaseOffset(source.getClass());
            val arrayOffsetTarget = UNSAFE.arrayBaseOffset(target.getClass());
            UNSAFE.copyMemory(source, arrayOffsetSource + sourceOffsetBytes, target, arrayOffsetTarget + targetOffset, (long)shorts << 1);
        } else {
            ByteBuffer.wrap(target, targetOffset, target.length - targetOffset).asShortBuffer().put(source, sourceOffset, shorts);
        }
    }

    public static void arraycopy(byte[] source, int sourceOffset, short[] target, int targetOffset, int shorts) {
        if (UNSAFE != null) {
            val targetOffsetBytes = targetOffset * 2;
            val arrayOffsetSource = UNSAFE.arrayBaseOffset(source.getClass());
            val arrayOffsetTarget = UNSAFE.arrayBaseOffset(target.getClass());
            UNSAFE.copyMemory(source, arrayOffsetSource + sourceOffset, target, arrayOffsetTarget + targetOffsetBytes, (long)shorts << 1);
        } else {
            ShortBuffer.wrap(target, targetOffset, target.length - targetOffset).put(ByteBuffer.wrap(source, sourceOffset, shorts * 2).asShortBuffer());
        }
    }
}
