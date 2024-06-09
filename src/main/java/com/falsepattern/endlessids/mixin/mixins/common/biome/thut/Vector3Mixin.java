package com.falsepattern.endlessids.mixin.mixins.common.biome.thut;

import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import thut.api.maths.Vector3;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

@Mixin(value = Vector3.class,
       remap = false)
public abstract class Vector3Mixin {

    @Shadow public abstract int intX();

    @Shadow public abstract int intZ();

    /**
     * @author FalsePattern
     * @reason ID Extension
     */
    @Overwrite
    public void setBiome(BiomeGenBase biome, World worldObj) {
        int x = this.intX();
        int z = this.intZ();
        Chunk chunk = worldObj.getChunkFromBlockCoords(x, z);
        val chunkHook = (ChunkBiomeHook) chunk;
        short[] biomes = chunkHook.getBiomeShortArray();
        short newBiome = (short)biome.biomeID;
        int chunkX = Math.abs(x & 15);
        int chunkZ = Math.abs(z & 15);
        int point = chunkX + 16 * chunkZ;
        if (biomes[point] != newBiome) {
            biomes[point] = newBiome;
            chunkHook.setBiomeShortArray(biomes);
            chunk.setChunkModified();
        }

    }
}
