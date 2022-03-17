package com.falsepattern.endlessids.mixin.mixins.common;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.DataWatcher;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ChunkCoordinates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Mixin(DataWatcher.class)
public abstract class DataWatcherMixin {
    @ModifyConstant(method = {"addObject", "writeWatchedListToPacketBuffer", "func_151509_a", "writeWatchableObjectToPacketBuffer"},
                    constant = @Constant(intValue = 31),
                    require = 4)
    private static int extend1(int constant) {
        return 127;
    }

    @ModifyConstant(method = "writeWatchableObjectToPacketBuffer",
                    constant = @Constant(intValue = 5, ordinal = 0),
                    require = 1)
    private static int extend2(int constant) {
        return 7;
    }

    @ModifyConstant(method = "writeWatchableObjectToPacketBuffer",
                    constant = @Constant(intValue = 255, ordinal = 0),
                    require = 1)
    private static int extend3(int constant) {
        return 1023;
    }

    @Redirect(method = "writeWatchableObjectToPacketBuffer",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/network/PacketBuffer;writeByte(I)Lio/netty/buffer/ByteBuf;"),
              require = 1)
    private static ByteBuf extendWrite(PacketBuffer instance, int p_writeByte_1_) {
        return instance.writeShort(p_writeByte_1_);
    }

    /**
     * @author FalsePattern
     * @reason Direct port from dumped code
     */
    @Overwrite
    public static List<DataWatcher.WatchableObject> readWatchedListFromPacketBuffer(PacketBuffer packet) throws IOException {
        ArrayList<DataWatcher.WatchableObject> watchables = null;

        for(short id = packet.readShort(); id != 32767; id = packet.readShort()) {
            if (watchables == null) {
                watchables = new ArrayList<>();
            }

            int var3 = (id & 896) >> 7;
            int var4 = id & 127;
            DataWatcher.WatchableObject watchable = null;
            switch(var3) {
                case 0:
                    watchable = new DataWatcher.WatchableObject(var3, var4, packet.readByte());
                    break;
                case 1:
                    watchable = new DataWatcher.WatchableObject(var3, var4, packet.readShort());
                    break;
                case 2:
                    watchable = new DataWatcher.WatchableObject(var3, var4, packet.readInt());
                    break;
                case 3:
                    watchable = new DataWatcher.WatchableObject(var3, var4, packet.readFloat());
                    break;
                case 4:
                    watchable = new DataWatcher.WatchableObject(var3, var4, packet.readStringFromBuffer(32767));
                    break;
                case 5:
                    watchable = new DataWatcher.WatchableObject(var3, var4, packet.readItemStackFromBuffer());
                    break;
                case 6:
                    int x = packet.readInt();
                    int y = packet.readInt();
                    int z = packet.readInt();
                    watchable = new DataWatcher.WatchableObject(var3, var4, new ChunkCoordinates(x, y, z));
            }

            watchables.add(watchable);
        }

        return watchables;
    }
}
