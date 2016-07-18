package me.jaime29010.ezbans.storage.data;

import java.util.UUID;

/**
 * Created by Jaime Martinez Rincon on 18/07/2016 in project eZBans.
 */
public class BanEntry {
    //Not exclusive
    private final BanType type;
    private final String identifier;
    private final UUID punisher;
    private final String reason;

    //Not exclusive but optional
    private final UUID punished;

    //Exclusive to address bans
    private final String address;

    //Exclusive to temporal bans
    private final long expires;

    //Constructor for normal ban
    public BanEntry(UUID punished, UUID punisher, String reason) {
        this.type = BanType.NORMAL;
        this.identifier = punished.toString();
        this.punisher = punisher;
        this.reason = reason;
        this.punished = punished;

        this.address = null;
        this.expires = 0;
    }

    //Constructor for temporary ban
    public BanEntry(UUID punished, UUID punisher, String reason, long expires) {
        this.type = BanType.TEMPORARY;
        this.identifier = punished.toString();
        this.punisher = punisher;
        this.reason = reason;
        this.punished = punished;
        this.expires = expires;

        this.address = null;
    }

    //Constructor for ip ban
    public BanEntry(String address, UUID punisher, String reason) {
        this.type = BanType.ADDRESS;
        this.identifier = address;
        this.punisher = punisher;
        this.reason = reason;
        this.address = address;

        this.punished = null;
        this.expires = 0;
    }

    public BanType getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public UUID getPunisher() {
        return punisher;
    }

    public boolean hasReason() {
        return reason != null;
    }

    public String getReason() {
        return reason;
    }

    public UUID getPunished() {
        return punished;
    }

    public String getAddress() {
        return address;
    }

    public boolean hasExpired() {
        return System.currentTimeMillis() > expires;
    }

    public long getExpires() {
        return expires;
    }

    public enum BanType {
        NORMAL, TEMPORARY, ADDRESS
    }
}
