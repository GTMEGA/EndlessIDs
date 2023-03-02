package com.falsepattern.endlessids.asm;

import com.falsepattern.endlessids.asm.transformer.ChunkProviderSuperPatcher;
import com.falsepattern.endlessids.asm.transformer.DragonAPIModList;
import com.falsepattern.endlessids.asm.transformer.FmlRegistry;
import com.falsepattern.endlessids.asm.transformer.SpaceCoreModInfoGenerator;
import com.falsepattern.endlessids.config.GeneralConfig;
import lombok.val;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

public enum ClassEdit {
    ChunkProviderSuperPatcher(new ChunkProviderSuperPatcher(), () -> GeneralConfig.extendBiome),
    DragonAPIModList(new DragonAPIModList(), () -> true, "Reika.DragonAPI.ModList"),
    FmlRegistry(new FmlRegistry(),
                () -> GeneralConfig.extendBlockItem,
                "cpw.mods.fml.common.registry.GameData",
                "cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry"),
    SpaceCoreModInfoGenerator(new SpaceCoreModInfoGenerator(), () -> true,
                              "com.spacechase0.minecraft.spacecore.mcp.ModInfoGenerator");

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
    private final BooleanSupplier cond;
    private final String[] classNames;

    ClassEdit(final IClassNodeTransformer transformer, BooleanSupplier cond, final String... classNames) {
        this.transformer = transformer;
        this.cond = cond;
        this.classNames = classNames;
    }

    public static ClassEdit get(final String className) {
        val ret = ClassEdit.editMap.get(className);
        return ret != null && ret.cond.getAsBoolean() ? ret : null;
    }

    public String getName() {
        return this.name();
    }

    public IClassNodeTransformer getTransformer() {
        return this.transformer;
    }
}
