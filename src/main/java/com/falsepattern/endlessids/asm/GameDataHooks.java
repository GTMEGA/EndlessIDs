package com.falsepattern.endlessids.asm;

import lombok.var;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.WeakHashMap;

public class GameDataHooks {
    private static final WeakHashMap<Block, WeakReference<ItemBlock>> itemBlockCache = new WeakHashMap<>();

    public static void cacheItemBlock(ItemBlock itemBlock) {
        itemBlockCache.put(itemBlock.field_150939_a, new WeakReference<>(itemBlock));
    }

    public static ItemBlock fetchCachedItemBlock(Block block) {
        var weakItem = itemBlockCache.get(block);
        if (weakItem != null) {
            return weakItem.get();
        }
        return null;
    }

    public static Iterable<Item> fakeIterable() {
        return Collections.emptyList();
    }
}
