package me.jaime29010.ezbans.storage.types;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import me.jaime29010.ezbans.Main;
import me.jaime29010.ezbans.storage.DataStorage;
import me.jaime29010.ezbans.storage.data.BanEntry;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jaime Martinez Rincon on 18/07/2016 in project eZBans.
 */
public class JsonStorage implements DataStorage {
    public static long INTERVAL = 10 * 60 * 20;
    private Map<String, BanEntry> data;
    private final Gson gson;

    public JsonStorage() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.enableComplexMapKeySerialization();
        gson = builder.create();
    }

    @Override
    public void open(Plugin plugin) {
        File file = new File(plugin.getDataFolder(), "data.json");
        if (file.exists()) {
            plugin.getLogger().info("Database exists, reading data...");
            try (JsonReader reader = new JsonReader(new FileReader(file))) {
                data = gson.fromJson(reader, Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            plugin.getLogger().fine("Database does not exist, creating it...");
            data = new HashMap<>();
        }
    }

    @Override
    public void close(Plugin plugin) {
        save(plugin);
    }

    private void save(Plugin plugin) {
        plugin.getLogger().info("Periodically saving database...");
        try (Writer writer = new FileWriter(new File(plugin.getDataFolder(), "data.json"))) {
            String output = gson.toJson(data, Map.class);
            writer.write(output);
        } catch (IOException e) {
            plugin.getLogger().severe("Something went terribly wrong, couldn't save the database");
            e.printStackTrace();
        }
    }

    @Override
    public BanEntry getEntry(String identifier) {
        return null;
    }

    @Override
    public boolean addEntry(BanEntry entry) {
        return false;
    }

    @Override
    public boolean removeEntry(String identifier) {
        return false;
    }

    @Override
    public boolean removeEntry(BanEntry entry) {
        return false;
    }

    @Override
    public Collection<BanEntry> getEntries() {
        return null;
    }
}
