package me.jaime29010.ezbans.storage;

import me.jaime29010.ezbans.storage.data.BanEntry;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

/**
 * Created by Jaime Martinez Rincon on 18/07/2016 in project eZBans.
 */
public interface DataStorage {
    /**
     * Opens the storage
     *
     * @throws Exception
     */
    void open(Plugin plugin);

    /**
     * Closes the storage
     *
     * @throws Exception
     */
    void close(Plugin plugin);


    /**
     * Returns an BanEntry object if it exists, null otherwise
     * The identifier argument can be an hostname {@link java.net.InetAddress} or an uuid {@link java.util.UUID}
     *
     * @param identifier the identifier of the entry or null if there is no entry with that identifier
     * @return the entry linked to that identifier
     */
    BanEntry getEntry(String identifier);

    /**
     * Adds a ban entry to the storage
     *
     * @param entry the ban entry
     * @return true if the ban was successfully added, false otherwise
     */
    boolean addEntry(BanEntry entry);

    /**
     * Removes a ban entry from the storage
     *
     * @param identifier the identifier of the ban
     * @return true if the entry was successfully removed, false otherwise
     */
    boolean removeEntry(String identifier);

    /**
     * Removes a ban entry from the storage
     *
     * @param entry the ban entry
     * @return true if the entry was successfully removed, false otherwise
     */
    boolean removeEntry(BanEntry entry);

    /**
     * Returns a collection with all the ban entries stored
     *
     * @return collection with all the entries stored
     */
    Collection<BanEntry> getEntries();
}
