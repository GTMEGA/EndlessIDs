package com.falsepattern.endlessids.mixin.mixins.common.biome.ir3;

import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import jp.plusplus.ir2.IR2;
import jp.plusplus.ir2.blocks.BlockCore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mixin(value = IR2.class,
       priority = 999,
       remap = false)
public abstract class IR2Mixin {
    @Shadow public static boolean oresAreGenerated;

    @Shadow public static boolean generateTinOre;

    @Shadow public static boolean generateCopperOre;

    @Shadow public static boolean generateSilverOre;

    @Shadow public static boolean generateManganeseCrust;

    @Shadow public static boolean generateCobaltCrust;

    /**
     * @author FalsePattern
     * @reason Compat
     */
    @SubscribeEvent
    @Overwrite
    public void generateOrePre(OreGenEvent.Pre event) {
        if (oresAreGenerated) {
            WorldGenerator genTin = new WorldGenMinable(BlockCore.ore, 0, 8, Blocks.stone);
            WorldGenerator genCopper = new WorldGenMinable(BlockCore.ore, 1, 8, Blocks.stone);
            WorldGenerator genSilver = new WorldGenMinable(BlockCore.ore, 2, 8, Blocks.stone);
            WorldGenerator genManganese = new WorldGenMinable(BlockCore.crust, 0, 16, Blocks.gravel);
            WorldGenerator genCobalt = new WorldGenMinable(BlockCore.crust, 1, 16, Blocks.gravel);
            if (generateTinOre && TerrainGen.generateOre(event.world, event.rand, genTin, event.worldX, event.worldZ, OreGenEvent.GenerateMinable.EventType.CUSTOM)) {
                for(int i = 0; i < 14; ++i) {
                    genTin.generate(event.world, event.rand, event.worldX + event.rand.nextInt(16), 30 + event.rand.nextInt(20), event.worldZ + event.rand.nextInt(16));
                }
            }

            if (generateCopperOre && TerrainGen.generateOre(event.world, event.rand, genCopper, event.worldX, event.worldZ, OreGenEvent.GenerateMinable.EventType.CUSTOM)) {
                for(int i = 0; i < 10; ++i) {
                    genCopper.generate(event.world, event.rand, event.worldX + event.rand.nextInt(16), 20 + event.rand.nextInt(20), event.worldZ + event.rand.nextInt(16));
                }
            }

            if (generateSilverOre && TerrainGen.generateOre(event.world, event.rand, genSilver, event.worldX, event.worldZ, OreGenEvent.GenerateMinable.EventType.CUSTOM)) {
                for(int i = 0; i < 4; ++i) {
                    genSilver.generate(event.world, event.rand, event.worldX + event.rand.nextInt(16), 1 + event.rand.nextInt(29), event.worldZ + event.rand.nextInt(16));
                }
            }

            short[] bIds = ((ChunkBiomeHook)event.world.getChunkFromBlockCoords(event.worldX, event.worldZ)).getBiomeShortArray();

            for(int k = 0; k < bIds.length; ++k) {
                if (bIds[k] == BiomeGenBase.ocean.biomeID || bIds[k] == BiomeGenBase.deepOcean.biomeID) {
                    if (generateManganeseCrust && TerrainGen.generateOre(event.world, event.rand, genSilver, event.worldX, event.worldZ, OreGenEvent.GenerateMinable.EventType.CUSTOM)) {
                        for(int i = 0; i < 7; ++i) {
                            genManganese.generate(event.world, event.rand, event.worldX + event.rand.nextInt(16), 16 + event.rand.nextInt(48), event.worldZ + event.rand.nextInt(16));
                        }
                    }

                    if (generateCobaltCrust && TerrainGen.generateOre(event.world, event.rand, genSilver, event.worldX, event.worldZ, OreGenEvent.GenerateMinable.EventType.CUSTOM)) {
                        for(int i = 0; i < 7; ++i) {
                            genCobalt.generate(event.world, event.rand, event.worldX + event.rand.nextInt(16), 16 + event.rand.nextInt(48), event.worldZ + event.rand.nextInt(16));
                        }
                    }
                    break;
                }
            }

        }
    }
}
