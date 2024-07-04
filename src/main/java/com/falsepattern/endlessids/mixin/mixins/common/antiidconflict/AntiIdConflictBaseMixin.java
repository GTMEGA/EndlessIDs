/*
 * EndlessIDs
 *
 * Copyright (C) 2022-2024 FalsePattern
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the words "MEGA"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.falsepattern.endlessids.mixin.mixins.common.antiidconflict;

import code.elix_x.coremods.antiidconflict.AntiIdConflictBase;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mixin(value = AntiIdConflictBase.class,
       remap = false)
public abstract class AntiIdConflictBaseMixin {
    @Shadow
    public static File mainFolder;

    @Inject(method = "preinit",
            at = @At("HEAD"),
            require = 1)
    private static void fixPath(FMLPreInitializationEvent event, CallbackInfo ci) {
        mainFolder = new File(event.getModConfigurationDirectory().getPath(), "AntiIDConflict");
    }

    @Redirect(method = "preinit",
              at = @At(value = "FIELD",
                       target = "Lcode/elix_x/coremods/antiidconflict/AntiIdConflictBase;mainFolder:Ljava/io/File;",
                       opcode = Opcodes.PUTSTATIC),
              require = 1)
    private static void removeOldPut(File value) {
    }
}
