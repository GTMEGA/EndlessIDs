package com.falsepattern.endlessids.asm;

import com.falsepattern.endlessids.asm.stubpackage.cpw.mods.fml.common.registry.GameData;
import com.falsepattern.endlessids.util.WeakIdentityHashMap;
import lombok.var;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.WeakHashMap;

//Used by ASM
@SuppressWarnings("unused")
public class GameDataHooks {
    private static WeakIdentityHashMap<Block, WeakReference<ItemBlock>> forGameData(GameData gameData) {
        if (gameData.endlessIDsItemBlockCache == null) {
            gameData.endlessIDsItemBlockCache = new WeakIdentityHashMap<>();
        }
        return gameData.endlessIDsItemBlockCache;
    }

    public static void cacheItemBlock(ItemBlock itemBlock, GameData gameData) {
        forGameData(gameData).put(itemBlock.field_150939_a, new WeakReference<>(itemBlock));
    }

    public static ItemBlock fetchCachedItemBlock(Block block, GameData gameData) {
        var weakItem = forGameData(gameData).get(block);
        if (weakItem != null) {
            return weakItem.get();
        }
        return null;
    }

    public static Iterable<Item> fakeIterable() {
        return Collections.emptyList();
    }
}
