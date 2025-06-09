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
| DataWatcher IDs | 32                                                 | 127 (Broken) | 127             | 65,536     |
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
- BlockPhysics
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
- FastCraft
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
- Ruins
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

## Depending On Metadata Increase In Another Mod

**NOTE:** This API is available as of version TODO of EndlessIDs.

EndlessIDs implements the common ID Extender API introduced in NotEnoughIDs Unofficial. See:
https://github.com/GTNewHorizons/NotEnoughIds/pull/17

The description below is an excerpt of NEID-U's readme concerning this API:

In order to use the metadata, you should not create a hard dependency on the `endlessids` Mod ID, but rather on the `idextender` Mod ID.
EndlessIDs bundles this "dummy" mod alongside itself.

Here is an example of how to use it, you could put this code in your Mod's pre-init for example:

```java
import idextender;

int maxMeta = 15;

if (Loader.isModLoaded("idextender")) {
    maxMeta = (Integer) Class.forName("idextender.Metadata").getDeclaredMethod("maximumMetadata").invoke(null);
}
```

You can also query for the number of bits the metadata value is extended to, rather than the maximum value:

```java
import idextender;

int metadataBits = 4;

if (Loader.isModLoaded("idextender")){
    metadataBits = (Integer) Class.forName("idextender.Metadata").getDeclaredMethod("metadataBits").invoke(null);
}
```

The `idextender.DataWatcher` class also exposes the extended DataWatcher count (used for entity data syncing over network)
in a similar fashion.

Note that these values CAN be identical to the vanilla values if the ID Extension mod implementing it has a config to
toggle itself, existence of the IDExtender class does not guarantee a fixed increase, and you should check that the
returned value is large enough for your needs. 

If for some reason, you need to know what parent mod is providing the `idextender` mod, you can do the following, which will give you
back the Mod ID of the actual mod that has provided `idextender`. For example, `endlessids`:

```java
// This will give back the value "endlessids" which is EndlessIDs' Mod ID
String parentIDExtender = (String) Class.forName("idextender.IDExtender").getDeclaredMethod("parentModId").invoke(null);
```

For more information, see the NotEnoughIDs readme.