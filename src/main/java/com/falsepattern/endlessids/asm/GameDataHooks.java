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
