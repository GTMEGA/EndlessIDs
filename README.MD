EndlessIDs is the unofficial successor of NotEnoughIDs, with more focus on compatibility, stability, and reaching
new limits.

Comparison of the known limits:

|                 | Vanilla                                            | NotEnoughIDs | EndlessIDs                       |
|-----------------|----------------------------------------------------|--------------|----------------------------------|
| Block IDs       | 4,096                                              | 32,000       | 16,777,216                       |
| Item  IDs       | 32,000                                             | 32,000       | 16,777,216                       |
| Biome IDs       | 256                                                | 256          | 65,536                           |
| Entity IDs      | 256                                                | 256          | 256 right now, extension planned |
| Potion IDs      | 32                                                 | 32           | 32 right now, extension planned  |
| Enchantment IDs | 256                                                | 256          | 256 right now, extension planned |
| Dimension IDs   | Theoretically unlimited, 4,294,967,295 in practice | Same         | Same                             |

This mod also contains patches for the following mods:

- Alternate Terrain Generation
- Anti ID Conflict
- ArchaicFix
- Biomes O' Plenty
- CoFH Lib
- DragonAPI
- GalactiCraft
- Immersive Cavegen
- More Fun Quicksand Mod
- Nature's Compass
- Realistic Terrain Generation
- Thaumcraft
- Twilight Forest
- Underground Biomes Constructs
- WorldEdit

Mods not listed here may or may not work.

- For item/block IDs:
  - A good rule of thumb is that the more invasive a mod is, the higher the chance
  it's broken without a patch once the IDs exceed 4096 and 32000.
- For biome IDs:
  - If a mod adds a custom world generator, it has a high chance of invoking an old vanilla method in the worldgen code.

    To avoid world corruption and broken biomes, this method has been replaced with an instant crash that refers the 
user to this github repo.

If you find any crashes or incompatibilities, don't hesitate to open an issue.

## Dependencies
- [FalsePatternLib](https://github.com/FalsePattern/FalsePatternLib)
- [SpongeMixins](https://github.com/TimeConqueror/SpongeMixins)

[![POOP badge](https://raw.githubusercontent.com/gist/poop-person/991e80f390384bbeef09d208bff208f4/raw/a9ef83add84a70f2202896c2d81117ff7b169be1/poop-badge.svg)](https://gist.github.com/poop-person/991e80f390384bbeef09d208bff208f4)