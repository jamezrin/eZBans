package me.jaime29010.ezbans.listeners;

import me.jaime29010.ezbans.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

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

    }
}
