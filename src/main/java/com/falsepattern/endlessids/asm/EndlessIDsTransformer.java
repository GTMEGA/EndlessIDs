/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2025 FalsePattern, The MEGA Team
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, only version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
