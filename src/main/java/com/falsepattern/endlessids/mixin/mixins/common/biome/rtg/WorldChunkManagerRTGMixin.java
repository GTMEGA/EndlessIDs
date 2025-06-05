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

package com.falsepattern.endlessids.mixin.mixins.common.biome.rtg;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rtg.world.biome.WorldChunkManagerRTG;

@Mixin(WorldChunkManagerRTG.class)
public abstract class WorldChunkManagerRTGMixin {
    
    @Shadow(remap = false)
    private float[] borderNoise;

    @Inject(method = "<init>()V",
            at = @At(value = "RETURN"),
            require = 1,
            remap = false)
    private void extendBorderNoise(CallbackInfo ci) {
        this.borderNoise = new float[ExtendedConstants.biomeIDCount];
    }
    
    @ModifyConstant(method = "getRainfall",
                    constant = @Constant(intValue = VanillaConstants.biomeIDMask),
                    require = 2)
    private int extendBiomes(int constant) {
        return ExtendedConstants.biomeIDMask;
    }
}
