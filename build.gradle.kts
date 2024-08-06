plugins {
    id("fpgradle-minecraft") version ("0.7.0")
}

group = "com.falsepattern"

minecraft_fp {
    mod {
        modid = "endlessids"
        name = "EndlessIDs"
        rootPkg = "$group.endlessids"
    }

    mixin {
        pkg = "mixin.mixins"
        pluginClass = "mixin.plugin.MixinPlugin"
    }

    core {
        coreModClass = "asm.EndlessIDsCore"
    }

    tokens {
        tokenClass = "Tags"
        modid = "MODID"
        name = "MODNAME"
        version = "VERSION"
        rootPkg = "GROUPNAME"
    }

    publish {
        changelog = "https://github.com/GTMEGA/EndlessIDs/releases/tag/{version}"
        maven {
            repoUrl  = "https://mvn.falsepattern.com/releases/"
            repoName = "mavenpattern"
        }
        curseforge {
            projectId = "665730"
            dependencies {
                required("fplib")
                required("chunkapi")
                incompatible("notenoughids-unofficial-1-7-10")
                incompatible("notenoughids")
            }
        }
        modrinth {
            projectId = "qGHY4QGo"
            dependencies {
                required("fplib")
                required("chunkapi")
                incompatible("notenoughids-unofficial")
            }
        }
    }
}

repositories {
    cursemavenEX()
    exclusive(mavenpattern(), "com.falsepattern")
    exclusive(ivy("https://mvn.falsepattern.com/releases/mirror/", "[orgPath]/[artifact]-[revision].[ext]"), "mirror", "mirror.micdoodle")
    exclusive(ivy("https://github.com/", "[orgPath]/releases/download/[revision]/[artifact]-[revision].[ext]"), "CannibalVox.DimDoors")
}

