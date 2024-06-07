# EndlessIDs <img src="EIDSico.png" align="right" width=150>

<sup>**[CurseForge](https://www.curseforge.com/minecraft/mc-mods/endlessids) | [Modrinth](https://modrinth.com/mod/endlessids)**</sup>

EndlessIDs is the unofficial successor of NotEnoughIDs, with more focus on compatibility, stability, and reaching
new limits.

## REQUIRED MODS
- [FalsePatternLib](https://github.com/FalsePattern/FalsePatternLib)
- [UniMixins](https://github.com/LegacyModdingMC/UniMixins)
- [ChunkAPI](https://github.com/FalsePattern/ChunkAPI)

Comparison of the known limits:

|                 | Vanilla                                            | NotEnoughIDs | EndlessIDs           |
|-----------------|----------------------------------------------------|--------------|----------------------|
| Block IDs       | 4,096                                              | 32,000       | 16,777,216 since 1.3 |
| Block Metadata  | 16                                                 | 16           | 65,536 since 1.5     |
| Item  IDs       | 32,000                                             | 32,000       | 16,777,216 since 1.3 |
| Biome IDs       | 256                                                | 256          | 65,536 since 1.4     |
| Entity IDs      | 256                                                | 256          | 32,768 since 1.5     |
| Potion IDs      | 32                                                 | 32           | 65,536 since 1.5     |
| Enchantment IDs | 256                                                | 256          | 32,768 since 1.5     |
| DataWatcher IDs | 32                                                 | Broken       | 65,536 since 1.5     |
| Dimension IDs   | Theoretically unlimited, 4,294,967,295 in practice | Same         | Same                 |

This mod also contains patches for the following mods:

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
- Compact Machines
- CoFH Lib
- Dark World
- Dimensional Doors
- DragonAPI
- Dynamic Surroundings
- Enderlicious
- Enhanced Biomes
- Erebus
- Extra Planets
- Extra Utilities
- Futurepack
- GalactiCraft
- Highlands
- Immersive Cavegen
- Industrial Revolution by Redstone Rebooted
- More Fun Quicksand Mod
- Nature's Compass
- Netherlicious
- Nomadic Tents
- Random Things
- Restructured
- Realistic Terrain Generation
- Realistic World Generation
- Thaumcraft
- The Lord of the Rings Mod: Legacy
- Tropicraft
- Twilight Forest
- Underground Biomes Constructs
- Witchery
- WorldEdit

Mods not listed here may or may not work.

- For item/block IDs:
  - A good rule of thumb is that the more invasive a mod is, the higher the chance
  it's broken without a patch once the IDs exceed 4096 and 32000.
- For biome IDs:
  - If a mod adds a custom world generator, it has a high chance of invoking an old vanilla method in the worldgen code.

    To avoid world corruption and broken biomes, this method has been replaced with an instant crash that refers the 
user to this GitHub repo.

If you find any crashes or incompatibilities, don't hesitate to open an issue.
