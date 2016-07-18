package me.jaime29010.ezbans;

import com.google.gson.Gson;
import me.jaime29010.ezbans.commands.*;
import me.jaime29010.ezbans.listeners.PlayerLoginListener;
import me.jaime29010.ezbans.storage.DataStorage;
import me.jaime29010.ezbans.storage.types.JsonStorage;
import me.jaime29010.ezbans.storage.types.SQLStorage;
import me.jaime29010.ezbans.utils.ConfigurationManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private FileConfiguration config;
    private DataStorage storage;
    private Gson gson;

    @Override
    public void onEnable() {
        //Loading config
        config = ConfigurationManager.loadConfig("config.yml", this);

        //Loading storage
        if (config.getBoolean("mysql.enabled")) {
            storage = new SQLStorage(
                    config.getString("mysql.address"),
                    config.getInt("mysql.port"),
                    config.getString("mysql.database"),
                    config.getString("mysql.username"),
                    config.getString("mysql.password")
            );
        } else {
            storage = new JsonStorage();
        }

        try {
            storage.open(this);
        } catch (Exception e) {
            getLogger().severe("Something went terribly wrong, couldn't open the database");
            setEnabled(false);
            return;
        }

        //Registering commands
        getCommand("ban").setExecutor(new BanCommand(this));
        getCommand("banip").setExecutor(new BanIpCommand(this));
        getCommand("banlist").setExecutor(new BanListCommand(this));
        getCommand("check").setExecutor(new CheckCommand(this));
        getCommand("kick").setExecutor(new KickCommand(this));
        getCommand("tempban").setExecutor(new TempBanCommand(this));
        getCommand("unban").setExecutor(new UnBanCommand(this));

        //Registering listener
        getServer().getPluginManager().registerEvents(new PlayerLoginListener(this), this);
    }

    @Override
    public void onDisable() {
        try {
            storage.close(this);
        } catch (Exception e) {
            getLogger().severe("Something went terribly wrong, couldn't close the database");
            e.printStackTrace();
        }
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    public DataStorage getStorage() {
        return storage;
    }
}
