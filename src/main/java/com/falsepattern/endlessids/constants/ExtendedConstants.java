package com.falsepattern.endlessids.constants;

import com.falsepattern.endlessids.IEConfig;

public class ExtendedConstants {
    //Tunables
    public static final int bitsPerID = 24;
    public static final int bitsPerMetadata = 4;
    public static final int countCorrectionBits = IEConfig.countCorrectionBits;

    public static final int watchableBits = 7;
    public static final int bitsPerBiome = 16;

    //BlockItemIDs
    public static final int nibblesPerID = bitsPerID / 4;
    public static final int bytesPerID = (nibblesPerID + 1) / 2;
    public static final int nibblesPerMetadata = bitsPerMetadata / 4;
    public static final int bytesPerIDPlusMetadata = (nibblesPerID + nibblesPerMetadata + 1) / 2;

    public static final int blockIDMask = (1 << bitsPerID) - 1;
    public static final int blockIDCount = 1 << (bitsPerID - countCorrectionBits);
    public static final int maxBlockID = blockIDCount - 1;

    public static final int bitsPerBlock = 8 + bitsPerID + bitsPerMetadata;
    public static final int nibblesPerBlock = bitsPerBlock / 4;
    public static final int nibblesPerEBS = nibblesPerBlock * 16 * 16 * 16;
    public static final int bytesPerEBS = (nibblesPerEBS + 1) / 2;

    //BiomeIDs
    public static final int biomeIDCount = 1 << bitsPerBiome;
    public static final int biomeIDMask = biomeIDCount - 1;
    public static final int biomeIDNull = biomeIDMask;
    public static final int bytesPerBiome = (bitsPerBiome + 7) / 8;
    //Chunk
    public static final int bytesPerChunk = bytesPerBiome * 256 + bytesPerEBS * 16;
    //DataWatcher
    public static final int watchableCount = 1 << watchableBits;
    public static final int maxWatchableID = watchableCount - 1;
    public static final int watchableMask = (0x7 << watchableBits) | maxWatchableID;
}
