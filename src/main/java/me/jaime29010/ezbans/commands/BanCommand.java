package me.jaime29010.ezbans.commands;

import me.jaime29010.ezbans.Main;
import me.jaime29010.ezbans.utils.PluginUtils;
import me.jaime29010.ezbans.data.JsonBanEntry;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Jaime Martinez Rincon on 03/07/2016 in project eZBans.
 */
public class BanCommand implements CommandExecutor {
    private final Main main;
    public BanCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("ezbans.ban")) {
                if (args.length >= 1) {
                    final String reason;
                    if (args.length >= 2) {
                        reason = PluginUtils.joinArray(args, 1);
                    } else {
                        reason = main.getConfig().getString("default-reason");
                    }

                    Player target = main.getServer().getPlayer(args[0]);
                    if (target != null) {
                        if (target.hasPermission("ezbans.ban")) {
                            player.sendMessage(PluginUtils.color(main.getConfig().getString("messages.ban-exempt")));
                            return true;
                        }

                        JsonBanEntry entry = new JsonBanEntry(target.getUniqueId(), player.getUniqueId(), reason);
                        if (main.getDataPool().getBans().containsKey(entry.getPunished())) {
                            player.sendMessage(PluginUtils.color(main.getConfig().getString("messages.already-banned")
                                    .replace("%identifier%", entry.getPunished().toString())
                            ));
                        } else {
                            main.addBan(entry);
                            target.kickPlayer(PluginUtils.joinList(main.getConfig().getStringList("messages.layouts.ban"), new PluginUtils.Joiner() {
                                @Override
                                public String add(String string) {
                                    string = string.replace("%prefix%", main.getConfig().getString("prefix"));
                                    string = string.replace("%reason%", reason);
                                    return PluginUtils.color(string);
                                }
                            }));

                            Bukkit.broadcastMessage(PluginUtils.color(main.getConfig().getString("messages.banned")
                                    .replace("%punished%", target.getName())
                                    .replace("%punisher%", player.getName())
                            ));
                        }
                    } else {
                        player.sendMessage(PluginUtils.color(main.getConfig().getString("messages.offline")
                                .replace("%target%", args[0])
                                .replace("%prefix%", main.getConfig().getString("prefix"))
                        ));
                    }
                    return true;
                }

                player.sendMessage(PluginUtils.color(main.getConfig().getString("usage.ban")
                        .replace("%label%", label)
                        .replace("%prefix%", main.getConfig().getString("prefix"))
                ));
            } else {
                player.sendMessage(PluginUtils.color(main.getConfig().getString("messages.no-perms")));
            }
        } else {
            sender.sendMessage("This command can only be executed by a player");
        }
        return true;
    }
}
