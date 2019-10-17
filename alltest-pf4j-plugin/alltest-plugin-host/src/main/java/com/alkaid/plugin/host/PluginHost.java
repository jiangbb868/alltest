package com.alkaid.plugin.host;

import com.alkaid.plugin.api.ICrying;
import com.alkaid.plugin.api.IMyPlugin;
import com.alkaid.plugin.api.MyPlugin;
import ro.fortsoft.pf4j.DefaultPluginManager;
import ro.fortsoft.pf4j.PluginManager;

import java.io.File;
import java.util.List;

public class PluginHost {

    public static void main(String[] args) {
        // 使用默认的插件管理器
        //PluginManager plugManager = new DefaultPluginManager(new File("alltest-pf4j-plugin/alltest-plugin-host/target/plugins"));
        PluginManager plugManager = new DefaultPluginManager();
        // 加载插件
        plugManager.loadPlugins();
        // 启动插件
        plugManager.startPlugins();

        // 获取插件
        List<IMyPlugin> pluginList = plugManager.getExtensions(IMyPlugin.class);
        pluginList.forEach(it -> {
            System.out.println(it.read());
        });
    }
}
