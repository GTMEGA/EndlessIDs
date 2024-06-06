package com.falsepattern.endlessids.util;

import net.minecraft.world.chunk.NibbleArray;

import java.util.Arrays;

import static com.falsepattern.endlessids.constants.ExtendedConstants.blocksPerSubChunk;

public class DataUtil {
    public static byte[] ensureSubChunkByteArray(byte[] arr) {
        return arr == null ? null : arr.length == blocksPerSubChunk ? arr : Arrays.copyOf(arr, blocksPerSubChunk);
    }

    public static NibbleArray ensureSubChunkNibbleArray(byte[] data) {
        return data == null ? null : new NibbleArray(data.length == (blocksPerSubChunk >>> 1) ? data : Arrays.copyOf(data, (blocksPerSubChunk >>> 1)), 4);
    }
}
