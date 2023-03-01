package com.falsepattern.endlessids.containers;

import com.falsepattern.endlessids.Tags;
import com.google.common.eventbus.EventBus;
import lombok.val;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;

public class _Dummy extends DummyModContainer {
    public _Dummy(String id, String name) {
        super(createMetadata(id, name));
    }

    private static ModMetadata createMetadata(String id, String name) {
        val meta = new ModMetadata();
        meta.modId = Tags.MODID + "_" + id;
        meta.name = Tags.MODNAME + " " + name + " Module";
        meta.version = Tags.VERSION;
        meta.dependencies.add(new DefaultArtifactVersion(Tags.MODID, Tags.VERSION));
        meta.parent = Tags.MODID;
        return meta;
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        return true;
    }
}
