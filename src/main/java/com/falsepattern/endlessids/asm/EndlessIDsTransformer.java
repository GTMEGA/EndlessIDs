package com.falsepattern.endlessids.asm;

import com.falsepattern.endlessids.Tags;
import com.falsepattern.endlessids.asm.transformer.DevFixer;
import com.falsepattern.endlessids.asm.transformer.FmlRegistry;
import com.falsepattern.endlessids.asm.transformer.GameDataAccelerator;
import com.falsepattern.endlessids.asm.transformer.SpaceCoreModInfoGenerator;
import com.falsepattern.endlessids.asm.transformer.chunk.ChunkProviderSuperPatcher;
import com.falsepattern.endlessids.asm.transformer.chunk.LOTRFieldExposer;
import com.falsepattern.endlessids.config.GeneralConfig;
import com.falsepattern.lib.turboasm.MergeableTurboTransformer;
import com.falsepattern.lib.turboasm.TurboClassTransformer;
import lombok.val;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class EndlessIDsTransformer extends MergeableTurboTransformer {
    public static final Logger logger = LogManager.getLogger(Tags.MODNAME + " ASM");

    private static List<TurboClassTransformer> transformers() {
        val result = new ArrayList<TurboClassTransformer>();
        if (GeneralConfig.extendBiome) {
            result.add(new ChunkProviderSuperPatcher());
            result.add(new LOTRFieldExposer());
        }
        if (GeneralConfig.extendBlockItem) {
            result.add(new FmlRegistry());
        }
        if (GeneralConfig.enableRegistryPerformanceTweak) {
            new GameDataAccelerator();
        }
        if (EndlessIDsCore.deobfuscated) {
            result.add(new SpaceCoreModInfoGenerator());
            result.add(new DevFixer());
        }
        return result;
    }

    public EndlessIDsTransformer() {
        super(transformers());
    }
}
