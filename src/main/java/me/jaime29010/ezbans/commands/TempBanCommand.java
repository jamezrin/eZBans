package me.jaime29010.ezbans.commands;

import me.jaime29010.ezbans.Main;
import me.jaime29010.ezbans.data.JsonBanEntry;
import me.jaime29010.ezbans.utils.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static me.jaime29010.ezbans.utils.PluginUtils.color;

/**
 * Created by Jaime Martinez Rincon on 03/07/2016 in project eZBans.
 */

public class TempBanCommand implements CommandExecutor {
    private final Main main;
    public TempBanCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("ezbans.tempban")) {
                if (args.length >= 3) {
                    Player target = main.getServer().getPlayer(args[0]);
                    if (target != null) {
                        final String reason;
                        if (args.length >= 4) {
                            reason = PluginUtils.joinArray(args, 3);
                        } else {
                            reason = main.getConfig().getString("default-reason");
                        }

                        long millis = System.currentTimeMillis();
                        try {
                            int duration = Integer.valueOf(args[1]);
                            if (duration < 1) {
                                player.sendMessage(ChatColor.RED + "You did not input a correct duration");
                                return true;
                            }

                            switch (args[2]) {
                                case "S": {
                                    millis += TimeUnit.SECONDS.toMillis(duration);
                                    break;
                                }
                                case "M": {
                                    millis += TimeUnit.MINUTES.toMillis(duration);
                                    break;
                                }
                                case "H": {
                                    millis += TimeUnit.HOURS.toMillis(duration);
                                    break;
                                }
                                case "D": {
                                    millis += TimeUnit.DAYS.toMillis(duration);
                                    break;
                                }
                                case "Mo": {
                                    millis += TimeUnit.DAYS.toMillis(duration) * 31;
                                    break;
                                }
                                case "Y": {
                                    millis += TimeUnit.DAYS.toMillis(duration) * 365;
                                    break;
                                }
                                default: {
                                    player.sendMessage(ChatColor.RED + "You did not input a correct duration");
                                    return true;
                                }
                            }
                        } catch (Exception e) {
                            player.sendMessage(ChatColor.RED + "You did not input a correct duration");
                        }

                        if (target.hasPermission("ezbans.tempban")) {
                            player.sendMessage(color(main.getConfig().getString("messages.ban-exempt")));
                            return true;
                        }

                        JsonBanEntry entry = new JsonBanEntry(target.getUniqueId(), player.getUniqueId(), reason, millis);
                        if (main.getDataPool().getBans().containsKey(entry.getPunished())) {
                            player.sendMessage(color(main.getConfig().getString("messages.already-banned")
                                    .replace("%identifier%", entry.getPunished().toString())
                            ));
                        } else {
                            final Date until = new Date(millis);
                            main.addBan(entry);
                            target.kickPlayer(PluginUtils.joinList(main.getConfig().getStringList("messages.layouts.tempban"), new PluginUtils.Joiner() {
                                @Override
                                public String add(String string) {
                                    string = string.replace("%prefix%", main.getConfig().getString("prefix"));
                                    string = string.replace("%reason%", reason);
                                    string = string.replace("%until%", until.toString());
                                    return color(string);
                                }
                            }));

                            Bukkit.broadcastMessage(color(main.getConfig().getString("messages.banned")
                                    .replace("%punished%", target.getName())
                                    .replace("%punisher%", player.getName())
                            ));
                        }
                    } else {
                        player.sendMessage(color(main.getConfig().getString("messages.offline")
                                .replace("%target%", args[0])
                                .replace("%prefix%", main.getConfig().getString("prefix"))
                        ));
                    }
                    return true;
                }

                player.sendMessage(color(main.getConfig().getString("usage.tempban")
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
