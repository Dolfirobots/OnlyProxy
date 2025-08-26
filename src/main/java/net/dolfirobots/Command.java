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
            sendPlayerMessage("-------------------------------------------------", commandSender);
            sendPlayerMessage("      §eOnlyProxy §7Plugin by §bDolfirobots      ", commandSender);
            sendPlayerMessage("        Loaded version: " + Main.getInstance().getDescription().getVersion(), commandSender);
            sendPlayerMessage("                 Download it here:               ", commandSender);
            sendPlayerMessage("     https://github.com/Dolfirobots/OnlyProxy    ", commandSender);
            sendPlayerMessage("-------------------------------------------------", commandSender);
        } else
        if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("reload")) {
                if (!commandSender.hasPermission("onlyproxy.commands")) {
                    sendPlayerMessage("-------------------------------------------------", commandSender);
                    sendPlayerMessage("§cYou don't have the Permission for this command!", commandSender);
                    sendPlayerMessage("-------------------------------------------------", commandSender);
                    return true;
                }
                sendPlayerMessage("-------------------------------------------------", commandSender);
                sendPlayerMessage("         §eReloading Plugin OnlyProxy...         ", commandSender);
                Config.reload();
                sendPlayerMessage("                    §aReloaded!                  ", commandSender);
                sendPlayerMessage("-------------------------------------------------", commandSender);
            } else
            if (strings[0].equalsIgnoreCase("version")) {
                if (!commandSender.hasPermission("onlyproxy.commands")) {
                    sendPlayerMessage("-------------------------------------------------", commandSender);
                    sendPlayerMessage("§cYou don't have the Permission for this command!", commandSender);
                    sendPlayerMessage("-------------------------------------------------", commandSender);
                    return true;
                }
                sendPlayerMessage("-------------------------------------------------", commandSender);
                sendPlayerMessage("            §eFetching last version...           ", commandSender);

                String lastedVersion = GitHub.getLastedReleaseVersion("Dolfirobots", "OnlyProxy");

                if (lastedVersion.equalsIgnoreCase(Main.getInstance().getDescription().getVersion())) {
                    sendPlayerMessage("     Your version: §a" + Main.getInstance().getDescription().getVersion() + "§7 == §a" + lastedVersion, commandSender);
                    sendPlayerMessage("         §aOnlyProxy is up to date! (:          ", commandSender);

                } else if (!lastedVersion.equalsIgnoreCase("unknown")) {
                    sendPlayerMessage("     Your version: §c" + Main.getInstance().getDescription().getVersion() + "§7 => §a" + lastedVersion, commandSender);
                    sendPlayerMessage("         §cOnlyProxy is not up to date! ):       ", commandSender);
                    sendPlayerMessage("Please download it HERE: https://github.com/Dolfirobots/OnlyProxy/releases/" + lastedVersion, commandSender);
                } else {
                    sendPlayerMessage("         Your version: " + Main.getInstance().getDescription().getVersion(), commandSender);
                    sendPlayerMessage("      §eWe couldn't check the lasted version!    ", commandSender);
                    sendPlayerMessage("Please check the internet connection from you Server.", commandSender);
                }
                sendPlayerMessage("-------------------------------------------------", commandSender);
            } else {
                sendPlayerMessage("-------------------------------------------------", commandSender);
                if (commandSender.hasPermission("onlyproxy.commands")) {
                    sendPlayerMessage("  §cUsage: §e/" + s + " [reload/version]", commandSender);
                } else {
                    sendPlayerMessage("         §cUsage: §e/" + s, commandSender);
                }
                sendPlayerMessage("-------------------------------------------------", commandSender);
            }
        } else {
            sendPlayerMessage("-------------------------------------------------", commandSender);
            if (commandSender.hasPermission("onlyproxy.commands")) {
                sendPlayerMessage("  §cUsage: §e/" + s + " [reload/version]", commandSender);
            } else {
                sendPlayerMessage("         §cUsage: §e/" + s, commandSender);
            }
            sendPlayerMessage("-------------------------------------------------", commandSender);
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            list.add("reload");
            list.add("version");
        }
        return list;
    }
}
