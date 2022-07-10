package com.falsepattern.endlessids.mixin.mixins.common.thaumcraft;

import com.falsepattern.endlessids.mixin.helpers.IChunkMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.misc.PacketBiomeChange;
import thaumcraft.common.lib.utils.Utils;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mixin(value = Utils.class,
       remap = false)
public abstract class UtilsMixin {

    /**
     * @author FalsePattern
     * @reason Biome ID Extension
     */
    @Overwrite
    public static void setBiomeAt(World world, int x, int z, BiomeGenBase biome) {
        if (biome != null) {
            Chunk chunk = world.getChunkFromBlockCoords(x, z);
            short[] array = ((IChunkMixin)chunk).getBiomeShortArray();
            array[(z & 15) << 4 | x & 15] = (short)(biome.biomeID);
            if (!world.isRemote) {
                PacketHandler.INSTANCE.sendToAllAround(new PacketBiomeChange(x, z, (short)biome.biomeID),
                                                       new NetworkRegistry.TargetPoint(world.provider.dimensionId,
                                                                                       x,
                                                                                       world.getHeightValue(x, z),
                                                                                       z,
                                                                                       32.0));
            }
        }
    }
}
