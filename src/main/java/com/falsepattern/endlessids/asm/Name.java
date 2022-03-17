package com.falsepattern.endlessids.asm;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public enum Name
{
    hooks("com/falsepattern/endlessids/Hooks"),
    hooks_create16BArray(Name.hooks, "create16BArray", null, null, "()[S"), 
    hooks_getBlockData(Name.hooks, "getBlockData", null, null, "(Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;)[B"), 
    hooks_setBlockData(Name.hooks, "setBlockData", null, null, "(Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;[BI)V"), 
    hooks_writeChunkToNbt(Name.hooks, "writeChunkToNbt", null, null, "(Lnet/minecraft/nbt/NBTTagCompound;Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;)V"), 
    hooks_readChunkFromNbt(Name.hooks, "readChunkFromNbt", null, null, "(Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;Lnet/minecraft/nbt/NBTTagCompound;)V"), 
    hooks_getBlockId(Name.hooks, "getBlockId", null, null, "(Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;III)I"), 
    hooks_getBlockById(Name.hooks, "getBlock", null, null, "(Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;III)Lnet/minecraft/block/Block;"), 
    hooks_setBlockId(Name.hooks, "setBlockId", null, null, "(Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;IIII)V"), 
    hooks_getIdFromBlockWithCheck(Name.hooks, "getIdFromBlockWithCheck", null, null, "(Lnet/minecraft/block/Block;Lnet/minecraft/block/Block;)I"), 
    acl("net/minecraft/world/chunk/storage/AnvilChunkLoader", "aqk"), 
    block("net/minecraft/block/Block", "aji"), 
    chunk("net/minecraft/world/chunk/Chunk", "apx"), 
    extendedBlockStorage("net/minecraft/world/chunk/storage/ExtendedBlockStorage", "apz"), 
    iChunkProvider("net/minecraft/world/chunk/IChunkProvider", "apu"), 
    nbtTagCompound("net/minecraft/nbt/NBTTagCompound", "dh"), 
    nhpc("net/minecraft/client/network/NetHandlerPlayClient", "bjb"), 
    nibbleArray("net/minecraft/world/chunk/NibbleArray", "apv"), 
    packet("net/minecraft/network/Packet", "ft"), 
    packetBuffer("net/minecraft/network/PacketBuffer", "et"), 
    s22("net/minecraft/network/play/server/S22PacketMultiBlockChange", "gk"), 
    s21("net/minecraft/network/play/server/S21PacketChunkData", "gx"), 
    s21_extracted("net/minecraft/network/play/server/S21PacketChunkData$Extracted", "gy"), 
    world("net/minecraft/world/World", "ahb"), 
    dataWatcher("net/minecraft/entity/DataWatcher", "te"), 
    dataWatcher_watchableObject("net/minecraft/entity/DataWatcher$WatchableObject", "tf"), 
    renderGlobal("net/minecraft/client/renderer/RenderGlobal", "bma"), 
    playerControllerMP("net/minecraft/client/multiplayer/PlayerControllerMP", "bje"), 
    itemInWorldManager("net/minecraft/server/management/ItemInWorldManager", "mx"), 
    entityPlayer("net/minecraft/entity/player/EntityPlayer", "yz"), 
    ebs_getBlock(Name.extendedBlockStorage, "getBlockByExtId", "a", "func_150819_a", "(III)Lnet/minecraft/block/Block;"), 
    ebs_setBlock(Name.extendedBlockStorage, "func_150818_a", "a", null, "(IIILnet/minecraft/block/Block;)V"), 
    ebs_getBlockLSBArray(Name.extendedBlockStorage, "getBlockLSBArray", "g", "func_76658_g", "()[B"), 
    ebs_getBlockMSBArray(Name.extendedBlockStorage, "getBlockMSBArray", "i", "func_76660_i", "()Lnet/minecraft/world/chunk/NibbleArray;"), 
    ebs_setBlockMSBArray(Name.extendedBlockStorage, "setBlockMSBArray", "a", "func_76673_a", "(Lnet/minecraft/world/chunk/NibbleArray;)V"), 
    ebs_isEmpty(Name.extendedBlockStorage, "isEmpty", "a", "func_76663_a", "()Z"), 
    ebs_removeInvalidBlocks(Name.extendedBlockStorage, "removeInvalidBlocks", "e", "func_76672_e", "()V"), 
    dataWatcher_addObject(Name.dataWatcher, "addObject", "a", "func_75682_a", "(ILjava/lang/Object;)V"), 
    dataWatcher_writeWatchableObjectToPacketBuffer(Name.dataWatcher, "writeWatchableObjectToPacketBuffer", "a", "func_151510_a", "(Lnet/minecraft/network/PacketBuffer;Lnet/minecraft/entity/DataWatcher$WatchableObject;)V"), 
    dataWatcher_readWatchedListFromPacketBuffer(Name.dataWatcher, "readWatchedListFromPacketBuffer", "b", "func_151508_b", "(Lnet/minecraft/network/PacketBuffer;)Ljava/util/List;"), 
    dataWatcher_writeWatchedListToPacketBuffer(Name.dataWatcher, "writeWatchedListToPacketBuffer", "a", "func_151507_a", "(Ljava/util/List;Lnet/minecraft/network/PacketBuffer;)V"), 
    dataWatcher_func_151509_a(Name.dataWatcher, "func_151509_a", "a", "func_151509_a", "(Lnet/minecraft/network/PacketBuffer;)V"), 
    ebs_blockRefCount(Name.extendedBlockStorage, "blockRefCount", "b", "field_76682_b", "I"), 
    ebs_tickRefCount(Name.extendedBlockStorage, "tickRefCount", "c", "field_76683_c", "I"), 
    acl_writeChunkToNBT(Name.acl, "writeChunkToNBT", "a", "func_75820_a", "(Lnet/minecraft/world/chunk/Chunk;Lnet/minecraft/world/World;Lnet/minecraft/nbt/NBTTagCompound;)V"), 
    acl_readChunkFromNBT(Name.acl, "readChunkFromNBT", "a", "func_75823_a", "(Lnet/minecraft/world/World;Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/world/chunk/Chunk;"), 
    block_getIdFromBlock(Name.block, "getIdFromBlock", "b", "func_149682_b", "(Lnet/minecraft/block/Block;)I"), 
    chunk_fillChunk(Name.chunk, "fillChunk", "a", "func_76607_a", "([BIIZ)V"), 
    packet_readPacketData(Name.packet, "readPacketData", "a", "func_148837_a", "(Lnet/minecraft/network/PacketBuffer;)V"), 
    packet_writePacketData(Name.packet, "writePacketData", "b", "func_148840_b", "(Lnet/minecraft/network/PacketBuffer;)V"), 
    nhpc_handleMultiBlockChange(Name.nhpc, "handleMultiBlockChange", "a", "func_147287_a", "(Lnet/minecraft/network/play/server/S22PacketMultiBlockChange;)V"), 
    s22_init_server(Name.s22, "<init>", null, null, "(I[SLnet/minecraft/world/chunk/Chunk;)V"), 
    s21_undefined1(Name.s21, "func_149275_c", "c", null, "()I"), 
    s21_undefined2(Name.s21, "func_149269_a", "a", null, "(Lnet/minecraft/world/chunk/Chunk;ZI)Lnet/minecraft/network/play/server/S21PacketChunkData$Extracted;"), 
    renderGlobal_playAuxSFX(Name.renderGlobal, "playAuxSFX", "a", "func_72706_a", "(Lnet/minecraft/entity/player/EntityPlayer;IIIII)V"), 
    playerControllerMP_onPlayerDestroyBlock(Name.playerControllerMP, "onPlayerDestroyBlock", "a", "func_78751_a", "(IIII)Z"), 
    itemInWorldManager_tryHarvestBlock(Name.itemInWorldManager, "tryHarvestBlock", "b", "func_73084_b", "(III)Z"), 
    world_breakBlock(Name.world, "func_147480_a", "a", null, "(IIIZ)Z"), 
    ub_bud("exterminatorJeff/undergroundBiomes/worldGen/BiomeUndergroundDecorator"), 
    ub_bud_replaceChunkOres_world(Name.ub_bud, "replaceChunkOres", null, null, "(IILnet/minecraft/world/World;)V"), 
    ub_bud_replaceChunkOres_iChunkProvider(Name.ub_bud, "replaceChunkOres", null, null, "(Lnet/minecraft/world/chunk/IChunkProvider;II)V"), 
    MFQM("MoreFunQuicksandMod/main/MFQM"), 
    MFQM_preInit(Name.MFQM, "preInit", null, null, "(Lcpw/mods/fml/common/event/FMLPreInitializationEvent;)V");
    
    public final Name clazz;
    public final String deobf;
    public final String obf;
    public final String srg;
    public final String desc;
    public String obfDesc;
    
    Name(final String deobf) {
        this(deobf, deobf);
    }
    
    Name(final String deobf, final String obf) {
        this.clazz = null;
        this.deobf = deobf;
        this.obf = obf;
        this.srg = deobf;
        this.desc = null;
    }
    
    Name(final Name clazz, final String deobf, final String obf, final String srg, final String desc) {
        this.clazz = clazz;
        this.deobf = deobf;
        this.obf = ((obf != null) ? obf : deobf);
        this.srg = ((srg != null) ? srg : deobf);
        this.desc = desc;
    }
    
    public boolean matches(final MethodNode x) {
        assert this.desc.startsWith("(");
        return (this.obf.equals(x.name) && this.obfDesc.equals(x.desc)) || (this.srg.equals(x.name) && this.desc.equals(x.desc)) || (this.deobf.equals(x.name) && this.desc.equals(x.desc));
    }
    
    public boolean matches(final FieldNode x) {
        assert !this.desc.startsWith("(");
        return (this.obf.equals(x.name) && this.obfDesc.equals(x.desc)) || (this.srg.equals(x.name) && this.desc.equals(x.desc)) || (this.deobf.equals(x.name) && this.desc.equals(x.desc));
    }
    
    public boolean matches(final MethodInsnNode x, final boolean obfuscated) {
        assert this.desc.startsWith("(");
        if (obfuscated) {
            return (this.clazz.obf.equals(x.owner) && this.obf.equals(x.name) && this.obfDesc.equals(x.desc)) || (this.clazz.srg.equals(x.owner) && this.srg.equals(x.name) && this.desc.equals(x.desc));
        }
        return this.clazz.deobf.equals(x.owner) && this.deobf.equals(x.name) && this.desc.equals(x.desc);
    }
    
    public boolean matches(final FieldInsnNode x, final boolean obfuscated) {
        assert !this.desc.startsWith("(");
        if (obfuscated) {
            return (this.clazz.obf.equals(x.owner) && this.obf.equals(x.name) && this.obfDesc.equals(x.desc)) || (this.clazz.srg.equals(x.owner) && this.srg.equals(x.name) && this.desc.equals(x.desc));
        }
        return this.clazz.deobf.equals(x.owner) && this.deobf.equals(x.name) && this.desc.equals(x.desc);
    }
    
    public MethodInsnNode staticInvocation(final boolean obfuscated) {
        assert this.desc.startsWith("(");
        if (obfuscated) {
            return new MethodInsnNode(184, this.clazz.srg, this.srg, this.desc, false);
        }
        return new MethodInsnNode(184, this.clazz.deobf, this.deobf, this.desc, false);
    }
    
    public MethodInsnNode virtualInvocation(final boolean obfuscated) {
        assert this.desc.startsWith("(");
        if (obfuscated) {
            return new MethodInsnNode(182, this.clazz.srg, this.srg, this.desc, false);
        }
        return new MethodInsnNode(182, this.clazz.deobf, this.deobf, this.desc, false);
    }
    
    public FieldInsnNode staticGet(final boolean obfuscated) {
        assert !this.desc.startsWith("(");
        if (obfuscated) {
            return new FieldInsnNode(178, this.clazz.srg, this.srg, this.desc);
        }
        return new FieldInsnNode(178, this.clazz.deobf, this.deobf, this.desc);
    }
    
    public FieldInsnNode virtualGet(final boolean obfuscated) {
        assert !this.desc.startsWith("(");
        if (obfuscated) {
            return new FieldInsnNode(180, this.clazz.srg, this.srg, this.desc);
        }
        return new FieldInsnNode(180, this.clazz.deobf, this.deobf, this.desc);
    }
    
    public FieldInsnNode staticSet(final boolean obfuscated) {
        assert !this.desc.startsWith("(");
        if (obfuscated) {
            return new FieldInsnNode(179, this.clazz.srg, this.srg, this.desc);
        }
        return new FieldInsnNode(179, this.clazz.deobf, this.deobf, this.desc);
    }
    
    public FieldInsnNode virtualSet(final boolean obfuscated) {
        assert !this.desc.startsWith("(");
        if (obfuscated) {
            return new FieldInsnNode(181, this.clazz.srg, this.srg, this.desc);
        }
        return new FieldInsnNode(181, this.clazz.deobf, this.deobf, this.desc);
    }
    
    private static void translateDescs() {
        final StringBuilder sb = new StringBuilder();
        for (final Name name : values()) {
            if (name.desc != null) {
                int pos;
                int endPos;
                for (pos = 0, endPos = -1; (pos = name.desc.indexOf(76, pos)) != -1; pos = endPos + 1) {
                    sb.append(name.desc, endPos + 1, pos);
                    endPos = name.desc.indexOf(59, pos + 1);
                    String cName = name.desc.substring(pos + 1, endPos);
                    for (final Name name2 : values()) {
                        if (name2.deobf.equals(cName)) {
                            cName = name2.obf;
                            break;
                        }
                    }
                    sb.append('L');
                    sb.append(cName);
                    sb.append(';');
                }
                sb.append(name.desc, endPos + 1, name.desc.length());
                name.obfDesc = sb.toString();
                sb.setLength(0);
            }
        }
    }
    
    static {
        translateDescs();
    }
}
