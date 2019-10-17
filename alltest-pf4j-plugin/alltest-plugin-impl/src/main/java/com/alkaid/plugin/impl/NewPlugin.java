package com.alkaid.plugin.impl;

import com.alkaid.plugin.api.MyPlugin;
import ro.fortsoft.pf4j.Extension;

@Extension
public class NewPlugin extends MyPlugin {

    public String read() {
        return "read something";
    }

    public void write(String name) {
        System.out.println("write");
    }
}
