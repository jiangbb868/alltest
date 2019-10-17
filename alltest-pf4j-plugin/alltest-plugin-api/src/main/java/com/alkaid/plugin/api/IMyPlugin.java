package com.alkaid.plugin.api;


import ro.fortsoft.pf4j.ExtensionPoint;

public interface IMyPlugin extends ExtensionPoint {

    public String read();
    public void write(String name);
}
