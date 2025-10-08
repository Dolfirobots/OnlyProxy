package net.dolfirobots;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Command implements CommandExecutor, TabCompleter {

    public static void sendPlayerMessage(String message, CommandSender player) {
        // More fancier
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.prefix()) + "§7" + message);
    }

    // Command: /onlyproxy (reload/version) 
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] strings) {
        // Default message for the plugin
        if (strings.length == 0) {
            sendPlayerMessage("-----------------------------------------", commandSender);
            sendPlayerMessage(Main.centerMessage("§eOnlyProxy §7Plugin by §bDolfirobots", 41), commandSender);
            sendPlayerMessage(Main.centerMessage("Loaded version: §e" + Main.getInstance().getDescription().getVersion(), 41), commandSender);
            sendPlayerMessage(Main.centerMessage("Download it here:", 41), commandSender);
            sendPlayerMessage(Main.centerMessage("https://github.com/Dolfirobots/OnlyProxy", 41), commandSender);
            sendPlayerMessage("-----------------------------------------", commandSender);
        } else
        if (strings.length == 1) {
            
            if (strings[0].equalsIgnoreCase("reload")) {
                // Checking the player permission that no stupid player can perform the /onlyproxy reload command
                // (Idk why, because it is not very dangerous but it may can crash the server)
                if (!commandSender.hasPermission("onlyproxy.commands")) {
                    sendPlayerMessage("-----------------------------------------", commandSender);
                    sendPlayerMessage(Main.centerMessage("§cYou don't have the Permission", 41), commandSender);
                    sendPlayerMessage(Main.centerMessage("§cfor this command!", 41), commandSender);
                    sendPlayerMessage("-----------------------------------------", commandSender);
                    return true;
                }
                sendPlayerMessage("-----------------------------------------", commandSender);
                sendPlayerMessage(Main.centerMessage("§eReloading Plugin OnlyProxy...", 41), commandSender);
                Config.reload();
                for (String proxyIP : Config.getList("proxyIPs")) {
                    if (proxyIP.contains(":")) {
                        try {
                            Integer.parseInt(proxyIP.split(":")[1]);
                        } catch (NumberFormatException e) {
                            // Port was not a number :(
                            sendPlayerMessage(Main.centerMessage("§cThere was an parsing error!", 41), commandSender);
                            sendPlayerMessage(Main.centerMessage("§cPlease check the console!", 41), commandSender);
                            Main.sendMessage("§cInvalid port formation in your config.yml by IP: " + proxyIP);
                        }
                    }
                }
                sendPlayerMessage("§a" + Main.centerMessage("Reloaded!", 41), commandSender);
                sendPlayerMessage("-----------------------------------------", commandSender);
            } else
            if (strings[0].equalsIgnoreCase("version")) {
                // Also a little bit useless permission system
                if (!commandSender.hasPermission("onlyproxy.commands")) {
                    sendPlayerMessage("-----------------------------------------", commandSender);
                    sendPlayerMessage("§c" + Main.centerMessage("You don't have the Permission", 41), commandSender);
                    sendPlayerMessage("§c" + Main.centerMessage("for this command!", 41), commandSender);
                    sendPlayerMessage("-----------------------------------------", commandSender);
                    return true;
                }

                sendPlayerMessage("-----------------------------------------", commandSender);
                sendPlayerMessage(Main.centerMessage("§eFetching last version...", 41), commandSender);
                // Get the plugin version from GitHub (uh it is very easy to see that xD) 
                String lastedVersion = GitHub.getLastedVersion("Dolfirobots", "OnlyProxy");

                if (lastedVersion.equalsIgnoreCase(Main.getInstance().getDescription().getVersion())) {
                    sendPlayerMessage(Main.centerMessage("Your version: §a" + Main.getInstance().getDescription().getVersion() + "§7 == §a" + lastedVersion, 41), commandSender);
                    sendPlayerMessage("§a" + Main.centerMessage("OnlyProxy is up to date! (:", 41), commandSender);

                } else if (!lastedVersion.equalsIgnoreCase("unknown")) {
                    sendPlayerMessage(Main.centerMessage("Your version: §c" + Main.getInstance().getDescription().getVersion() + "§7 => §a" + lastedVersion, 41), commandSender);
                    sendPlayerMessage("§c" + Main.centerMessage("OnlyProxy is not up to date! ):", 41), commandSender);
                    ArrayList<String> versions = GitHub.getAllVersions("Dolfirobots", "OnlyProxy");

                    ArrayList<Integer> removeVersionIndex = new ArrayList<>();
                    int currentIndex = 0;
                    // IM NEW IN JAVA (complicated but it work)
                    for (String version : versions) {
                        if (version.equalsIgnoreCase(Main.getInstance().getDescription().getVersion())) {
                            break;
                        }
                        removeVersionIndex.add(currentIndex);
                        currentIndex++;
                    }
                    for (int i = removeVersionIndex.size() - 1; i >= 0; i--) {
                        versions.remove((int) removeVersionIndex.get(i));
                    }

                    sendPlayerMessage(Main.centerMessage("You are §e" + versions.size() + "§7 versions behind!", 41), commandSender); // Yay it is so cool with the "You are ... versions behind!"
                    sendPlayerMessage(Main.centerMessage("Download it here:", 41), commandSender);
                    sendPlayerMessage(Main.centerMessage("https://github.com/Dolfirobots/OnlyProxy/", 41), commandSender);
                } else {
                    // No connection error
                    sendPlayerMessage(Main.centerMessage("Your version: " + Main.getInstance().getDescription().getVersion(), 41), commandSender);
                    sendPlayerMessage(Main.centerMessage("§eWe couldn't check the lasted version!", 41), commandSender);
                    sendPlayerMessage(Main.centerMessage("Please check the internet", 41), commandSender);
                    sendPlayerMessage(Main.centerMessage("connection from your Server.", 41), commandSender);
                }
                sendPlayerMessage("-----------------------------------------", commandSender);
            } else {
                // Help command if the sub command is not handled
                sendPlayerMessage("-----------------------------------------", commandSender);
                if (commandSender.hasPermission("onlyproxy.commands")) {
                    sendPlayerMessage(Main.centerMessage("§cUsage: §e/" + s + " [reload/version]", 41), commandSender);
                } else {
                    sendPlayerMessage(Main.centerMessage("§cUsage: §e/" + s, 41), commandSender);
                }
                sendPlayerMessage("-----------------------------------------", commandSender);
            }
        } else {
            // Yea its me, a dummy programmer
            sendPlayerMessage("-----------------------------------------", commandSender);
            if (commandSender.hasPermission("onlyproxy.commands")) {
                sendPlayerMessage(Main.centerMessage("§cUsage: §e/" + s + " [reload/version]", 41), commandSender);
            } else {
                sendPlayerMessage(Main.centerMessage("§cUsage: §e/" + s, 41), commandSender);
            }
            sendPlayerMessage("-----------------------------------------", commandSender);
        }
        return true;
    }
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] strings) {
        // Let me do TAB
        List<String> list = new ArrayList<>();
        if (commandSender.hasPermission("onlyproxy.commands")) {
            if (strings.length == 1) {
                list.add("reload");
                list.add("version");
            }
        }
        return list;
    }
}
