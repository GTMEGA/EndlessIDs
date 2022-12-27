package com.falsepattern.endlessids.mixin.mixins.common.galaxyspace;

import com.falsepattern.endlessids.mixin.helpers.BiomePatchHelper;
import com.falsepattern.endlessids.mixin.helpers.GalaxySpaceHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.chunk.Chunk;

@Pseudo
@Mixin(targets = {"galaxyspace.systems.SolarSystem.satellites.venus.dimension.ChunkProviderVenusSS",
                  "galaxyspace.SolarSystem.satellites.venus.dimension.ChunkProviderVenusSS"},
       remap = false)
public abstract class ChunkProviderVenusSSMixin {

    @SuppressWarnings("UnresolvedMixinReference")
    @Redirect(method = "func_73154_d",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/world/chunk/Chunk;func_76605_m()[B"),
              require = 1)
    private byte[] setBiomesTweaked(Chunk chunk) {
        return BiomePatchHelper.getBiomeArrayTweaked(chunk, (i) -> GalaxySpaceHelper.GSSpace());
    }
}
