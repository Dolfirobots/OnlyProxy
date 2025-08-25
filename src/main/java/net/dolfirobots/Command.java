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
                // TODO: Adding github in this Plugin
            } else {
                commandSender.sendMessage("§cUsage: §e/onlyproxy [reload/version]");
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
