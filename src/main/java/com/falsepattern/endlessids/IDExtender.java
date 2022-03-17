package com.falsepattern.endlessids;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.creativetab.CreativeTabs;

@Mod(modid = "notenoughIDs", name = "NotEnoughIDs", version = "1.4.3.4")
public class IDExtender
{
    public IDExtender() {
        super();
    }
    
    @Mod.EventHandler
    public void test(final FMLPreInitializationEvent event) {
        for(int i = 0; i < 31658; i++){
        	Block shield = new BlockIce().setBlockName(".shield" + i);
        	GameRegistry.registerBlock(shield, i + "ShieldBlock");
        	shield.setCreativeTab(CreativeTabs.tabFood);
        }
    }
}
