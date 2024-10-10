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

package com.falsepattern.endlessids.mixin.mixins.common.potion.thaumcraft;

import com.falsepattern.endlessids.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thaumcraft.api.potions.PotionFluxTaint;
import thaumcraft.api.potions.PotionVisExhaust;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.Config;
import thaumcraft.common.lib.potions.PotionBlurredVision;
import thaumcraft.common.lib.potions.PotionDeathGaze;
import thaumcraft.common.lib.potions.PotionInfectiousVisExhaust;
import thaumcraft.common.lib.potions.PotionSunScorned;
import thaumcraft.common.lib.potions.PotionThaumarhia;
import thaumcraft.common.lib.potions.PotionUnnaturalHunger;
import thaumcraft.common.lib.potions.PotionWarpWard;

import net.minecraft.potion.Potion;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

@Mixin(value = Config.class,
       remap = false)
public abstract class ConfigMixin {
    @Shadow public static Configuration config;

    @Shadow public static int potionTaintPoisonID;

    @Shadow public static int potionVisExhaustID;

    @Shadow public static int potionInfVisExhaustID;

    @Shadow public static int potionUnHungerID;

    @Shadow public static int potionWarpWardID;

    @Shadow public static int potionDeathGazeID;

    @Shadow public static int potionBlurredID;

    @Shadow public static int potionSunScornedID;

    @Shadow public static int potionThaumarhiaID;

    @Shadow
    public static void save() {
    }

    /**
     * @author FalsePattern
     * @reason ID Extension, flatten recursion
     */
    @Overwrite
    static int getNextPotionId(int id) {
        boolean next = true;
        while (next) {
            next = false;
            if (id <= 0 || id >= Potion.potionTypes.length || Potion.potionTypes[id] != null) {
                ++id;
                if (id < Potion.potionTypes.length) {
                    next = true;
                } else {
                    id = -1;
                }
            }
        }
        return id;
    }

    @Unique
    private static final String EID$CATEGORY_POTION_IDS = "Potion_IDs";

    @Inject(method = "initialize",
            at = @At(value = "INVOKE",
                     target = "Lnet/minecraftforge/common/config/Configuration;load()V"),
            require = 1)
    private static void addPotionIDsCategory(File file, CallbackInfo ci) {
        config.addCustomCategoryComment(EID$CATEGORY_POTION_IDS, "Potion IDs (extended by " + Tags.MODNAME + ")");
    }

    @Unique
    private static int eid$tryGetPotionFromConfigOrAuto(String name, int id) {
        id = getNextPotionId(id);
        return config.get(EID$CATEGORY_POTION_IDS, name, id).getInt();
    }

    @Inject(method = "initPotions",
            at = @At("HEAD"),
            cancellable = true,
            require = 1)
    private static void endlessIDsInitPotions(CallbackInfo ci) {
        ci.cancel();
        int id = 1000;

        id = eid$tryGetPotionFromConfigOrAuto("potion_1_flux_taint", id);
        if (id >= 0) {
            potionTaintPoisonID = id;
            PotionFluxTaint.instance = new PotionFluxTaint(potionTaintPoisonID, true, 0x663377);
            PotionFluxTaint.init();
            Thaumcraft.log.info("Initializing PotionFluxTaint with id {}", id);
        }

        id = eid$tryGetPotionFromConfigOrAuto("potion_2_vis_exhaust", id);
        if (id >= 0) {
            potionVisExhaustID = id;
            PotionVisExhaust.instance = new PotionVisExhaust(potionVisExhaustID, true, 0x664477);
            PotionVisExhaust.init();
            Thaumcraft.log.info("Initializing PotionVisExhaust with id {}", id);
        }

        id = eid$tryGetPotionFromConfigOrAuto("potion_3_infectious_vis_exhaust", id);
        if (id >= 0) {
            potionInfVisExhaustID = id;
            PotionInfectiousVisExhaust.instance = new PotionInfectiousVisExhaust(potionInfVisExhaustID, true, 0x665577);
            PotionInfectiousVisExhaust.init();
            Thaumcraft.log.info("Initializing PotionInfectiousVisExhaust with id {}", id);
        }

        id = eid$tryGetPotionFromConfigOrAuto("potion_4_unnatural_hunger", id);
        if (id >= 0) {
            potionUnHungerID = id;
            PotionUnnaturalHunger.instance = new PotionUnnaturalHunger(potionUnHungerID, true, 0x446633);
            PotionUnnaturalHunger.init();
            Thaumcraft.log.info("Initializing PotionUnnaturalHunger with id {}", id);
        }

        id = eid$tryGetPotionFromConfigOrAuto("potion_5_warp_ward", id);
        if (id >= 0) {
            potionWarpWardID = id;
            PotionWarpWard.instance = new PotionWarpWard(potionWarpWardID, false, 0xe0f2f7);
            PotionWarpWard.init();
            Thaumcraft.log.info("Initializing PotionWarpWard with id {}", id);
        }

        id = eid$tryGetPotionFromConfigOrAuto("potion_6_death_gaze", id);
        if (id >= 0) {
            potionDeathGazeID = id;
            PotionDeathGaze.instance = new PotionDeathGaze(potionDeathGazeID, true, 0x664433);
            PotionDeathGaze.init();
            Thaumcraft.log.info("Initializing PotionDeathGaze with id {}", id);
        }

        id = eid$tryGetPotionFromConfigOrAuto("potion_7_blurred_vision", id);
        if (id >= 0) {
            potionBlurredID = id;
            PotionBlurredVision.instance = new PotionBlurredVision(potionBlurredID, true, 0x808080);
            PotionBlurredVision.init();
            Thaumcraft.log.info("Initializing PotionBlurredVision with id {}", id);
        }

        id = eid$tryGetPotionFromConfigOrAuto("potion_8_sun_scorned", id);
        if (id >= 0) {
            potionSunScornedID = id;
            PotionSunScorned.instance = new PotionSunScorned(potionSunScornedID, true, 0xf8d86a);
            PotionSunScorned.init();
            Thaumcraft.log.info("Initializing PotionSunScorned with id {}", id);
        }

        id = eid$tryGetPotionFromConfigOrAuto("potion_9_thaumarhia", id);
        if (id >= 0) {
            potionThaumarhiaID = id;
            PotionThaumarhia.instance = new PotionThaumarhia(potionThaumarhiaID, true, 0x664477);
            PotionThaumarhia.init();
            Thaumcraft.log.info("Initializing PotionThaumarhia with id {}", id);
        }
        save();
    }
}
