package me.jaime29010.ezbans.commands;

import me.jaime29010.ezbans.Main;
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

        } else {
            sender.sendMessage("This command can only be executed by a player");
        }
        return true;
    }
}
