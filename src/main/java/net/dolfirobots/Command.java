package net.dolfirobots;

import org.bukkit.Bukkit;
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
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.prefix()) + "§7" + message);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 0) {
            sendPlayerMessage("-----------------------------------------", commandSender);
            sendPlayerMessage("§e" + Main.centerMessage("OnlyProxy §7Plugin by §bDolfirobots", 41), commandSender);
            sendPlayerMessage(Main.centerMessage("Loaded version: §e" + Main.getInstance().getDescription().getVersion(), 41), commandSender);
            sendPlayerMessage(Main.centerMessage("             Download it here:           ", 41), commandSender);
            sendPlayerMessage(Main.centerMessage("https://github.com/Dolfirobots/OnlyProxy", 41), commandSender);
            sendPlayerMessage("-----------------------------------------", commandSender);
        } else
        if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("reload")) {
                if (!commandSender.hasPermission("onlyproxy.commands")) {
                    sendPlayerMessage("-----------------------------------------", commandSender);
                    sendPlayerMessage("§c" + Main.centerMessage("You don't have the Permission", 41), commandSender);
                    sendPlayerMessage("§c" + Main.centerMessage("for this command!", 41), commandSender);
                    sendPlayerMessage("-----------------------------------------", commandSender);
                    return true;
                }
                sendPlayerMessage("-----------------------------------------", commandSender);
                sendPlayerMessage("§e" + Main.centerMessage("Reloading Plugin OnlyProxy...", 41), commandSender);
                Config.reload();
                for (String proxyIP : Config.getList("proxyIPs")) {
                    if (proxyIP.contains(":")) {
                        try {
                            Integer.parseInt(proxyIP.split(":")[1]);
                        } catch (NumberFormatException e) {
                            sendPlayerMessage("§c" + Main.centerMessage("There was an parsing error!", 41), commandSender);
                            sendPlayerMessage("§c" + Main.centerMessage("Please check the console!", 41), commandSender);
                            Main.sendMessage("§cInvalid port formation in your config.yml by IP: " + proxyIP);
                        }
                    }
                }
                sendPlayerMessage("§a" + Main.centerMessage("Reloaded!", 41), commandSender);
                sendPlayerMessage("-----------------------------------------", commandSender);
            } else
            if (strings[0].equalsIgnoreCase("version")) {
                if (!commandSender.hasPermission("onlyproxy.commands")) {
                    sendPlayerMessage("-----------------------------------------", commandSender);
                    sendPlayerMessage("§c" + Main.centerMessage("You don't have the Permission", 41), commandSender);
                    sendPlayerMessage("§c" + Main.centerMessage("for this command!", 41), commandSender);
                    sendPlayerMessage("-----------------------------------------", commandSender);
                    return true;
                }

                sendPlayerMessage("-----------------------------------------", commandSender);
                sendPlayerMessage("§e" + Main.centerMessage("Fetching last version...", 41), commandSender);

                String lastedVersion = GitHub.getLastedReleaseVersion("Dolfirobots", "OnlyProxy");

                if (lastedVersion.equalsIgnoreCase(Main.getInstance().getDescription().getVersion())) {
                    sendPlayerMessage("Your version: §a" + Main.getInstance().getDescription().getVersion() + "§7 == §a" + lastedVersion, commandSender);
                    sendPlayerMessage("§a" + Main.centerMessage("OnlyProxy is up to date! (:", 41), commandSender);

                } else if (!lastedVersion.equalsIgnoreCase("unknown")) {
                    sendPlayerMessage(Main.centerMessage("Your version: §c" + Main.getInstance().getDescription().getVersion() + "§7 => §a" + lastedVersion, 41), commandSender);
                    sendPlayerMessage("§c" + Main.centerMessage("OnlyProxy is not up to date! ):", 41), commandSender);
                    ArrayList<String> versions = GitHub.getAllReleaseVersions("Dolfirobots", "OnlyProxy");

                    ArrayList<Integer> removeVersionIndex = new ArrayList<>();
                    int currentIndex = 0;
                    for (String version : versions) {
                        currentIndex++;
                        removeVersionIndex.add(currentIndex);
                        if (version.equalsIgnoreCase(Main.getInstance().getDescription().getVersion())) {
                            break;
                        }
                    }
                    for (int index : removeVersionIndex) {
                        versions.remove(index);
                    }
                    sendPlayerMessage(Main.centerMessage("You are §e" + versions.size() + "§7 versions behind!", 41), commandSender);
                    sendPlayerMessage(Main.centerMessage("Please download it here:", 41), commandSender);
                    sendPlayerMessage(Main.centerMessage("https://github.com/Dolfirobots/OnlyProxy/", 41), commandSender);
                } else {
                    sendPlayerMessage(Main.centerMessage("Your version: " + Main.getInstance().getDescription().getVersion(), 41), commandSender);
                    sendPlayerMessage(Main.centerMessage("§eWe couldn't check the lasted version!", 41), commandSender);
                    sendPlayerMessage(Main.centerMessage("Please check the internet", 41), commandSender);
                    sendPlayerMessage(Main.centerMessage("connection from your Server.", 41), commandSender);
                }
                sendPlayerMessage("-----------------------------------------", commandSender);
            } else {
                sendPlayerMessage("-----------------------------------------", commandSender);
                if (commandSender.hasPermission("onlyproxy.commands")) {
                    sendPlayerMessage(Main.centerMessage("§cUsage: §e/" + s + " [reload/version]", 41), commandSender);
                } else {
                    sendPlayerMessage(Main.centerMessage("§cUsage: §e/" + s, 41), commandSender);
                }
                sendPlayerMessage("-----------------------------------------", commandSender);
            }
        } else {
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
