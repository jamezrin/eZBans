package me.jaime29010.ezbans.commands;

import me.jaime29010.ezbans.Main;
import me.jaime29010.ezbans.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Jaime Martinez Rincon on 30/06/2016 in project eZBans.
 */

public class KickCommand implements CommandExecutor {
    private final Main main;
    public KickCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("ezbans.kick")) {
                if (args.length >= 1) {
                    Player target = main.getServer().getPlayer(args[0]);
                    if (target != null) {
                        if (target.hasPermission("ezbans.kick")) {
                            player.sendMessage(PluginUtils.color(main.getConfig().getString("messages.kick-exempt")));
                            return true;
                        }

                        final String reason;
                        if (args.length >= 2) {
                            reason = PluginUtils.joinArray(args, 1);
                        } else {
                            reason = null;
                        }

                        target.kickPlayer(PluginUtils.joinList(main.getConfig().getStringList("messages.layouts.kick"), new PluginUtils.Joiner() {
                            @Override
                            public String add(String string) {
                                string = PluginUtils.color(string.replace("%prefix%", main.getConfig().getString("prefix")));
                                string = PluginUtils.color(string.replace("%reason%", reason == null ? main.getConfig().getString("default-reason") : reason));
                                return string;
                            }
                        }));

                        Bukkit.broadcastMessage(PluginUtils.color(main.getConfig().getString("messages.kicked")
                                .replace("%punished%", target.getName())
                                .replace("%punisher%", sender.getName())
                        ));
                    } else {
                        sender.sendMessage(PluginUtils.color(main.getConfig().getString("messages.offline")
                                .replace("%target%", args[0])
                                .replace("%prefix%", main.getConfig().getString("prefix"))
                        ));
                    }
                    return true;
                }

                sender.sendMessage(PluginUtils.color(main.getConfig().getString("usage.kick")
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
