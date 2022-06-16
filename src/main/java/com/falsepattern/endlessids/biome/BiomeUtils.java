package com.falsepattern.endlessids.biome;

import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

public class BiomeUtils {
   public static byte[] getBlockData(ExtendedBlockStorage ebs) {
      short[] data = get(ebs);
      byte[] ret = new byte[data.length * 2];
      ByteBuffer.wrap(ret).asShortBuffer().put(data);
      return ret;
   }

   public static short[] getBiomeShortArray(Chunk chunk) {
      return ((IChunkMixin)chunk).getBiomeShortArray();
   }

   public static void setBiomeShortArray(short[] biomeIDs, Chunk chunk) {
      ((IChunkMixin)chunk).setBiomeShortArray(biomeIDs);
   }

   public static byte[] fromShortToByteArray(short[] shortArray) {
      byte[] byteArray = new byte[512];

      for(int i = 0; i < 256; ++i) {
         byteArray[i] = (byte)(shortArray[i] & 255);
         byteArray[i + 256] = (byte)(shortArray[i] >> 8);
      }

      return byteArray;
   }

   public static byte[] getBiomeArray(Chunk chunk) {
      byte[] byteArray = new byte[256];
      short[] shortArray = getBiomeShortArray(chunk);

      for(int i = 0; i < 256; ++i) {
         byteArray[i] = (byte)shortArray[i];
      }

      return byteArray;
   }

   public static short[] setBiomeArray(byte[] byteArray) {
      short[] shortArray = new short[256];

      for(int i = 0; i < 256; ++i) {
         shortArray[i] = byteArray[i];
      }

      return shortArray;
   }

   public static int readBiomeArrayFromPacket(Chunk chunk, byte[] array, int int1) {
      byte[] byteArray = new byte[512];
      System.arraycopy(array, int1, byteArray, 0, 512);
      setBiomeShortArray(getShortArrayFromByteArrays(byteArray), chunk);
      return byteArray.length;
   }

   public static int writeBiomeArrayToPacket(Chunk chunk, byte[] array, int int1) {
      byte[] byteArray = fromShortToByteArray(getBiomeShortArray(chunk));
      System.arraycopy(byteArray, 0, array, int1, 512);
      return byteArray.length;
   }

   public static short[] getShortArrayFromByteArrays(byte[] byteArray) {
      short[] shortArray = new short[256];

      for(int i = 0; i < 256; ++i) {
         shortArray[i] = (short)(byteArray[i + 256] << 8 | byteArray[i]);
      }

      return shortArray;
   }

   public static void setBlockData(ExtendedBlockStorage ebs, byte[] data, int offset) {
      ShortBuffer.wrap(get(ebs)).put(ByteBuffer.wrap(data, offset, 8192).asShortBuffer());
      ShortBuffer.wrap(get(ebs)).put(ByteBuffer.wrap(data, offset, 8192).asShortBuffer());
   }

   public static void writeChunkBiomeArrayToNbt(Chunk chunk, NBTTagCompound nbt) {
      byte[] byteArray = fromShortToByteArray(getBiomeShortArray(chunk));
      nbt.setByteArray("Biomes16", byteArray);
   }

   public static void readChunkBiomeArrayFromNbt(Chunk chunk, NBTTagCompound nbt) {
      if (nbt.hasKey("Biomes16", 7)) {
         setBiomeShortArray(getShortArrayFromByteArrays(nbt.getByteArray("Biomes16")), chunk);
      }

   }

   public static void writeChunkToNbt(NBTTagCompound nbt, ExtendedBlockStorage ebs) {
      nbt.setByteArray("Blocks16", getBlockData(ebs));
      short[] data = get(ebs);
      byte[] lsbData = new byte[data.length];
      byte[] msbData = null;

      for(int i = 0; i < data.length; ++i) {
         int id = data[i] & '\uffff';
         if (id <= 255) {
            lsbData[i] = (byte)id;
         } else if (id <= 4095) {
            if (msbData == null) {
               msbData = new byte[data.length / 2];
            }

            lsbData[i] = (byte)id;
            if (i % 2 == 0) {
               msbData[i / 2] = (byte)(msbData[i / 2] | id >>> 8 & 15);
            } else {
               msbData[i / 2] = (byte)(msbData[i / 2] | id >>> 4 & 240);
            }
         }
      }

      nbt.setByteArray("Blocks", lsbData);
      if (msbData != null) {
         nbt.setByteArray("Add", msbData);
      }

   }

   public static void readChunkFromNbt(ExtendedBlockStorage ebs, NBTTagCompound nbt) {
      if (nbt.hasKey("Blocks16")) {
         setBlockData(ebs, nbt.getByteArray("Blocks16"), 0);
      } else if (nbt.hasKey("Blocks")) {
         short[] out = get(ebs);
         byte[] lsbData = nbt.getByteArray("Blocks");
         if (nbt.hasKey("Add")) {
            byte[] msbData = nbt.getByteArray("Add");

            for(int i = 0; i < out.length; i += 2) {
               byte msPart = msbData[i / 2];
               out[i] = (short)(lsbData[i] & 255 | (msPart & 15) << 8);
               out[i + 1] = (short)(lsbData[i + 1] & 255 | (msPart & 240) << 4);
            }
         } else {
            for(int i = 0; i < out.length; ++i) {
               out[i] = (short)(lsbData[i] & 255);
            }
         }
      } else {
         assert false;
      }

   }

   public static int getBlockId(ExtendedBlockStorage ebs, int x, int y, int z) {
      return get(ebs)[y << 8 | z << 4 | x] & '\uffff';
   }

   public static Block getBlock(ExtendedBlockStorage ebs, int x, int y, int z) {
      return Block.getBlockById(getBlockId(ebs, x, y, z));
   }

   public static void setBlockId(ExtendedBlockStorage ebs, int x, int y, int z, int id) {
      if (id >= 0 && id <= 32767) {
         get(ebs)[y << 8 | z << 4 | x] = (short)id;
      } else {
         throw new IllegalArgumentException("id out of range: " + id);
      }
   }

   public static short[] create16BArray() {
      return new short[4096];
   }

   private static short[] get(ExtendedBlockStorage ebs) {
      return null;
   }

   public static void setTickRefCount(ExtendedBlockStorage ebs, int value) {
   }

   public static void setBlockRefCount(ExtendedBlockStorage ebs, int value) {
   }

   public static void removeInvalidBlocksHook(ExtendedBlockStorage ebs) {
      short[] blkIds = get(ebs);
      int cntNonEmpty = 0;
      int cntTicking = 0;

      for(int off = 0; off < blkIds.length; ++off) {
         int id = blkIds[off] & '\uffff';
         if (id > 0) {
            Block block = Block.getBlockById(id);
            if (block != null && block != Blocks.air) {
               ++cntNonEmpty;
               if (block.getTickRandomly()) {
                  ++cntTicking;
               }
            } else {
               blkIds[off] = 0;
            }
         }
      }

      setBlockRefCount(ebs, cntNonEmpty);
      setTickRefCount(ebs, cntTicking);
   }
}
