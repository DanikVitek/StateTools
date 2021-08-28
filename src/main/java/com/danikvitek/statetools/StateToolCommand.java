package com.danikvitek.statetools;

import com.danikvitek.statetools.utils.ToolsEnum;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StateToolCommand implements CommandExecutor, TabCompleter {
    private static final YamlConfiguration translations = Main.getModifyTranslationsFile();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("statetools.command.statetool")) {
                if (args.length == 1) {
                    switch (args[0].toLowerCase()) {
                        case "wrench": {
                            if (player.getInventory().firstEmpty() != -1)
                                player.getInventory().addItem(ToolsEnum.WRENCH.getItem());
                            else
                                player.getWorld().dropItem(player.getLocation(), ToolsEnum.WRENCH.getItem());
                            break;
                        }
                        case "hammer": {
                            if (player.getInventory().firstEmpty() != -1)
                                player.getInventory().addItem(ToolsEnum.HAMMER.getItem());
                            else
                                player.getWorld().dropItem(player.getLocation(), ToolsEnum.HAMMER.getItem());
                            break;
                        }
                        case "brush": {
                            if (player.getInventory().firstEmpty() != -1)
                                player.getInventory().addItem(ToolsEnum.BRUSH.getItem());
                            else
                                player.getWorld().dropItem(player.getLocation(), ToolsEnum.BRUSH.getItem());
                            break;
                        }
                        default:
                            return false;
                    }
                }
            }
            else if (Objects.requireNonNull(translations.getString("no_permission", "&cYou have no permissions to do that")).length() > 0)
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(translations.getString("no_permission", "&cYou have no permissions to do that"))));
        }
        else if (Objects.requireNonNull(translations.getString("cant_use_console", "&cCan only be used by a player")).length() > 0)
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(translations.getString("cant_use_console", "&cCan only be used by a player"))));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player)
            if (sender.hasPermission("statetools.command.statetool"))
                if (args.length == 1) {
                    return copyPartialInnerMatching(args[0], Arrays.asList("wrench", "hammer", "brush"));
                }

        return null;
    }

    private static List<String> copyPartialInnerMatching(String arg, List<String> copyFrom) {
        List<String> result = new ArrayList<>();
        for (String copied: copyFrom) {
            if (copied.contains(arg.toLowerCase()))
                result.add(copied);
        }
        return result;
    }
}
