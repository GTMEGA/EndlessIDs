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

package com.falsepattern.endlessids.mixin.mixins.common.entity.vanilla;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import com.falsepattern.endlessids.mixin.helpers.EntityRegistryAccessor;
import com.falsepattern.endlessids.util.RemappingEntityIDMap;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import cpw.mods.fml.common.registry.EntityRegistry;

import java.util.Map;


@Mixin(EntityList.class)
public abstract class EntityListMixin {
    @Shadow
    public static Map<Integer, Class<? extends Entity>> IDtoClassMapping;

    @Inject(method = "<clinit>",
            at = @At(value = "RETURN"),
            require = 1)
    private static void hijackMap(CallbackInfo ci) {
        IDtoClassMapping = new RemappingEntityIDMap(IDtoClassMapping);
    }

    @ModifyConstant(method = "addMapping(Ljava/lang/Class;Ljava/lang/String;I)V",
                    constant = @Constant(intValue = VanillaConstants.maxEntityID),
                    require = 1)
    private static int extendRange(int constant) {
        return ExtendedConstants.maxEntityID;
    }

    @Inject(method = "addMapping(Ljava/lang/Class;Ljava/lang/String;I)V",
            at = @At(value = "INVOKE",
                     target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;",
                     ordinal = 0),
            require = 1)
    private static void updateForgeIDMap(Class<? extends Entity> entityClass, String entityName, int id, CallbackInfo ci) {
        val availableIndicies = ((EntityRegistryAccessor) EntityRegistry.instance()).eids$availableIndicies();
        availableIndicies.clear(id);
    }
}
