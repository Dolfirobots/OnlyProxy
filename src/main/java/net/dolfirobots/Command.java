package net.dolfirobots;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Command implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("reload")) {
                commandSender.sendMessage("§eReloading Plugin OnlyProxy...");
                Config.reloadConfig();
                commandSender.sendMessage("§aSuccessfully reloaded!");
            } else
            if (strings[0].equalsIgnoreCase("version")) {
                commandSender.sendMessage("§eFetching lasted version...");
                String lastedVersion = GitHub.getLastedReleaseVersion("Dolfirobots", "OnlyProxy");
                commandSender.sendMessage("Lasted version: " + lastedVersion + " Your version: " + Main.getInstance().getDescription().getVersion());
                if (lastedVersion.equalsIgnoreCase(Main.getInstance().getDescription().getVersion())) {
                    commandSender.sendMessage("§aOnlyProxy is up to date! (:");
                } else {
                    commandSender.sendMessage("§cOnlyProxy is not up to date! ):");
                    commandSender.sendMessage("Please update it HERE: https://github.com/Dolfirobots/OnlyProxy/releases/" + lastedVersion);
                }
            } else {
                commandSender.sendMessage("§cUsage: §e/" + s + " [reload/version]");
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            list.add("reload");
        }
        return list;
    }
}
