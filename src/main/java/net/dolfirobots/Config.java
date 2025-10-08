package net.dolfirobots;

import org.bukkit.configuration.Configuration;

import java.util.List;

public class Config {
    public static void reload() {
        Main.getInstance().saveDefaultConfig();
        Main.getInstance().reloadConfig();
    }
    public static Configuration getConfig() {
        return Main.getInstance().getConfig();
    }
    public static String prefix() {
        String prefix = getString("prefix");
        if (getString("prefix").isEmpty() || getString("prefix") == null) prefix = "&7[&eOnlyProxy&7] ";
        return prefix;
    }
    // I dont wanne talk how dump the lines under this comment are (you can easy do "Config.getConfig().getString("prefix")" for example)
    // I just want to code
    public static String getString(String path) {
        return getConfig().getString(path);
    }
    public static Boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }
    public static List<String> getList(String path) {
        return getConfig().getStringList(path);
    }
}
