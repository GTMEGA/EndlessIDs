package com.falsepattern.endlessids.asm;

import java.util.HashMap;
import com.falsepattern.endlessids.asm.transformer.FmlRegistry;
import java.util.Map;

public enum ClassEdit
{
    //SelfHooks(new SelfHooks(), new String[] { "com.falsepattern.endlessids.Hooks" }),
    //VanillaBlockFire(new VanillaBlockFire(), new String[] { "net.minecraft.block.BlockFire" }),
    //VanillaStatList(new VanillaStatList(), new String[] { "net.minecraft.stats.StatList" }),
    //VanillaAnvilChunkLoader(new VanillaAnvilChunkLoader(), new String[] { "net.minecraft.world.chunk.storage.AnvilChunkLoader" }),
    //VanillaChunk(new VanillaChunk(), new String[] { "net.minecraft.world.chunk.Chunk" }),
    //VanillaExtendedBlockStorage(new VanillaExtendedBlockStorage(), new String[] { "net.minecraft.world.chunk.storage.ExtendedBlockStorage" }),
    //VanillaDataWatcher(new VanillaDataWatcher(), new String[] { "net.minecraft.entity.DataWatcher" }),
    //VanillaNetHandlerPlayClient(new VanillaNetHandlerPlayClient(), new String[] { "net.minecraft.client.network.NetHandlerPlayClient" }),
    //VanillaS21PacketChunkData(new VanillaS21PacketChunkData(), new String[] { "net.minecraft.network.play.server.S21PacketChunkData" }),
    //VanillaS22PacketMultiBlockChange(new VanillaS22PacketMultiBlockChange(), new String[] { "net.minecraft.network.play.server.S22PacketMultiBlockChange" }),
    //VanillaS24PacketBlockActivation(new VanillaS24PacketBlockActivation(), new String[] { "net.minecraft.network.play.server.S24PacketBlockAction" }),
    //VanillaS26PacketMapChunkBulk(new VanillaS26PacketMapChunkBulk(), new String[] { "net.minecraft.network.play.server.S26PacketMapChunkBulk" }),
    //VanillaRenderGlobal(new VanillaRenderGlobal(), new String[] { "net.minecraft.client.renderer.RenderGlobal" }),
    //VanillaPlayerControllerMP(new VanillaPlayerControllerMP(), new String[] { "net.minecraft.client.multiplayer.PlayerControllerMP" }),
    //VanillaItemInWorldManager(new VanillaItemInWorldManager(), new String[] { "net.minecraft.server.management.ItemInWorldManager" }),
    //VanillaWorld(new VanillaWorld(), new String[] { "net.minecraft.world.World" }),
    FmlRegistry(new FmlRegistry(), new String[] { "cpw.mods.fml.common.registry.GameData", "cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry" }),
    //CofhBlockHelper(new CofhBlockHelper(), new String[] { "cofh.lib.util.helpers.BlockHelper" }),
    //UndergroundBiomesOreUBifier(new UndergroundBiomesOreUBifier(), new String[] { "exterminatorJeff.undergroundBiomes.worldGen.OreUBifier" }),
    //UndergroundBiomesBiomeUndergroundDecorator(new UndergroundBiomesBiomeUndergroundDecorator(), new String[] { "exterminatorJeff.undergroundBiomes.worldGen.BiomeUndergroundDecorator" }),
    //MFQM(new MFQM(), new String[] { "MoreFunQuicksandMod.main.MFQM" }),
    //WorldEditBaseBlock(new WorldEditBaseBlock(), new String[] { "com.sk89q.worldedit.blocks.BaseBlock" })
    ;
    
    private static final Map<String, ClassEdit> editMap;
    private final IClassNodeTransformer transformer;
    private final String[] classNames;
    
    ClassEdit(final IClassNodeTransformer transformer, final String[] classNames) {
        this.transformer = transformer;
        this.classNames = classNames;
    }
    
    public static ClassEdit get(final String className) {
        return ClassEdit.editMap.get(className);
    }
    
    public String getName() {
        return this.name();
    }
    
    public IClassNodeTransformer getTransformer() {
        return this.transformer;
    }
    
    static {
        editMap = new HashMap<>();
        for (final ClassEdit edit : values()) {
            for (final String name : edit.classNames) {
                ClassEdit.editMap.put(name, edit);
            }
        }
    }
}
