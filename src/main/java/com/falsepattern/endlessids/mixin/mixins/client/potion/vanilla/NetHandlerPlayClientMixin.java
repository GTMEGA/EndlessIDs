package com.falsepattern.endlessids.mixin.mixins.client.potion.vanilla;

import com.falsepattern.endlessids.mixin.helpers.IS1DPacketEntityEffectMixin;
import lombok.val;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mixin(NetHandlerPlayClient.class)
public abstract class NetHandlerPlayClientMixin {
    private static int theID;

    @Inject(method = "handleEntityEffect",
            at = @At("HEAD"),
            require = 1)
    private void grabID(S1DPacketEntityEffect packetIn, CallbackInfo ci) {
        theID = ((IS1DPacketEntityEffectMixin)packetIn).getIDExtended();
    }

    @Redirect(method = "handleEntityEffect",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/entity/EntityLivingBase;addPotionEffect(Lnet/minecraft/potion/PotionEffect;)V"),
              require = 1)
    private void addPotionEffect(EntityLivingBase entity, PotionEffect effect) {
        val newPotion = new PotionEffect(theID, effect.getDuration(), effect.getAmplifier());
        theID = 0;
        newPotion.setPotionDurationMax(effect.getIsPotionDurationMax());
        entity.addPotionEffect(newPotion);
    }
}
