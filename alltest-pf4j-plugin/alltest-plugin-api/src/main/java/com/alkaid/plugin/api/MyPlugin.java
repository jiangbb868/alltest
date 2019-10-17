package com.alkaid.plugin.api;

import ro.fortsoft.pf4j.ExtensionPoint;

public abstract  class MyPlugin implements ExtensionPoint {

    public abstract String read();

    public abstract void write(String name);

}
