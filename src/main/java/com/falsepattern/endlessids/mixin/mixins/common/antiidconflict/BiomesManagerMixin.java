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

package com.falsepattern.endlessids.mixin.mixins.common.antiidconflict;

import code.elix_x.coremods.antiidconflict.AntiIdConflictBase;
import code.elix_x.coremods.antiidconflict.managers.BiomesManager;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import com.falsepattern.endlessids.mixin.helpers.AIDCStringFixer;
import lombok.val;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.world.biome.BiomeGenBase;

import java.io.File;
import java.io.PrintWriter;

import static code.elix_x.coremods.antiidconflict.managers.BiomesManager.conflicts;

@Mixin(value = BiomesManager.class,
       remap = false)
public abstract class BiomesManagerMixin {
    @Shadow
    public static String freeBiomeIDs;

    @Shadow
    public static String occupiedBiomeIDs;

    @Shadow
    public static boolean debug;

    @Shadow
    public static int IconflictedIds;

    @Shadow
    public static String conflictedIds;

    private static String[] getIntervalNaming(int first, int last) {
        if (first == last) {
            return new String[]{Integer.toString(first)};
        } else if (first + 1 == last) {
            return new String[]{Integer.toString(first), Integer.toString(last)};
        } else {
            return new String[]{first + "-" + last};
        }
    }

    @ModifyConstant(method = "<clinit>",
                    constant = @Constant(intValue = VanillaConstants.biomeIDCount),
                    require = 1)
    private static int extendIDs(int constant) {
        return ExtendedConstants.biomeIDCount;
    }

    @ModifyConstant(method = "preinit",
                    constant = @Constant(stringValue = "\\biomes"))
    private static String fixPaths1(String original) {
        return AIDCStringFixer.fixString(original);
    }
    @ModifyConstant(method = "setUpBiomesFolder",
                    constant = @Constant(stringValue = "\\main.cfg"))
    private static String fixPaths2(String original) {
        return AIDCStringFixer.fixString(original);
    }

    /**
     * @author FalsePattern
     * @reason Better code, and detecting empty areas.
     */
    @Overwrite
    public static void updateBiomesFolder() throws Exception {
        val biomes = BiomeGenBase.getBiomeGenArray();
        val builder = new StringBuilder();
        boolean hasEmpty = false;
        int emptyRegionStart = 0;
        for (int i = 0; i < biomes.length; ++i) {
            if (biomes[i] == null) {
                if (!hasEmpty) {
                    emptyRegionStart = i;
                    hasEmpty = true;
                }
                ++BiomesManager.freeIds;
            } else if (hasEmpty) {
                hasEmpty = false;
                for (val part : getIntervalNaming(emptyRegionStart, i - 1)) {
                    builder.append(part).append('\n');
                }
            }
        }
        if (hasEmpty) {
            for (val part : getIntervalNaming(emptyRegionStart, ExtendedConstants.biomeIDCount - 1)) {
                builder.append(part).append('\n');
            }
        }
        freeBiomeIDs = builder.toString();
        builder.setLength(0);

        System.out.println("Found " + BiomesManager.freeIds + " free biome ids in total.");

        for (val biome : biomes) {
            if (biome != null) {
                builder.append(biome.biomeID)
                       .append(" : ")
                       .append(biome.biomeName)
                       .append(" (")
                       .append(biome.getBiomeClass())
                       .append(")\n");
                ++BiomesManager.occupiedIds;
            }
        }
        occupiedBiomeIDs = builder.toString();
        builder.setLength(0);

        System.out.println("Found " + BiomesManager.occupiedIds + " occupied biome ids in total.");

        for (val conflict : conflicts) {
            if (conflict != null) {
                if (debug) {
                    System.out.println(
                            "Found biomes id conflict for " + conflict.ID + " : " + conflict.getCrashMessage());
                }
                builder.append(conflict.getCrashMessage()).append('\n');
                ++IconflictedIds;
            }
        }
        conflictedIds = builder.toString();
        builder.setLength(0);

        System.out.println("Found " + IconflictedIds + " conflicted biome ids in total.");
        File available = new File(AntiIdConflictBase.biomesFolder, "availableIDs.txt");
        if (available.exists()) {
            available.delete();
        }

        available.createNewFile();
        PrintWriter writer = new PrintWriter(available);
        writer.println("Total amount of free biome ids: " + available);
        writer.println("List of free biome ids:");
        val freeIDs = freeBiomeIDs.split("\n");

        for (val freeID : freeIDs) {
            writer.println(freeID);
        }

        writer.close();
        val occupied = new File(AntiIdConflictBase.biomesFolder, "occupiedIDs.txt");
        if (occupied.exists()) {
            occupied.delete();
        }

        occupied.createNewFile();
        writer = new PrintWriter(occupied);
        writer.println("Total amount of occupied biome ids: " + BiomesManager.occupiedIds);
        writer.println("Table of occupied biome ids and their owners");
        writer.println("id:name(class)");
        val occupiedIDs = occupiedBiomeIDs.split("\n");

        for (String occupiedID : occupiedIDs) {
            writer.println(occupiedID);
        }

        writer.close();
        val conflicted = new File(AntiIdConflictBase.biomesFolder, "conflictedIDs.txt");
        if (conflicted.exists()) {
            conflicted.delete();
        }

        conflicted.createNewFile();
        writer = new PrintWriter(conflicted);
        writer.println("Total amount of conflicted biome ids: " + IconflictedIds);
        writer.println("IDs in conflict:\n");
        val conflictedIDs = conflictedIds.split("\n");
        for (val conflictedID : conflictedIDs) {
            writer.println(conflictedID);
        }

        writer.close();
        val all = new File(AntiIdConflictBase.biomesFolder, "AllIDs.txt");
        writer = new PrintWriter(all);
        writer.println("Total amount of free biome ids: " + BiomesManager.freeIds);
        writer.println("Total amount of occupied biome ids: " + BiomesManager.occupiedIds);
        writer.println("Total amount of conflicted biome ids: " + IconflictedIds);
        writer.println("All ids and their position:");

        hasEmpty = false;
        emptyRegionStart = 0;

        for (int i = 0; i < biomes.length; ++i) {
            if (!ArrayUtils.isEmpty(conflicts) && conflicts[i] != null) {
                writer.println(conflicts[i].getCrashMessage());
            } else if (biomes[i] != null) {
                if (hasEmpty) {
                    hasEmpty = false;
                    for (val part : getIntervalNaming(emptyRegionStart, i - 1)) {
                        writer.print(part);
                        writer.println(" is Available");
                    }
                }
                writer.print(i);
                writer.print(" is Occupied by ");
                writer.print(biomes[i].biomeName);
                writer.print(" (");
                writer.print(biomes[i].getBiomeClass().getName());
                writer.println(")");
            } else if (!hasEmpty) {
                hasEmpty = true;
                emptyRegionStart = i;
            }
        }
        if (hasEmpty) {
            for (val part : getIntervalNaming(emptyRegionStart, ExtendedConstants.biomeIDCount - 1)) {
                writer.print(part);
                writer.println(" is Available");
            }
        }

        writer.close();
    }
}
