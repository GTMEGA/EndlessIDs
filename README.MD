EndlessIDs is a modern rewrite of NotEnoughIDs which increases the limit even more, up to the maximum 24 bit unsigned integer value.

|           | Vanilla | NotEnoughIDs | EndlessIDs |
|-----------|---------|--------------|------------|
| Block IDs | 4096    | 32000        | 16777216   |
| Item  IDs | 32000   | 32000        | 16777216   |
| Biome IDs | 256     | 256          | 65536      |

This mod also contains patches for the following mods:

- Anti ID Conflict
- Alternate Terrain Generation
- Biomes O' Plenty
- CoFH Lib
- DragonAPI
- GalactiCraft
- More Fun Quicksand Mod
- Nature's Compass
- Realistic Terrain Generation
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