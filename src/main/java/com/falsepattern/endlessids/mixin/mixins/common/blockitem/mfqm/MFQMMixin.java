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

package com.falsepattern.endlessids.mixin.mixins.common.blockitem.mfqm;

import MoreFunQuicksandMod.main.MFQM;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

@Mixin(value = MFQM.class,
       remap = false)
public abstract class MFQMMixin {

    @ModifyConstant(method = "preInit",
                    constant = @Constant(stringValue = "Must be different in range between 20 and 31"),
                    require = 1)
    private String changeConfigText(String constant) {
        return constant +
               " (Notice from EndlessIDs: If you enable the ExtendDataWatcher config option in the endlessids config file, the maximum value will be 127 instead, same for the other datawatcher ids in this file)";
    }

    @ModifyConstant(method = "postInit",
                    constant = {@Constant(intValue = VanillaConstants.maxBlockID), @Constant(intValue = 31999)},
                    require = 2)
    private int extendIDs(int constant) {
        return ExtendedConstants.maxBlockID;
    }

    @Redirect(method = "postInit",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/block/Block;getBlockById(I)Lnet/minecraft/block/Block;",
                       remap = true),
              require = 1)
    private Block getBlockByIDNullable(int id) {
        val block = Block.getBlockById(id);
        return block == Blocks.air ? null : block;
    }
}
