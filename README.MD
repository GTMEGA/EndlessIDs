# EndlessIDs <img src="src/main/resources/endlessids.png" align="right" width=150>

<sup>**[CurseForge](https://www.curseforge.com/minecraft/mc-mods/endlessids) | [Modrinth](https://modrinth.com/mod/endlessids)**</sup>

EndlessIDs is the unofficial successor of NotEnoughIDs, with more focus on compatibility, stability, and reaching
new limits.

## REQUIRED MODS
- [FalsePatternLib](https://github.com/FalsePattern/FalsePatternLib)
- [UniMixins](https://github.com/LegacyModdingMC/UniMixins)
- [ChunkAPI](https://github.com/FalsePattern/ChunkAPI)

## NotEnoughIDs world compatibility
EndlessIDs can open and upgrade worlds created with NotEnoughIDs without any problems (both the original and the Unofficial version).
If you notice any problems when opening NEID worlds with EndlessIDs, please open an issue!

## Comparison of the known limits

|                 | Vanilla                                            | NotEnoughIDs | NEID Unofficial | EndlessIDs |
|-----------------|----------------------------------------------------|--------------|-----------------|------------|
| Block IDs       | 4,096                                              | 32,000       | 65,536          | 16,777,216 |
| Block Metadata  | 16                                                 | 16           | 65,536          | 65,536     |
| Item  IDs       | 32,000                                             | 32,000       | 65,536          | 16,777,216 |
| Biome IDs       | 256                                                | 256          | 256             | 65,536     |
| Entity IDs      | 256                                                | 256          | 256             | 32,768     |
| Potion IDs      | 32                                                 | 32           | 32              | 65,536     |
| Enchantment IDs | 256                                                | 256          | 256             | 32,768     |
| DataWatcher IDs | 32                                                 | 127 (Broken) | 127 (Broken)    | 65,536     |
| Dimension IDs   | Theoretically unlimited, 4,294,967,295 in practice | Same         | Same            | Same       |

## A note about biomes

Minecraft has a system known as "Biome Mutations", where a biome can have modified versions of itself at (id + 128).
Older versions of EndlessIDs used to have a system for this called the "conflict avoidance system", where it would temporarily
register placeholder biomes at these offsets to avoid conflicts.

This system has been disabled by default as it was causing weird biome registration problems, so if you want to use this
mod, we HIGHLY recommend also installing `AntiIDConflict`.

## Implementing extended metadatas

The block/item ID extension module also extends block metadatas. This allows you to use 16-bit metadata, however, you MUST
avoid the value of `32767`/`0x7FFF`/`0b0111_1111_1111_1111` for blocks that can be picked up as items, because forge treats
metadata `32767` as the OreDictionary wildcard value.

Using this specific metadata value in the world is fine if you convert it into item NBT or similar when the block is broken for pick-up.

## Mod compat

This mod also contains patches for the following mods:

<details>
<summary>Mod list</summary>

- AbyssalCraft
- Advanced Rocketry
- Alternate Terrain Generation
- Antique Atlas
- Anti ID Conflict
- ArchaicFix
- Atum
- Biomes O' Plenty
- Biome Wand
- BuildCraft
- ChromatiCraft
- Climate Control
- Compact Machines
- CoFH Lib
- Dark World
- Dimensional Doors
- DragonAPI
- Dynamic Surroundings
- Enderlicious
- Enhanced Biomes
- Erebus
- Extended Planets
- Extra Planets
- Extra Utilities
- Factorization
- Futurepack
- Gadomancy
- GalactiCraft
- Highlands
- Immersive Cavegen
- Industrial Revolution by Redstone Rebooted
- Just Another Spawner
- Matter Overdrive
- More Fun Quicksand Mod
- Mystcraft
- Nature's Compass
- Netherlicious
- Nomadic Tents
- Nostalgic World Generation
- Old World Gen
- Random Things
- Restructured
- Realistic Terrain Generation
- Realistic World Generation
- Thaumcraft
- The Lord of the Rings Mod: Legacy
- The Mists of RioV
- ThutCore/ThutMods
- Tropicraft
- Twilight Forest
- Underground Biomes Constructs
- Witchery
- WorldEdit

</details>


Mods not listed here may or may not work.

- For item/block IDs:
  - A good rule of thumb is that the more invasive a mod is, the higher the chance
  it's broken without a patch once the IDs exceed 4096 and 32000.
- For biome IDs:
  - If a mod adds a custom world generator, it has a high chance of invoking an old vanilla method in the worldgen code.

    To avoid world corruption and broken biomes, this method has been replaced with an instant crash that refers the 
user to this GitHub repo.

If you find any crashes or incompatibilities, don't hesitate to open an issue.
