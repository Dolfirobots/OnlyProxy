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

import static org.bukkit.Bukkit.getConsoleSender;

public final class Main extends JavaPlugin implements Listener {

    public static File logFolder = new File("plugins/OnlyProxy", "logs");
    private static Main main;
    public static void sendMessage(String message) {
        getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Config.prefix()) + message);
    }
    public static String centerMessage(String message, int length) {
        int spaces = Math.max(0, (length - ChatColor.stripColor(message).length()) / 2);
        return " ".repeat(spaces) + message;
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
            sendMessage("§c" + centerMessage("There is a newer version outside!", 38) + "§r");
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
        
        boolean passed = false;
        for (String proxyIP : Config.getList("proxyIPs")) {
            if (proxyIP.contains(":")) {
                String[] partedIP = proxyIP.split(":");
                int port;
                try {
                    port = Integer.parseInt(partedIP[1]);
                } catch (NumberFormatException e) {
                    sendMessage("§cInvalid port formation in your config.yml by IP: " + proxyIP);
                    continue;
                }
                int clientPort = Integer.parseInt(event.getHostname().split(":")[1]); // WARNING: That "port forwarding" detects not perfect the port because it only checks the port that was sent by the client wich can be manipulated!
                if ((partedIP[0].equalsIgnoreCase(event.getRealAddress().getHostAddress()) || partedIP[0].equalsIgnoreCase(event.getRealAddress().getHostName())) && clientPort == port) {
                    passed = true;
                    break;
                }
            } else if (proxyIP.equalsIgnoreCase(event.getRealAddress().getHostAddress()) || proxyIP.equalsIgnoreCase(event.getRealAddress().getHostName())) {
                passed = true;
                break;
            }
        }
        if (!passed) {
            String kickMsg = ChatColor.translateAlternateColorCodes('&', String.join("\n", Config.getList("kickMessage"))).replace("%prefix%", ChatColor.translateAlternateColorCodes('&', Config.prefix()));
            int counter = 0;
            for (String address : Config.getList("proxyIPs")) {
                counter++;
                kickMsg = kickMsg.replace("%proxy_" + counter + "%", address);
            }
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, kickMsg);
        }
        createLog(event, passed);
        if (!Config.getString("console.logging").equalsIgnoreCase("OFF")) {
            if (Config.getString("console.logging").equalsIgnoreCase("OTHER") && !passed) return;
            sendMessage("§7[" + (passed ? "§aPASSED" : "§cBLOCKED") + "§7] " + (Config.getBoolean("console.logPlayerName") ? "Player: §e" + player.getName() + "§7 | " : "") + (Config.getBoolean("console.logIPs") ? "Player IP: §e" + event.getAddress().getHostAddress() + "§7 | " : "") + (Config.getBoolean("console.logProxyIPs") ? "Proxy IP: §e" + event.getRealAddress().getHostAddress() : ""));
        }
    }
    public static Main getInstance() {
        return main;
    }
}
