package me.jaime29010.ezbans.storage.types;

import me.jaime29010.ezbans.storage.DataStorage;
import me.jaime29010.ezbans.storage.data.BanEntry;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

/**
 * Created by Jaime Martinez Rincon on 18/07/2016 in project eZBans.
 */
public class SQLStorage implements DataStorage {
    private final String address;
    private final int port;
    private final String database;
    private final String username;
    private final String password;

    public SQLStorage(String address, int port, String database, String username, String password) {
        this.address = address;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    @Override
    public void open(Plugin plugin) {

    }

    @Override
    public void close(Plugin plugin) {

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
