package net.dolfirobots;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.bukkit.Bukkit.getConsoleSender;

public final class Main extends JavaPlugin implements Listener {

    public static File logFolder = new File("plugins/OnlyProxy", "logs");

    private static Main main;

    public static void sendMessage(String message) {
        getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Config.prefix()) + message);
    }
    public static String centerMessage(String message, int length) {
        int msgLength = ChatColor.stripColor(message).length();
        int spaces = (length - msgLength) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < spaces; i++) sb.append(" ");
        sb.append(message);
        return sb.toString();
    }

    @Override
    public void onEnable() {
        main = this;
        sendMessage("§a--------------------------------------§r");
        sendMessage("§a" + centerMessage("Only Proxy Plugin was enabled!", 38) + "§r");
        sendMessage("§a" + centerMessage("Paper", 38) + "§r");
        if (getServer().getVersion().contains("1.20") || getServer().getVersion().contains("1.21")) {
            sendMessage("§a" + centerMessage("Version: compatible", 38) + "§r");
        } else {
            sendMessage("§a" + centerMessage("Version:§c not compatible", 38) + "§r");
        }
        String lastedVersion = GitHub.getLastedReleaseVersion("Dolfirobots", "OnlyProxy");
        if (!lastedVersion.equalsIgnoreCase(getDescription().getVersion())) {
            sendMessage("§c" + centerMessage("There is a new version outside!", 38) + "§r");
            sendMessage("§c" + centerMessage("Please check with /onlyproxy version", 38) + "§r");
        }
        sendMessage("§a--------------------------------------§r");

        Config.reload();

        getServer().getPluginManager().registerEvents(this, this);
        getCommand("onlyproxy").setExecutor(new Command());
        getCommand("onlyproxy").setTabCompleter(new Command());

        if (!Config.getString("log.logging").equalsIgnoreCase("OFF")) {
            if (!logFolder.exists()) {
                if (!logFolder.mkdir()) {
                    sendMessage("§cError by creating the logs folder!");
                }
            }
        }
    }
    @Override
    public void onDisable() {
        sendMessage("§a--------------------------------------§r");
        sendMessage("§a" + centerMessage("Only Proxy Plugin was disabled!", 38) + "§r");
        sendMessage("§a" + centerMessage("Paper", 38) + "§r");
        if (getServer().getVersion().contains("1.20") || getServer().getVersion().contains("1.21")) {
            sendMessage("§a" + centerMessage("Version: compatible", 38) + "§r");
        } else {
            sendMessage("§a" + centerMessage("Version:§c not compatible", 38) + "§r");
        }
        String lastedVersion = GitHub.getLastedReleaseVersion("Dolfirobots", "OnlyProxy");
        if (!lastedVersion.equalsIgnoreCase(getDescription().getVersion())) {
            sendMessage("§c" + centerMessage("There is a new version outside!", 38) + "§r");
            sendMessage("§c" + centerMessage("Please check with /onlyproxy version", 38) + "§r");
        }
        sendMessage("§a--------------------------------------§r");
    }

    public static void createLog(PlayerLoginEvent event, Boolean passed) {
        if (Config.getString("log.logging").equalsIgnoreCase("OFF") || (Config.getString("log.logging").equalsIgnoreCase("OTHER") && !passed)) {
            return;
        }
        Player player = event.getPlayer();

        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());

        File logFile = new File(logFolder, "log_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log");
        if (!logFolder.exists() && !logFile.getParentFile().mkdirs()) {
            sendMessage("§cError by creating the logs folder!");
            return;
        }
        try {
            if (!logFile.exists() && !logFile.createNewFile()) {
                sendMessage("§cError by creating the logs file!");
            }
        } catch (Exception e) {
            sendMessage("§cError by creating the logs file: " + e.getMessage());
        }
        String lastLine = "";

        try (BufferedReader obj = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = obj.readLine()) != null) {
                lastLine = line;
            }
        } catch (IOException e) {
            sendMessage("§cError by reading logs file: " + e.getMessage());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            if (!lastLine.replace(" ", "").isEmpty()) {
                writer.newLine();
            }
            writer.write("[" + timestamp + "] [" + (passed ? "PASSED" : "BLOCKED") + "] " + (Config.getBoolean("console.logPlayerName") ? "Player: " + player.getName() + " | " : "") + (Config.getBoolean("console.logIPs") ? "Player IP: " + event.getAddress().getHostAddress() + " | " : "") + (Config.getBoolean("console.logProxyIPs") ? "Proxy IP: " + event.getRealAddress().getHostAddress() : ""));
        } catch (Exception e) {
            sendMessage("§cError by writing to the log file: " + e.getMessage());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        String joinedProxyAddress = event.getRealAddress().getHostAddress();
        String joinedProxyHost = event.getRealAddress().getHostName();
        boolean passed = false;
        if (Config.getList("proxyIPs").contains(joinedProxyHost) || Config.getList("proxyIPs").contains(joinedProxyAddress)) {
            passed = true;
        } else {
            String kickMsg = ChatColor.translateAlternateColorCodes('&', String.join("\n", (List<String>) Config.getList("kickMessage"))).replace("%prefix%", ChatColor.translateAlternateColorCodes('&', Config.prefix()));
            int counter = 0;
            for (String address : (List<String>) Config.getList("proxyIPs")) {
                counter++;
                kickMsg = kickMsg.replace("%proxy_" + counter + "%", address);
            }
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, kickMsg);
        }
        createLog(event, passed);
        if (!Config.getString("console.logging").equalsIgnoreCase("OFF")) {
            if (Config.getString("console.logging").equalsIgnoreCase("OTHER") && !passed) return;
            sendMessage("§7[" + (passed ? "§aPASSED" : "§cBLOCKED") + "§7] " + (Config.getBoolean("console.logPlayerName") ? "Player: §e" + player.getName() + "§7 | " : "") + (Config.getBoolean("console.logIPs") ? "Player IP: §e" + event.getAddress().getHostAddress() + "§7 | " : "") + (Config.getBoolean("console.logProxyIPs") ? "Proxy IP: §e" + joinedProxyAddress : ""));
        }
    }
    public static Main getInstance() {
        return main;
    }
}
