package com.falsepattern.endlessids.asm;

import com.falsepattern.endlessids.asm.transformer.ChunkProviderSuperPatcher;
import com.falsepattern.endlessids.asm.transformer.FmlRegistry;
import com.falsepattern.endlessids.asm.transformer.GameDataAccelerator;
import com.falsepattern.endlessids.asm.transformer.LOTRFieldExposer;
import com.falsepattern.endlessids.asm.transformer.SpaceCoreModInfoGenerator;
import com.falsepattern.endlessids.config.GeneralConfig;
import lombok.val;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

public enum ClassEdit {
    ChunkProviderSuperPatcher(new ChunkProviderSuperPatcher(), () -> GeneralConfig.extendBiome),
    FmlRegistry(new FmlRegistry(),
                () -> GeneralConfig.extendBlockItem,
                "cpw.mods.fml.common.registry.GameData",
                "cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry"),
    LOTRFieldExposer(new LOTRFieldExposer(),
                     () -> GeneralConfig.extendBiome,
                     "lotr.common.network.LOTRPacketBiomeVariantsWatch"),
    SpaceCoreModInfoGenerator(new SpaceCoreModInfoGenerator(), () -> true,
                              "com.spacechase0.minecraft.spacecore.mcp.ModInfoGenerator"),
    GameDataAccelerator(new GameDataAccelerator(), () -> GeneralConfig.enableRegistryPerformanceTweak, "cpw.mods.fml.common.registry.GameData"),
    ;

    private static final Map<String, List<ClassEdit>> editMap;

    static {
        editMap = new HashMap<>();
        for (final ClassEdit edit : values()) {
            for (final String name : edit.classNames) {
                ClassEdit.editMap.computeIfAbsent(name, (ignored) -> new ArrayList<>()).add(edit);
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

    public static List<ClassEdit> get(final String className) {
        val edit = ClassEdit.editMap.get(className);
        if (edit == null) {
            return Collections.emptyList();
        }
        return edit.stream()
                   .filter(r -> r.cond.getAsBoolean())
                   .collect(Collectors.toList());
    }

    public String getName() {
        return this.name();
    }

    public IClassNodeTransformer getTransformer() {
        return this.transformer;
    }
}
