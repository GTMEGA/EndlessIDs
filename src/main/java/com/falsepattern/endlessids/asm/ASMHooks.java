package com.falsepattern.endlessids.asm;

import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import com.falsepattern.endlessids.asm.stubpackage.Reika.DragonAPI.ModList;
import lombok.SneakyThrows;
import lombok.val;

import cpw.mods.fml.common.Loader;

public class ASMHooks {
    @SneakyThrows
    public static void DragonAPIModListConstructorTweak(ModList list) {
        list.successful = false;
        DragonAPIModListTryLoad(list);
    }

    public static boolean DragonAPIModListTryLoad(ModList list) {
        if (!list.successful) {
            try {
                list.condition = Loader.isModLoaded(list.modLabel);
                list.successful = true;
                if (list.condition) {
                    ReikaJavaLibrary.pConsole(
                            "DRAGONAPI: " + list + " detected in the MC installation. Adjusting behavior accordingly.");
                } else {
                    ReikaJavaLibrary.pConsole(
                            "DRAGONAPI: " + list + " not detected in the MC installation. No special action taken.");
                }
                if (list.condition) {
                    ReikaJavaLibrary.pConsole("DRAGONAPI: Attempting to load data from " + list);
                    if (list.blockClasses == null) {
                        ReikaJavaLibrary.pConsole("DRAGONAPI: Could not block class for " + list +
                                                  ": Specified class was null. This may not be an error.");
                    }
                    if (list.itemClasses == null) {
                        ReikaJavaLibrary.pConsole("DRAGONAPI: Could not item class for " + list +
                                                  ": Specified class was null. This may not be an error.");
                    }
                }
            } catch (NullPointerException e) {
                list.condition = list.successful = false;
            }
        }
        return list.condition;
    }

    public static void DragonAPIModListSuppressLogging(Object obj) {}
}
