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

package com.falsepattern.endlessids.mixin.mixins.common.biome.ntm;

import com.falsepattern.endlessids.EndlessIDs;
import com.falsepattern.endlessids.mixin.helpers.IHBMBiomeSyncPacket;
import com.hbm.packet.BiomeSyncPacket;
import io.netty.buffer.ByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(value = BiomeSyncPacket.class,
       remap = false)
public abstract class BiomeSyncPacketMixin implements IHBMBiomeSyncPacket {
    @Shadow
    int chunkX;
    @Shadow
    int chunkZ;
    @Shadow
    byte[] biomeArray;
    @Shadow
    byte blockX;
    @Shadow
    byte blockZ;

    @Unique
    private short eid$biome;
    @Unique
    private short[] eid$biomeArray;

    @Override
    public void eid$setBiome(short biome) {
        eid$biome = biome;
    }

    @Override
    public short eid$getBiome() {
        return eid$biome;
    }

    @Override
    public void eid$setBiomeArray(short[] biomeArray) {
        this.biomeArray = EndlessIDs.FAKE_BIOME_ARRAY_PLACEHOLDER;
        eid$biomeArray = Arrays.copyOf(biomeArray, biomeArray.length);
    }

    @Override
    public short[] eid$getBiomeArray() {
        return eid$biomeArray;
    }

    @Inject(method = "toBytes",
            at = @At("HEAD"),
            cancellable = true,
            require = 1)
    private void toBytes(ByteBuf buf, CallbackInfo ci) {
        ci.cancel();
        buf.writeInt(chunkX);
        buf.writeInt(chunkZ);
        if (eid$biomeArray == null) {
            buf.writeBoolean(false);
            buf.writeShort(eid$biome);
            buf.writeByte(blockX);
            buf.writeByte(blockZ);
        } else {
            buf.writeBoolean(true);
            for (int i = 0; i < 256; i++) {
                buf.writeShort(eid$biomeArray[i]);
            }
        }
    }

    @Inject(method = "fromBytes",
            at = @At(value = "HEAD"),
            cancellable = true,
            require = 1)
    private void fromBytes(ByteBuf buf, CallbackInfo ci) {
        ci.cancel();
        chunkX = buf.readInt();
        chunkZ = buf.readInt();
        if (!buf.readBoolean()) {
            eid$biome = buf.readShort();
            blockX = buf.readByte();
            blockZ = buf.readByte();
        } else {
            eid$biomeArray = new short[256];
            for (int i = 0; i < 256; i++) {
                eid$biomeArray[i] = buf.readShort();
            }
        }
    }
}
