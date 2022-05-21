EndlessIDs is a modern rewrite of NotEnoughIDs which increases the limit even more, up to the maximum 24 bit unsigned integer value.

|           | Vanilla | NotEnoughIDs | EndlessIDs |
|-----------|---------|--------------|------------|
| Block IDs | 4096    | 32000        | 16777216   |
| Item  IDs | 32000   | 32000        | 16777216   |

This mod also contains patches for the following mods:

- UndergroundBiomesConstructs
- More Fun Quicksand Mod
- WorldEdit
- CoFH Lib
- DragonAPI

Mods not listed here may or may not work. A good rule of thumb is that the more invasive a mod is, the higher the chance
it's broken without a patch once the IDs exceed 4096 and 32000.

If you find any crashes or incompatibilities, don't hesitate to open an issue.

## Dependencies
- [FalsePatternLib](https://modrinth.com/mod/fplib)
- Either [SpongeMixins](https://modrinth.com/mod/spongemixin1710) or [Grimoire](https://github.com/CrucibleMC/Grimoire-legacy)