dependencies {
    ////////////////Our dependencies////////////////
    implementationSplit("com.falsepattern:falsepatternlib-mc1.7.10:1.4.0")
    implementation("it.unimi.dsi:fastutil:8.5.13")
    implementationSplit("com.falsepattern:chunkapi-mc1.7.10:0.5.1")

    ////////////////Patched mods////////////////

    //AbyssalCraft 1.9.1.3-FINAL
    compileOnly(deobfCurse("abyssalcraft-53686:2311135"))

    //Advanced Rocketry
    compileOnly(deobfCurse("advancedrocketry-236542:2985057"))

    //Alternate Terrain Generation 0.12.0
    compileOnly(deobfCurse("atg-228356:2231155"))

    //Antique Atlas 4.4.4
    compileOnly(deobfCurse("antiqueatlas-227795:2454232"))

    //Anti ID Conflict 1.3.5
    compileOnly(deobfCurse("antiidconflict-246288:2308279"))

//    //ArchaicFix 0.2.0
//    compileOnly("com.github.embeddedt:ArchaicFix:0.2.0:dev")

    //Atum 0.6.77
    compileOnly(deobfCurse("atum-59621:2256356"))

    //Biomes O' Plenty 2.1.0.2308
    compileOnly(deobfCurse("biomesoplenty-220318:2499612"))

    //Biome Tweaker 2.0.182
    compileOnly(deobfCurse("biometweaker-228895:2279911"))

    //Biome Wand 1.1.9
    //Source: https://spacechase0.com/wp-content/uploads/2014/10/1.7.10-Biome-Wand-1.1.9.jar
    compileOnly(rfg.deobf("mirror:1.7.10-Biome-Wand:1.1.9"))

    //Buildcraft 7.1.24
    compileOnly(deobfCurse("buildcraft-61811:3538651"))

    //CoFH Lib 1.2.1-185
    compileOnly(deobfCurse("cofh-lib-220333:2388748"))

    //CompactMachines 1.21
    compileOnly(deobfCurse("compactmachines-224218:2268575"))

    //Dark World 0.2
    compileOnly(deobfCurse("darkworld-225075:2225172"))

    //DimDoors 2.2.5-test9
    //Source: https://github.com/CannibalVox/DimDoors/releases/download/2.2.5-test9/DimensionalDoors-2.2.5-test9.jar
    compileOnly(rfg.deobf("CannibalVox.DimDoors:DimensionalDoors:2.2.5-test9"))

    //DragonAPI V32a
    compileOnly(deobfCurse("dragonapi-235591:4611379"))

    //Dynamic Surroundings 1.0.6.4
    compileOnly(deobfCurse("dynamicsurrounding-238891:2642381"))

    //Enderlicious 1.1.2
    compileOnly(deobfCurse("enderlicious-508777:3504111"))

    //Enhanced Biomes 2.5
    compileOnly(rfg.deobf("mirror:enhancedbiomes:2.5"))

    //Erebus 0.4.7
    compileOnly(deobfCurse("theerebus-220698:2305035"))

    //Extra Planets 2.1.4
    compileOnly(deobfCurse("extraplanets-241291:3214413"))

    //Extra Utilities 1.2.12
    compileOnly(deobfCurse("extrautilities-225561:2264384"))

    //Futurepack v18.7
    compileOnly(deobfCurse("futurepack-237333:2263966"))

    //Galacticraft Core 3.0.12.504
    //micdoodle8.com went down
    compileOnly(rfg.deobf("mirror.micdoodle:GalacticraftCore:1.7-3.0.12.504"))

    //Galacticraft Planets 3.0.12.504
    compileOnly(rfg.deobf("mirror.micdoodle:Galacticraft-Planets:1.7-3.0.12.504"))

    //Highlands 2.2.3
    compileOnly(deobfCurse("highlands-221226:2227924"))

    //Immersive Cavegen 1.2g
    compileOnly(deobfCurse("immersivecavegen-521557:3649411"))

    //Industrial Revolution by Redstone Rebooted 1.1.1
    compileOnly(deobfCurse("industrialrevolutionbyredstonerebooted-241812:2538035"))

    //LOTR Mod 36.15
    compileOnly(deobfCurse("lotrmod-423748:4091561"))

    //Matter Overdrive 0.4.2-hotfix1
    compileOnly(deobfCurse("matteroverdrive-229694:2331162"))

    //More Fun Quicksands Mod 1.0.8.8-fixed
    compileOnly(deobfCurse("more-fun-quicksand-mod-321960:2717318"))

    //Nature's Compass 1.3.1
    compileOnly(deobfCurse("naturescompass-252848:2451823"))

    //Netherlicious 3.2.8
    compileOnly(deobfCurse("netherlicious-385860:4363437"))

    //Nomadic Tents 1.12.1
    compileOnly(deobfCurse("nomadictents-238794:2679476"))

    //Nostalgic World Generation 1.0.0
    compileOnly(deobfCurse("nostalgiagenerator-927053:4816167"))

    //Hbm's Nuclear Tech Mod 1.0.27X5027
    compileOnly(deobfCurse("hbm-ntm-235439:5534354"))

    //RandomThings 2.2.4
    compileOnly(deobfCurse("randomthings-59816:2225310"))

    //Realistic Terrain Generation 1.1.1.7
    compileOnly(deobfCurse("rtg-237989:2521557"))

    //Restructured 1.0.4.2
    compileOnly(deobfCurse("restructured-230570:2276784"))

    //Thaumcraft 4.2.3.5
    compileOnly(deobfCurse("thaumcraft-223628:2227552"))

    //ThutCore 2.0
    //Only available on dropbox
    compileOnly(rfg.deobf("mirror:thutcore:2.0"))

    //Tropicraft 6.0.5
    //This is from a mirror file, because the file on curseforge is a zip containing other files too.
    //Source: https://www.curseforge.com/minecraft/mc-mods/tropicraft/files/2353906
    compileOnly(rfg.deobf("mirror:tropicraft:6.0.5"))

    //Twilight Forest 2.3.8
    compileOnly(deobfCurse("twilightforest-227639:3039937"))

    //Underground Biomes Constructs 0.8-beta49
    compileOnly(deobfCurse("undergroundbiomesconstructs-72744:2304497"))

    //Witchery 0.24.1
    compileOnly(deobfCurse("witchery-69673:2234410"))

    //WorldEdit 6.1.1
    compileOnly(deobfCurse("worldedit-225608:2309699"))

    ////////////////Dependencies for patched mods////////////////

    //SpaceCore 0.7.14
    //needed by: Biome Wand
    //Source: http://spacechase0.com/files/mcmod/1.7.10-SpaceCore-0.7.14.jar
    compileOnly(rfg.deobf("mirror:1.7.10-SpaceCore:0.7.14"))
}