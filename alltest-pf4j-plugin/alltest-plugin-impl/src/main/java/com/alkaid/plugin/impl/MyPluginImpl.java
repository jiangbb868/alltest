package com.alkaid.plugin.impl;

import com.alkaid.plugin.api.ICrying;
import com.alkaid.plugin.api.IMyPlugin;
import com.alkaid.plugin.api.MyPlugin;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

public class MyPluginImpl extends Plugin {

    public MyPluginImpl(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println("plugin start");
        System.out.println("IMyPlugin is assignable from PluginImpl : " + IMyPlugin.class.isAssignableFrom(PluginImpl.class));
    }

    @Override
    public void stop() {
        System.out.println("plugin stop");
    }

    @Extension
    public static class PluginImpl implements IMyPlugin {
        @Override
        public String read() {
            return "read something";
        }
        @Override
        public void write(String name) {
            System.out.println("write");
        }
    }

    @Extension
    public static class MyCrying implements ICrying {

        @Override
        public String cry() {
            return "read something";
        }

    }
    /*
    @Extension
    public static class NewPlugin extends MyPlugin {

        @Override
        public String read() {
            return "read something";
        }

        @Override
        public void write(String name) {
            System.out.println("write");
        }
    }
    */
}
