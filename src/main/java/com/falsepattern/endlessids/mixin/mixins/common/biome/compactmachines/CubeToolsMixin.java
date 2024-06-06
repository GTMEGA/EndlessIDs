package com.falsepattern.endlessids.mixin.mixins.common.biome.compactmachines;

import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import lombok.val;
import org.dave.CompactMachines.handler.ConfigurationHandler;
import org.dave.CompactMachines.machines.tools.CubeTools;
import org.dave.CompactMachines.tileentity.TileEntityMachine;
import org.dave.CompactMachines.utility.WorldUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;

@Mixin(value = CubeTools.class,
       remap = false)
public abstract class CubeToolsMixin {

    /**
     * @author FalsePattern
     * @reason getBiomeArray fix
     */
    @Overwrite
    public static void setCubeBiome(int coords, BiomeGenBase biome) {
        WorldServer machineWorld = MinecraftServer.getServer().worldServerForDimension(ConfigurationHandler.dimensionId);
        Chunk chunk = machineWorld.getChunkFromBlockCoords(coords * ConfigurationHandler.cubeDistance, 0);
        if (chunk != null && chunk.isChunkLoaded) {
            short[] biomeArray = ((ChunkBiomeHook) chunk).getBiomeShortArray();
            for (int x = 0; x < 15; ++x) {
                for (int z = 0; z < 15; ++z) {
                    biomeArray[z << 4 | x] = (short) biome.biomeID;
                }
            }
        }

    }

    /**
     * @author FalsePattern
     * @reason getBiomeArray fix
     */
    @Overwrite
    public static BiomeGenBase getMachineBiome(TileEntityMachine machine) {
        val TE = (TileEntity)machine;
        val x = TE.xCoord;
        val z = TE.zCoord;
        short[] biomeArray = ((ChunkBiomeHook) TE.getWorldObj()
                                                 .getChunkFromBlockCoords(x, z)).getBiomeShortArray();
        int biomeId = biomeArray[(z & 15) << 4 | x & 15];
        return biomeId > 0 && biomeId < BiomeGenBase.getBiomeGenArray().length &&
               BiomeDictionary.isBiomeRegistered(biomeId) ? BiomeGenBase.getBiome(biomeId) : WorldUtils.getBiomeByName(
                ConfigurationHandler.defaultBiome);
    }
}
