package com.falsepattern.endlessids.constants;

public class VanillaConstants {
    //Tunables
    public static final int bitsPerID = 12;
    public static final int bitsPerMetadata = 4;
    public static final int countCorrectionBits = 0;

    public static final int watchableBits = 5;

    //IDs
    public static final int nibblesPerID = bitsPerID / 4;
    public static final int nibblesPerMetadata = bitsPerMetadata / 4;

    public static final int bytesPerID = (nibblesPerID + 1) / 2;
    public static final int bytesPerIDPlusMetadata = (nibblesPerID + nibblesPerMetadata + 1) / 2;

    public static final int blockIDMask = (1 << bitsPerID) - 1;
    public static final int blockIDCount = 1 << (bitsPerID - countCorrectionBits);
    public static final int maxBlockID = blockIDCount - 1;

    public static final int bitsPerBlock = 8 + bitsPerID + bitsPerMetadata;
    public static final int nibblesPerBlock = bitsPerBlock / 4;
    public static final int nibblesPerEBS = nibblesPerBlock * 16 * 16 * 16;
    public static final int bytesPerEBS = nibblesPerEBS / 2;
    public static final int bytesPerChunk = 256 + bytesPerEBS * 16;

    //DataWatcher
    public static final int watchableCount = 1 << watchableBits;
    public static final int maxWatchableID = watchableCount - 1;
    public static final int watchableMask = (0x7 << watchableBits) | maxWatchableID;
}
