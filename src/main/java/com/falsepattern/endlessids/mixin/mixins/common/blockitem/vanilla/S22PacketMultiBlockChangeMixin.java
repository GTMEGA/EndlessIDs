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

package com.falsepattern.endlessids.mixin.mixins.common.blockitem.vanilla;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.block.Block;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.world.chunk.Chunk;

import java.io.DataOutputStream;
import java.io.IOException;

@Mixin(S22PacketMultiBlockChange.class)
public abstract class S22PacketMultiBlockChangeMixin {
    private int id;
    private int meta;

    @ModifyConstant(method = "<init>(I[SLnet/minecraft/world/chunk/Chunk;)V",
                    constant = @Constant(intValue = 2 + VanillaConstants.bytesPerIDPlusMetadata,
                                         ordinal = 0),
                    require = 1)
    private int extend1(int constant) {
        return 2 + ExtendedConstants.bytesPerIDPlusMetadata;
    }

    @Redirect(method = "<init>(I[SLnet/minecraft/world/chunk/Chunk;)V",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;getBlock(III)Lnet/minecraft/block/Block;"),
              require = 1)
    private Block extractBlockInfo(Chunk instance, int x, int y, int z) {
        Block block = instance.getBlock(x, y, z);
        id = Block.getIdFromBlock(block);
        meta = instance.getBlockMetadata(x, y, z);
        return block;
    }

    @Redirect(method = "<init>(I[SLnet/minecraft/world/chunk/Chunk;)V",
              at = @At(value = "INVOKE",
                       target = "Ljava/io/DataOutputStream;writeShort(I)V",
                       ordinal = 1),
              require = 1)
    private void hackWrite(DataOutputStream instance, int value) throws IOException {
        instance.writeShort(id & 0xFFFF);
        instance.writeByte((id >>> 16) & 0xFF);
        instance.writeShort(meta);
    }
}
