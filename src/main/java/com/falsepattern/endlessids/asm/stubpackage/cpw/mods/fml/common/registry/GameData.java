package com.falsepattern.endlessids.asm.stubpackage.cpw.mods.fml.common.registry;

import com.falsepattern.endlessids.util.WeakIdentityHashMap;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

import java.lang.ref.WeakReference;

public class GameData {
    public WeakIdentityHashMap<Block, WeakReference<ItemBlock>> endlessIDsItemBlockCache;
}
