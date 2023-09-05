package com.falsepattern.endlessids.mixin.mixins.common.entity.vanilla;

import com.falsepattern.endlessids.constants.ExtendedConstants;
import com.falsepattern.endlessids.constants.VanillaConstants;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.EntityRegistry;

import java.util.BitSet;

@Mixin(value = EntityRegistry.class,
       remap = false)
public abstract class EntityRegistryMixin {
    @Shadow private BitSet availableIndicies;

    @ModifyConstant(method = "<init>",
                    constant = @Constant(intValue = VanillaConstants.entityIDCount),
                    require = 1)
    private int extendIDs1(int constant) {
        return ExtendedConstants.entityIDCount;
    }

    @ModifyConstant(method = "<init>",
                    constant = @Constant(intValue = VanillaConstants.maxEntityID),
                    require = 1)
    private int extendIDs2(int constant) {
        return ExtendedConstants.maxEntityID;
    }

    /**
     * @author FalsePattern
     * @reason Extended IDs
     */
    @Overwrite
    private int validateAndClaimId(int id) {
        //Keeping the legacy sub-0 id workaround here...

        // workaround for broken ML
        int realId = id;
        if (id < Byte.MIN_VALUE) {
            realId += 3000;
            FMLLog.warning("Compensating for modloader out of range compensation by mod : entityId %d for mod %s is now %d", id, Loader.instance().activeModContainer().getModId(), realId);
        }

        if (realId < 0) {
            realId += Byte.MAX_VALUE;
        }

        if ((realId & ~ExtendedConstants.entityIDMask) != 0) {
            FMLLog.log(Level.ERROR, "The entity ID %d for mod %s is not in the range 1-" + ExtendedConstants.entityIDMask + " and may not work", id, Loader.instance().activeModContainer().getModId());
        }

        if (!availableIndicies.get(realId)) {
            FMLLog.severe("The mod %s has attempted to register an entity ID %d which is already reserved. This could cause severe problems", Loader.instance().activeModContainer().getModId(), id);
        }
        availableIndicies.clear(realId);
        return realId;
    }
}
