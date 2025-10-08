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
        // Just a cooler logger
        getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Config.prefix()) + message);
    }
    public static String centerMessage(String message, int length) {
        // I like the text more in the middle
        return " ".repeat((length - ChatColor.stripColor(message).length()) / 2) + message;
    }

    @Override
    public void onEnable() {
        main = this;
        // bStats metrics
        Metrics metrics = new Metrics(this, 27115);

        // Fancy Enable message with version check
        sendMessage("§a--------------------------------------§r");
        sendMessage("§a" + centerMessage("Only Proxy Plugin was enabled!", 38) + "§r");
        sendMessage("§a" + centerMessage("Paper", 38) + "§r");
        if (getServer().getVersion().contains("1.20") || getServer().getVersion().contains("1.21")) {
            sendMessage("§a" + centerMessage("Version: compatible", 38) + "§r");
        } else {
            sendMessage("§a" + centerMessage("Version:§c not compatible", 38) + "§r");
        }
        if (!GitHub.getLastedVersion("Dolfirobots", "OnlyProxy").equalsIgnoreCase(getDescription().getVersion())) {
            sendMessage("§c" + centerMessage("There is a newer version outside!", 38) + "§r");
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
        // Fancy Disable message with version checker
        sendMessage("§a--------------------------------------§r");
        sendMessage("§a" + centerMessage("Only Proxy Plugin was disabled!", 38) + "§r");
        sendMessage("§a" + centerMessage("Paper", 38) + "§r");
        if (getServer().getVersion().contains("1.20") || getServer().getVersion().contains("1.21")) {
            sendMessage("§a" + centerMessage("Version: compatible", 38) + "§r");
        } else {
            sendMessage("§a" + centerMessage("Version:§c not compatible", 38) + "§r");
        }
        if (!GitHub.getLastedVersion("Dolfirobots", "OnlyProxy").equalsIgnoreCase(getDescription().getVersion())) {
            sendMessage("§c" + centerMessage("There is a newer version outside!", 38) + "§r");
            sendMessage("§c" + centerMessage("Please check with /onlyproxy version", 38) + "§r");
        }
        sendMessage("§a--------------------------------------§r");
    }

    public static void createLog(PlayerLoginEvent event, Boolean passed) {
        if (Config.getString("log.logging").equalsIgnoreCase("OFF") || (Config.getString("log.logging").equalsIgnoreCase("OTHER") && passed)) {
            return;
        }
        Player player = event.getPlayer();

        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        // Create the log file (idk why I write two times log but it should be fine)
        // log_YYYY-MM-DD.log
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
        // Write the log file in the next free line
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            if (!lastLine.replace(" ", "").isEmpty()) {
                writer.newLine();
            }
            // Log entry message
            writer.write("[" + timestamp + "] [" + (passed ? "PASSED" : "BLOCKED") + "] " + (Config.getBoolean("log.logPlayerName") ? "Player: " + player.getName() + " | " : "") + (Config.getBoolean("log.logIPs") ? "Player IP: " + event.getAddress().getHostAddress() + " | " : "") + (Config.getBoolean("log.logProxyIPs") ? "Proxy IP: " + event.getRealAddress().getHostAddress() : ""));
        } catch (Exception e) {
            sendMessage("§cError by writing to the log file: " + e.getMessage());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        // This whole stuff here is not secure, because it only reads the ip that was send from the proxy.
        // And you must know, that such packtes can be manipulated!

        boolean passed = false;
        for (String proxyIP : Config.getList("proxyIPs")) {
            if (proxyIP.contains(":")) {
                String[] partedIP = proxyIP.split(":");
                int port;
                // Check if it is a vaild integer
                try {
                    port = Integer.parseInt(partedIP[1]);
                } catch (NumberFormatException e) {
                    sendMessage("§cInvalid port formation in your config.yml by IP: " + proxyIP);
                    continue;
                }
                // Check port with the client joined port and check the proxy ip
                int clientPort = Integer.parseInt(event.getHostname().split(":")[1]);
                if ((partedIP[0].equalsIgnoreCase(event.getRealAddress().getHostAddress()) || partedIP[0].equalsIgnoreCase(event.getRealAddress().getHostName())) && clientPort == port) {
                    passed = true;
                    break;
                }
                // Yea im new in coding
            } else if (proxyIP.equalsIgnoreCase(event.getRealAddress().getHostAddress()) || proxyIP.equalsIgnoreCase(event.getRealAddress().getHostName())) {
                passed = true;
                break;
            }
        }
        if (!passed) {
            // Formatting the kick message
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
            if (Config.getString("console.logging").equalsIgnoreCase("OTHER") && passed) return;
            // The console log message
            sendMessage("§7[" + (passed ? "§aPASSED" : "§cBLOCKED") + "§7] " + (Config.getBoolean("console.logPlayerName") ? "Player: §e" + player.getName() + "§7 | " : "") + (Config.getBoolean("console.logIPs") ? "Player IP: §e" + event.getAddress().getHostAddress() + "§7 | " : "") + (Config.getBoolean("console.logProxyIPs") ? "Proxy IP: §e" + event.getRealAddress().getHostAddress() : ""));
        }
    }

    public static Main getInstance() {
        return main;
    }
}
