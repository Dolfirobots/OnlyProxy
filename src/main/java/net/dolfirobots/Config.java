package net.dolfirobots;

import org.bukkit.configuration.Configuration;

import java.util.List;

public class Config {
    public static List<String> allowedProxyIPs = List.of("0.0.0.0");
    public static String prefix = "§c§l[PBP] §7";
    public static List<String> getKickMessage = List.of("-");

    public static String logLevel = "ALL";
    public static boolean logIPs = true;
    public static boolean logPlayerNames = true;
    public static boolean logProxyIPs = true;

    public static String consoleLogLevel = "ALL";
    public static boolean consoleLogIPs = true;
    public static boolean consoleLogPlayerNames = true;
    public static boolean consoleLogProxyIPs = true;

    public static void reloadConfig() {
        Main.getInstance().saveDefaultConfig();
        Configuration cfg = Main.getInstance().getConfig();
        allowedProxyIPs = cfg.getStringList("proxyAddress");
        if (allowedProxyIPs.isEmpty()) allowedProxyIPs = List.of("0.0.0.0");
        prefix = cfg.getString("prefix", "§c§l[PBP] §7");
        getKickMessage = cfg.getStringList("kickMessage");
        if (getKickMessage.isEmpty()) getKickMessage = List.of("%prefix%", "§r", "§r", "§cYou must you this Server over %proxy_1% or %proxy_2%!", "§7Need help? Join our Discord: discord.gg");
        logLevel = cfg.getString("log.logging", "ALL");
        logIPs = cfg.getBoolean("log.logIPs", true);
        logPlayerNames = cfg.getBoolean("log.logPlayerNames", true);
        logProxyIPs = cfg.getBoolean("log.logProxyIPs", true);

        consoleLogLevel = cfg.getString("console.logging", "ALL");
        consoleLogIPs = cfg.getBoolean("console.logIPs", true);
        consoleLogPlayerNames = cfg.getBoolean("console.logPlayerNames", true);
        consoleLogProxyIPs = cfg.getBoolean("console.logProxyIPs", true);
    }
}
