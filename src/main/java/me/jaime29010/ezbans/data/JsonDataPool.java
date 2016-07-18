package me.jaime29010.ezbans.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Jaime Martinez Rincon on 30/06/2016 in project eZBans.
 */

public class JsonDataPool {
    private final Map<UUID, JsonBanEntry> bans = new HashMap<>();
    private final Map<String, JsonBanEntry> ipbans = new HashMap<>();

    public Map<UUID, JsonBanEntry> getBans() {
        synchronized (bans) {
            return bans;
        }
    }

    public Map<String, JsonBanEntry> getIpBans() {
        synchronized (ipbans) {
            return ipbans;
        }
    }
}
