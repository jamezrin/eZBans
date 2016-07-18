package me.jaime29010.ezbans.commands;

import me.jaime29010.ezbans.Main;
import me.jaime29010.ezbans.utils.PluginUtils;
import me.jaime29010.ezbans.data.JsonBanEntry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Jaime Martinez Rincon on 30/06/2016 in project eZBans.
 */

public class BanListCommand implements CommandExecutor {
    private final Main main;
    public BanListCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("ezbans.banlist")) {
                for (String message : main.getConfig().getStringList("messages.layouts.banlist.header")) {
                    player.sendMessage(PluginUtils.color(message));
                }

                for (JsonBanEntry entry : main.getDataPool().getBans().values()) {
                    for (String message : main.getConfig().getStringList("messages.layouts.banlist.entry")) {
                        player.sendMessage(PluginUtils.color(message
                                .replace("%identifier%", Bukkit.getOfflinePlayer(entry.getPunished()).getName())
                                .replace("%until%", String.valueOf(entry.getExpires()))
                                .replace("%punisher%", Bukkit.getOfflinePlayer(entry.getPunisher()).getName())
                                .replace("%reason%", entry.getReason())
                        ));
                    }
                }

                for (JsonBanEntry entry : main.getDataPool().getIpBans().values()) {
                    for (String message : main.getConfig().getStringList("messages.layouts.banlist.entry")) {
                        player.sendMessage(PluginUtils.color(message
                                .replace("%identifier%", entry.getAddress())
                                .replace("%until%", String.valueOf(entry.getExpires()))
                                .replace("%punisher%", Bukkit.getOfflinePlayer(entry.getPunisher()).getName())
                                .replace("%reason%", entry.getReason())
                        ));
                    }
                }

                player.sendMessage(PluginUtils.color(main.getConfig().getString("usage.banlist")
                        .replace("%label%", label)
                        .replace("%prefix%", main.getConfig().getString("prefix"))
                ));
            } else {
                player.sendMessage(PluginUtils.color(main.getConfig().getString("messages.no-perms")));
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player");
        }
        return true;
    }
}