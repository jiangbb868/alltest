package com.alkaid.plugin.impl;

import com.alkaid.plugin.api.IMyPlugin;
import ro.fortsoft.pf4j.Extension;

@Extension
public class PluginImpl implements IMyPlugin {

    @Override
    public String read() {
        return "read something";
    }

    @Override
    public void write(String name) {
        System.out.println("write");
    }
}
