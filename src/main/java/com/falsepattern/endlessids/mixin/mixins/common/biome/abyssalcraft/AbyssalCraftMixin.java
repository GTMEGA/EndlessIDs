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

package com.falsepattern.endlessids.mixin.mixins.common.biome.abyssalcraft;

import com.falsepattern.endlessids.PlaceholderBiome;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.shinoow.abyssalcraft.AbyssalCraft;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.biome.BiomeGenBase;

@Mixin(value = AbyssalCraft.class,
       remap = false)
public abstract class AbyssalCraftMixin {
    @Shadow
    public static int configBiomeId1;
    @Shadow
    public static int configBiomeId2;
    @Shadow
    public static int configBiomeId3;
    @Shadow
    public static int configBiomeId4;
    @Shadow
    public static int configBiomeId5;
    @Shadow
    public static int configBiomeId6;
    @Shadow
    public static int configBiomeId7;
    @Shadow
    public static int configBiomeId8;
    @Shadow
    public static int configBiomeId9;
    @Shadow
    public static int configBiomeId10;
    @Shadow
    public static int configBiomeId11;
    @Shadow
    public static int configBiomeId12;
    @Shadow
    public static int configBiomeId13;

    @ModifyConstant(method = "syncConfig",
                    constant = {@Constant(intValue = 100, ordinal = 0),
                                @Constant(intValue = 101, ordinal = 0),
                                @Constant(intValue = 102, ordinal = 0),
                                @Constant(intValue = 103, ordinal = 0),
                                @Constant(intValue = 104, ordinal = 0),
                                @Constant(intValue = 105, ordinal = 0),
                                @Constant(intValue = 106, ordinal = 0),
                                @Constant(intValue = 107, ordinal = 0),
                                @Constant(intValue = 108, ordinal = 0),
                                @Constant(intValue = 109, ordinal = 0),
                                @Constant(intValue = 110, ordinal = 0),
                                @Constant(intValue = 112, ordinal = 0),
                                @Constant(intValue = 113, ordinal = 0)},
                    require = 13)
    private static int shiftBiomeIDsUp(int constant) {
        return constant + 8000;
    }

    @Inject(method = "checkBiomeIds",
            at = @At("HEAD"),
            require = 1)
    private void cleanupBiomeArrayForAssignment(CallbackInfo ci) {
        val bga = BiomeGenBase.getBiomeGenArray();

        int[] biomes = new int[]{configBiomeId1, configBiomeId2, configBiomeId3, configBiomeId4, configBiomeId5,
                                 configBiomeId6, configBiomeId7, configBiomeId8, configBiomeId9, configBiomeId10,
                                 configBiomeId11, configBiomeId12, configBiomeId13};
        for (val biome : biomes) {
            if (biome < 0 || biome >= ExtendedConstants.biomeIDCount) {
                continue;
            }
            if (bga[biome] instanceof PlaceholderBiome) {
                bga[biome] = null;
            }
        }
    }
}
