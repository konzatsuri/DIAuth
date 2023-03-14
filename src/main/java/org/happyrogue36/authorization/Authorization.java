//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.happyrogue36.authorization;

import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Authorization extends JavaPlugin {
    public static Data data;

    public Authorization() {
    }

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        data = new Data(this.getConfig());
        new botApp();

        try {
            SQLiteJDBC.createConnection();
        } catch (SQLException var3) {
            throw new RuntimeException(var3);
        }

        data.start(30);
    }

    public void onDisable() {
    }
}
