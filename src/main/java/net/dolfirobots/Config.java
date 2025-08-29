package net.dolfirobots;

import org.bukkit.configuration.Configuration;

import java.util.List;

public class Config {
    private static Configuration cfg = null;

    public static void reload() {
        Main.getInstance().saveDefaultConfig();
        Main.getInstance().reloadConfig();
        cfg = Main.getInstance().getConfig();
    }
    public static Configuration getConfig() {
        return Main.getInstance().getConfig();
    }
    public static String prefix() {
        String prefix = getString("prefix");
        if (getString("prefix").isEmpty() || getString("prefix") == null) prefix = "&7[&eOnlyProxy&7] ";
        return prefix;
    }
    public static String getString(String path) {
        return getConfig().getString(path);
    }
    public static Boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }
    public static List getList(String path) {
        return getConfig().getList(path);
    }
}
