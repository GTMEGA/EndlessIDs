package com.falsepattern.endlessids.mixin.mixins.common.datawatcher.vanilla;

import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import io.netty.buffer.ByteBuf;
import lombok.val;
import lombok.var;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.DataWatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ChunkCoordinates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(value = DataWatcher.class,
       priority = 1001)
public abstract class DataWatcherMixin {
    @ModifyConstant(method = {"addObject", "func_151509_a"},
                    constant = @Constant(intValue = VanillaConstants.maxWatchableID),
                    require = 2)
    private int extend1d(int constant) {
        return ExtendedConstants.maxWatchableID;
    }

    /**
     * @author FalsePattern
     * @reason Extend IDs
     */
    @Overwrite
    public static List<DataWatcher.WatchableObject> readWatchedListFromPacketBuffer(PacketBuffer packet)
            throws IOException {
        ArrayList<DataWatcher.WatchableObject> watchables = null;

        for (int type = packet.readByte() & 0xFF; type != 0x7F; type = packet.readByte() & 0xFF) {
            if (watchables == null) {
                watchables = new ArrayList<>();
            }

            boolean shortID = (type & 0x80) != 0;
            type &= 0x7F;
            int id = shortID
                     ? packet.readShort() & 0xFFFF
                     : packet.readByte() & 0xFF;
            DataWatcher.WatchableObject watchable = null;
            switch (type) {
                case 0:
                    watchable = new DataWatcher.WatchableObject(type, id, packet.readByte());
                    break;
                case 1:
                    watchable = new DataWatcher.WatchableObject(type, id, packet.readShort());
                    break;
                case 2:
                    watchable = new DataWatcher.WatchableObject(type, id, packet.readInt());
                    break;
                case 3:
                    watchable = new DataWatcher.WatchableObject(type, id, packet.readFloat());
                    break;
                case 4:
                    boolean present = packet.readBoolean();
                    watchable = new DataWatcher.WatchableObject(type, id, present ? packet.readStringFromBuffer(32767) : null);
                    break;
                case 5:
                    watchable = new DataWatcher.WatchableObject(type, id, packet.readItemStackFromBuffer());
                    break;
                case 6:
                    ChunkCoordinates coords = null;
                    if (packet.readBoolean()) {
                        int x = packet.readInt();
                        int y = packet.readInt();
                        int z = packet.readInt();
                        coords = new ChunkCoordinates(x, y, z);
                    }
                    watchable = new DataWatcher.WatchableObject(type, id, coords);
            }

            watchables.add(watchable);
        }

        return watchables;
    }


    /**
     * Writes a watchable object (entity attribute of type {byte, short, int, float, string, ItemStack,
     * ChunkCoordinates}) to the specified PacketBuffer
     *
     * @author FalsePattern
     * @reason Extend IDs
     */

    @Overwrite
    private static void writeWatchableObjectToPacketBuffer(PacketBuffer buf, DataWatcher.WatchableObject watcher)
            throws IOException {
        var type = watcher.getObjectType() & 0x7F;
        val id = watcher.getDataValueId() & 0xFFFF;
        if (id > 255)
            type |= 0x80;
        buf.writeByte(type);
        if (id > 255) {
            buf.writeShort(id & 0xFFFF);
        } else {
            buf.writeByte(id & 0xFF);
        }

        switch (type) {
            case 0:
                buf.writeByte((byte) watcher.getObject());
                break;
            case 1:
                buf.writeShort((short) watcher.getObject());
                break;
            case 2:
                buf.writeInt((int) watcher.getObject());
                break;
            case 3:
                buf.writeFloat((float) watcher.getObject());
                break;
            case 4:
                val obj = (String) watcher.getObject();
                if (obj == null) {
                    buf.writeBoolean(false);
                } else {
                    buf.writeBoolean(true);
                    buf.writeStringToBuffer(obj);
                }
                break;
            case 5:
                ItemStack itemstack = (ItemStack) watcher.getObject();
                buf.writeItemStackToBuffer(itemstack);
                break;
            case 6:
                ChunkCoordinates chunkcoordinates = (ChunkCoordinates) watcher.getObject();
                if (chunkcoordinates == null) {
                    buf.writeBoolean(false);
                } else {
                    buf.writeBoolean(true);
                    buf.writeInt(chunkcoordinates.posX);
                    buf.writeInt(chunkcoordinates.posY);
                    buf.writeInt(chunkcoordinates.posZ);
                }
        }
    }
}
