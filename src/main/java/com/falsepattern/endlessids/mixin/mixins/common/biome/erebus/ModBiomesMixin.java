/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2024 FalsePattern
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.falsepattern.endlessids.mixin.mixins.common.biome.erebus;

import com.falsepattern.endlessids.PlaceholderBiome;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import erebus.ModBiomes;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(value = ModBiomes.class,
       remap = false)
public abstract class ModBiomesMixin {
    @Shadow
    public static int undergroundJungleID;
    @Shadow
    public static int volcanicDesertID;
    @Shadow
    public static int subterraneanSavannahID;
    @Shadow
    public static int elysianFieldsID;
    @Shadow
    public static int ulteriorOutbackID;
    @Shadow
    public static int fungalForestID;
    @Shadow
    public static int submergedSwampID;
    @Shadow
    public static int fieldsSubForestID;
    @Shadow
    public static int jungleSubLakeID;
    @Shadow
    public static int jungleSubAsperGroveID;
    @Shadow
    public static int desertSubCharredForestID;
    @Shadow
    public static int savannahSubRockyWastelandID;
    @Shadow
    public static int savannahSubAsperGroveID;
    @Shadow
    public static int savannahSubSteppeID;

    @ModifyConstant(method = "<clinit>",
                    constant = {@Constant(intValue = 100),
                                @Constant(intValue = 101),
                                @Constant(intValue = 102),
                                @Constant(intValue = 103),
                                @Constant(intValue = 104),
                                @Constant(intValue = 105),
                                @Constant(intValue = 106),
                                @Constant(intValue = 107)},
                    require = 8)
    private static int shiftBiomeIDsUp(int id) {
        return id + 4000;
    }

    @ModifyConstant(method = "init",
                    constant = @Constant(intValue = 128),
                    require = 1)
    private static int extendIDs(int constant) {
        return ExtendedConstants.biomeIDCount - 129;
    }

    @ModifyConstant(method = "init",
                    constant = @Constant(stringValue = "Erebus biome IDs cannot be higher than 127 or smaller than 0!"),
                    require = 1)
    private static String modifyWarning(String str) {
        return "Erebus biome IDs cannot be higher than " + (ExtendedConstants.biomeIDCount - 129) +
               " or smaller than 0!";
    }

    @Inject(method = "init",
            at = @At("HEAD"),
            require = 1)
    private static void cleanupBiomeArrayForAssignment(CallbackInfo ci) {
        val bga = BiomeGenBase.getBiomeGenArray();

        int[] biomes = new int[]{undergroundJungleID, volcanicDesertID, subterraneanSavannahID, elysianFieldsID,
                                 ulteriorOutbackID, fungalForestID, submergedSwampID, fieldsSubForestID,
                                 jungleSubLakeID, jungleSubAsperGroveID, desertSubCharredForestID,
                                 savannahSubRockyWastelandID, savannahSubAsperGroveID, savannahSubSteppeID};
        for (val biome : biomes) {
            if (biome <= 0 || biome >= ExtendedConstants.biomeIDCount - 129) {
                continue;
            }
            if (bga[biome] instanceof PlaceholderBiome) {
                bga[biome] = null;
            }
        }
    }
}
