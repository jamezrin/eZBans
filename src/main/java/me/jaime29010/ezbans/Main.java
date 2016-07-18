package me.jaime29010.ezbans;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import me.jaime29010.ezbans.commands.*;
import me.jaime29010.ezbans.listeners.PlayerLoginListener;
import me.jaime29010.ezbans.data.JsonBanEntry;
import me.jaime29010.ezbans.data.JsonDataPool;
import me.jaime29010.ezbans.utils.ConfigurationManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.UUID;

public final class Main extends JavaPlugin {
    public static final String ADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    public static final int SAVE_INTERVAL = 10;
    private FileConfiguration config;
    private JsonDataPool database;
    private Gson gson;

    @Override
    public void onEnable() {
        //Loading config
        config = ConfigurationManager.loadConfig("config.yml", this);

        //Setting up gson
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.enableComplexMapKeySerialization();
        gson = builder.create();

        //Loading database
        File file = new File(getDataFolder(), "data.json");
        if (file.exists()) {
            getLogger().info("Database exists, reading data...");
            try (JsonReader reader = new JsonReader(new FileReader(file))) {
                database = gson.fromJson(reader, JsonDataPool.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            getLogger().fine("Database does not exist, it will be created on server shutdown");
            database = new JsonDataPool();
        }

        //Database save task
        getLogger().info(String.format("The database will be saved every %s minutes", SAVE_INTERVAL));
        new BukkitRunnable() {
            @Override
            public void run() {
                getLogger().info("Periodically saving database...");
                try (Writer writer = new FileWriter(new File(getDataFolder(), "data.json"))) {
                    String output = gson.toJson(database, JsonDataPool.class);
                    writer.write(output);
                } catch (IOException e) {
                    getLogger().severe("Something went terribly wrong, couldn't save the database");
                    e.printStackTrace();
                }
            }
        }.runTaskTimerAsynchronously(this, SAVE_INTERVAL * 60 * 20, SAVE_INTERVAL * 60 * 20);

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
        //Saving database
        getServer().getScheduler().cancelTasks(this);
        getLogger().info("Saving database...");
        try (Writer writer = new FileWriter(new File(getDataFolder(), "data.json"))) {
            String output = gson.toJson(database, JsonDataPool.class);
            writer.write(output);
        } catch (IOException e) {
            getLogger().severe("Something went terribly wrong, couldn't save the database");
            e.printStackTrace();
        }
    }

    public JsonDataPool getDataPool() {
        return database;
    }

    public JsonBanEntry getBan(UUID uuid, String ip) {
        JsonBanEntry result = database.getBans().get(uuid);
        if (result != null) return result;

        result = database.getIpBans().get(ip);
        return result;
    }

    public void addBan(JsonBanEntry entry) {
        if (entry.getType() == JsonBanEntry.BanType.ADDRESS) {
            database.getIpBans().put(entry.getAddress(), entry);
        } else {
            database.getBans().put(entry.getPunished(), entry);
        }
    }

    public void removeBan(JsonBanEntry entry) {
        if (entry.getType() == JsonBanEntry.BanType.ADDRESS) {
            database.getIpBans().remove(entry.getAddress(), entry);
        } else {
            database.getBans().remove(entry.getPunished(), entry);
        }
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }
}
