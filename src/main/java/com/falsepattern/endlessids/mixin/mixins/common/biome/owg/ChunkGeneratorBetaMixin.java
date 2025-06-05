/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2025 FalsePattern, The MEGA Team
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, only version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.falsepattern.endlessids.mixin.mixins.common.biome.owg;

import com.falsepattern.endlessids.mixin.helpers.ChunkBiomeHook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import owg.generator.ChunkGeneratorBeta;
import owg.map.MapGenOLD;
import owg.world.ManagerOWG;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenStronghold;

import java.util.Random;

@Mixin(value = ChunkGeneratorBeta.class,
       remap = false)
public abstract class ChunkGeneratorBetaMixin implements IChunkProvider {
    @Shadow private Random rand;

    @Shadow private BiomeGenBase[] biomesForGeneration;

    @Shadow private World worldObj;

    @Shadow public abstract void generateTerrain(int i, int j, Block[] blocks, BiomeGenBase[] abiomegenbase, double[] ad);

    @Shadow public abstract void replaceBlocksForBiome(int i, int j, Block[] blocks, byte[] metadata, BiomeGenBase[] abiomegenbase);

    @Shadow private MapGenOLD field_902_u;

    @Shadow @Final private boolean mapFeaturesEnabled;

    @Shadow private MapGenStronghold strongholdGenerator;

    @Shadow private MapGenMineshaft mineshaftGenerator;

    @Shadow private int biomeSettings;

    /**
     * @author _
     * @reason _
     */
    @Overwrite
    public Chunk provideChunk(int i, int j) {
        this.rand.setSeed((long)i * 341873128712L + (long)j * 132897987541L);
        Block[] blocks = new Block[128 * 16 * 16];
        byte[] metadata = new byte[128 * 16 * 16];
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, i * 16, j * 16, 16, 16);
        double[] ad = ManagerOWG.temperature;
        this.generateTerrain(i, j, blocks, this.biomesForGeneration, ad);
        this.replaceBlocksForBiome(i, j, blocks, metadata, this.biomesForGeneration);
        this.field_902_u.generate(this, this.worldObj, i, j, blocks);
        if (this.mapFeaturesEnabled) {
            this.strongholdGenerator.func_151539_a(this, this.worldObj, i, j, blocks);
            this.mineshaftGenerator.func_151539_a(this, this.worldObj, i, j, blocks);
        }

        Chunk chunk = new Chunk(this.worldObj, blocks, metadata, i, j);
        short[] abyte1 = ((ChunkBiomeHook)chunk).getBiomeShortArray();
        int k;
        if (this.biomeSettings == 0) {
            for(k = 0; k < abyte1.length; ++k) {
                abyte1[k] = (short)this.biomesForGeneration[k].biomeID;
            }
        } else {
            for(k = 0; k < abyte1.length; ++k) {
                abyte1[k] = (short)this.biomesForGeneration[(k * 16 & 255) + k / 16].biomeID;
            }
        }

        chunk.generateSkylightMap();
        return chunk;
    }
}
