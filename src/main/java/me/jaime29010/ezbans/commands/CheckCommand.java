package me.jaime29010.ezbans.commands;

import me.jaime29010.ezbans.Main;
import me.jaime29010.ezbans.data.JsonBanEntry;
import me.jaime29010.ezbans.utils.UUIDFetcher;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.jaime29010.ezbans.Main.ADDRESS_PATTERN;
import static me.jaime29010.ezbans.utils.PluginUtils.color;

/**
 * Created by Jaime Martinez Rincon on 03/07/2016 in project eZBans.
 */

public class CheckCommand implements CommandExecutor {
    private final Main main;
    public CheckCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("ezbans.check")) {
                if (args.length == 1) {
                    final JsonBanEntry entry;
                    if (!args[0].matches(ADDRESS_PATTERN)) {
                        try {
                            UUID uuid = UUIDFetcher.getUUIDOf(args[0]);
                            entry = main.getBan(uuid, null);
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + "Error while fetching the uuid of the player specified");
                            return true;
                        }
                    } else {
                        entry = main.getBan(null, args[0]);
                    }

                    player.sendMessage(ChatColor.AQUA + args[0] + ChatColor.GRAY + " is " + (entry == null ? ChatColor.GREEN + "not banned" : ChatColor.RED + "banned"));
                    return true;
                }

                player.sendMessage(color(main.getConfig().getString("usage.check")
                        .replace("%label%", label)
                        .replace("%prefix%", main.getConfig().getString("prefix"))
                ));
            } else {
                player.sendMessage(color(main.getConfig().getString("messages.no-perms")));
            }
        } else {
            sender.sendMessage("This command can only be executed by a player");
        }
        return true;
    }
}
