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

    public static void sendMessage(String message) {
        getConsoleSender().sendMessage(Config.prefix + message);
    }

    @Override
    public void onEnable() {
        sendMessage("§a--------------------------------------§r");
        sendMessage("§a     Only Proxy Plugin was enabled!   §r");
        sendMessage("§a                 Paper                §r");
        if (getServer().getVersion().contains("1.20") || getServer().getVersion().contains("1.21")) {
            sendMessage("§a         Version: compatible          §r");
        } else {
            sendMessage("§a       Version:§c not compatible§a      §r");
        }
        sendMessage("§a                                      §r");
        sendMessage("§a--------------------------------------§r");
        Config.reloadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("onlyproxy").setExecutor(new Command());
        getCommand("onlyproxy").setTabCompleter(new Command());
        if (!Config.logLevel.equalsIgnoreCase("OFF")) {
            if (!logFolder.exists()) {
                if (!logFolder.mkdir()) {
                    sendMessage("§cError by creating the logs folder!");
                }
            }
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(new Date());
                File logFile = new File(logFolder, "log_" + dateString + ".log");
                if (!logFile.exists()) {
                    if (!logFile.createNewFile()) {
                        sendMessage("§cError by creating the log file");
                    }
                }
            } catch (IOException e) {
                sendMessage("§cError by creating the log file: " + e.getMessage());
            }
        }
    }
    @Override
    public void onDisable() {
        sendMessage("§a--------------------------------------§r");
        sendMessage("§a     Only Proxy Plugin was disabled!  §r");
        sendMessage("§a                 Paper                §r");
        if (getServer().getVersion().contains("1.20") || getServer().getVersion().contains("1.21")) {
            sendMessage("§a         Version: compatible          §r");
        } else {
            sendMessage("§a     Version:§c not compatible§a      §r");
        }
        sendMessage("§a                                      §r");
        sendMessage("§a--------------------------------------§r");
    }


    public static void createLog(PlayerLoginEvent event, Boolean success) {
        if (Config.logLevel.equalsIgnoreCase("OFF") || (Config.logLevel.equalsIgnoreCase("OTHER") && !success)) {
            return;
        }
        String playerName = "Playername: " + event.getPlayer().getName();
        String playerIP = "Player IP: " + event.getRealAddress();
        String proxyIP = "Proxy IP: " + event.getRealAddress().getHostAddress();
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        File logFile = new File(logFolder, "log_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log");
        if (!logFile.getParentFile().mkdirs()) {
            sendMessage("§cError by creating the logs folder!");
            return;
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
            writer.write( "[" + timestamp + "] [" + (success ? "PASSED" : "BLOCKED") + "] " + (Config.logPlayerNames ? playerName + " | " : "") + (Config.logIPs ? playerIP + " | " : "") + (Config.logProxyIPs ? proxyIP : ""));
        } catch (IOException e) {
            sendMessage("§cError by writing to the log file: " + e.getMessage());
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        String joinedProxyAddress = event.getRealAddress().getHostAddress();
        String joinedProxyHost = event.getRealAddress().getHostName();
        boolean passed = false;
        if (Config.allowedProxyIPs.contains(joinedProxyHost) || Config.allowedProxyIPs.contains(joinedProxyAddress)) {
            passed = true;
        } else {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', String.join("\n", Config.getKickMessage)));
        }
        createLog(event, passed);
        if (!Config.consoleLogLevel.equalsIgnoreCase("OFF")) {
            if (Config.consoleLogLevel.equalsIgnoreCase("OTHER") && !passed) return;
            sendMessage("§7[" + (passed ? "§aPASSED" : "§cBLOCKED") + "§7] " + (Config.consoleLogPlayerNames ? "Player: §e" + player.getName() + "§7 | " : "") + (Config.consoleLogIPs ? "Player IP: §e" + joinedProxyHost + "§7 | " : "") + (Config.consoleLogProxyIPs ? "Proxy IP: §e" + joinedProxyAddress : ""));
        }
    }
    public static Main getInstance() {
        return JavaPlugin.getPlugin(Main.class);
    }
}
