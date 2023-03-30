package com.falsepattern.endlessids.managers;

import com.falsepattern.chunk.api.ChunkDataManager;
import com.falsepattern.endlessids.Hooks;
import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import lombok.var;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;

import java.nio.ByteBuffer;

public class BiomeManager implements ChunkDataManager, ChunkDataManager.PacketDataManager, ChunkDataManager.ChunkNBTDataManager {

    @Override
    public String domain() {
        return Tags.MODID;
    }

    @Override
    public String id() {
        return "biome";
    }

    @Override
    public int maxPacketSize() {
        return 2 * 16 * 16;
    }

    @Override
    public void writeToBuffer(Chunk chunk, int ebsMask, boolean forceUpdate, ByteBuffer data) {
        data.asShortBuffer().put(((IChunkMixin)chunk).getBiomeShortArray());
        data.position(data.position() + maxPacketSize());
    }

    @Override
    public void readFromBuffer(Chunk chunk, int ebsMask, boolean forceUpdate, ByteBuffer buffer) {
        var arr = ((IChunkMixin)chunk).getBiomeShortArray();
        if (arr == null) {
            arr = new short[16 * 16];
            ((IChunkMixin)chunk).setBiomeShortArray(arr);
        }
        buffer.asShortBuffer().get(arr);
        buffer.position(buffer.position() + maxPacketSize());
    }

    @Override
    public boolean chunkPrivilegedAccess() {
        return true;
    }

    @Override
    public void writeChunkToNBT(Chunk chunk, NBTTagCompound nbt) {
        byte[] arr = Hooks.shortToByteArray(((IChunkMixin)chunk).getBiomeShortArray());
        nbt.setByteArray("Biomes16v2", arr);
    }

    @Override
    public void readChunkFromNBT(Chunk chunk, NBTTagCompound nbt) {
        var data = ((IChunkMixin) chunk).getBiomeShortArray();
        if (data == null) {
            data = new short[16 * 16];
            ((IChunkMixin)chunk).setBiomeShortArray(data);
        }
        if (nbt.hasKey("Biomes16v2", 7)) {
            Hooks.byteToShortArray(nbt.getByteArray("Biomes16v2"), 0, data, 0, data.length * 2);
        } else if (nbt.hasKey("Biomes16", 7)) { //NotEnoughBiomes DFU
            Hooks.byteToShortArrayLegacy(nbt.getByteArray("Biomes16"), data);
        } else if (nbt.hasKey("Biomes", 7)) { //Vanilla DFU
            Hooks.scatter(nbt.getByteArray("Biomes"), data);
        }

    }
}
