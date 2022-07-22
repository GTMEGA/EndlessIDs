package com.falsepattern.endlessids.asm;

import com.falsepattern.endlessids.asm.transformer.ChunkProviderSuperPatcher;
import com.falsepattern.endlessids.asm.transformer.DragonAPIModList;
import com.falsepattern.endlessids.asm.transformer.FmlRegistry;
import com.falsepattern.endlessids.asm.transformer.SpaceCoreModInfoGenerator;

import java.util.HashMap;
import java.util.Map;

public enum ClassEdit {
    ChunkProviderSuperPatcher(new ChunkProviderSuperPatcher()),
    DragonAPIModList(new DragonAPIModList(), "Reika.DragonAPI.ModList"),
    FmlRegistry(new FmlRegistry(),
                "cpw.mods.fml.common.registry.GameData",
                "cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry"),
    SpaceCoreModInfoGenerator(new SpaceCoreModInfoGenerator(), "com.spacechase0.minecraft.spacecore.mcp.ModInfoGenerator");

    private static final Map<String, ClassEdit> editMap;

    static {
        editMap = new HashMap<>();
        for (final ClassEdit edit : values()) {
            for (final String name : edit.classNames) {
                ClassEdit.editMap.put(name, edit);
            }
        }
    }

    private final IClassNodeTransformer transformer;
    private final String[] classNames;

    ClassEdit(final IClassNodeTransformer transformer, final String... classNames) {
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
}
