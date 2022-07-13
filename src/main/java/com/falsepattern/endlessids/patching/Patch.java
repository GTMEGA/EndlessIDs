package com.falsepattern.endlessids.patching;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Patch {
    public final String modid;

    @SuppressWarnings("RedundantThrows")
    public boolean construct() throws Exception {
        return false;
    }

    @SuppressWarnings("RedundantThrows")
    public boolean preInit() throws Exception {
        return false;
    }

    @SuppressWarnings("RedundantThrows")
    public boolean init() throws Exception {
        return false;
    }

    @SuppressWarnings("RedundantThrows")
    public boolean postInit() throws Exception {
        return false;
    }
}
