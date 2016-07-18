package me.jaime29010.ezbans.listeners;

import me.jaime29010.ezbans.Main;
import me.jaime29010.ezbans.data.JsonBanEntry;
import me.jaime29010.ezbans.utils.PluginUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Date;

import static me.jaime29010.ezbans.utils.PluginUtils.color;

/**
 * Created by Jaime Martinez Rincon on 30/06/2016 in project eZBans.
 */

public class PlayerLoginListener implements Listener {
    private final Main main;
    public PlayerLoginListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void on(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        JsonBanEntry entry = main.getBan(player.getUniqueId(), event.getAddress().getHostName());
        if (entry != null) {
            switch (entry.getType()) {
                case NORMAL: {
                    String message = PluginUtils.joinList(main.getConfig().getStringList("messages.layouts.ban"), new PluginUtils.Joiner() {
                        @Override
                        public String add(String string) {
                            string = color(string.replace("%prefix%", main.getConfig().getString("prefix")));
                            string = color(string.replace("%reason%", entry.getReason()));
                            return string;
                        }
                    });
                    event.disallow(PlayerLoginEvent.Result.KICK_BANNED, message);
                    break;
                }

                case TEMPORARY: {
                    if (entry.hasExpired()) {
                        main.removeBan(entry);
                        break;
                    }

                    String message = PluginUtils.joinList(main.getConfig().getStringList("messages.layouts.tempban"), new PluginUtils.Joiner() {
                        @Override
                        public String add(String string) {
                            string = color(string.replace("%prefix%", main.getConfig().getString("prefix")));
                            string = color(string.replace("%reason%", entry.getReason()));
                            string = color(string.replace("%until%", new Date(entry.getExpires()).toString()));
                            return string;
                        }
                    });
                    event.disallow(PlayerLoginEvent.Result.KICK_BANNED, message);
                    break;
                }

                case ADDRESS: {
                    String message = PluginUtils.joinList(main.getConfig().getStringList("messages.layouts.ban"), new PluginUtils.Joiner() {
                        @Override
                        public String add(String string) {
                            string = color(string.replace("%prefix%", main.getConfig().getString("prefix")));
                            string = color(string.replace("%reason%", entry.getReason()));
                            return string;
                        }
                    });
                    event.disallow(PlayerLoginEvent.Result.KICK_BANNED, message);
                    break;
                }
            }
        }
    }
}
