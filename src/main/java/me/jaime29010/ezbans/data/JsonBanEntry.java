package me.jaime29010.ezbans.data;

import java.util.UUID;

/**
 * Created by Jaime Martinez Rincon on 30/06/2016 in project eZBans.
 */

public class JsonBanEntry {
    //Not exclusive
    private final BanType type;
    private final UUID punisher;
    private final String reason;

    //Exclusive to normal and temporal bans
    private final UUID punished;

    //Exclusive to ip bans
    private final String address;

    //Exclusive to temporal bans
    private final long expires;

    //Constructor for normal ban
    public JsonBanEntry(UUID punished, UUID punisher, String reason) {
        this.type = BanType.NORMAL;
        this.punished = punished;
        this.punisher = punisher;
        this.reason = reason;

        this.address = null;
        this.expires = 0;
    }

    //Constructor for temporary ban
    public JsonBanEntry(UUID punished, UUID punisher, String reason, long expires) {
        this.type = BanType.TEMPORARY;
        this.punisher = punisher;
        this.reason = reason;
        this.punished = punished;
        this.expires = expires;

        this.address = null;
    }

    //Constructor for ip ban
    public JsonBanEntry(String address, UUID punisher, String reason) {
        this.type = BanType.ADDRESS;
        this.punisher = punisher;
        this.reason = reason;
        this.address = address;

        this.punished = null;
        this.expires = 0;
    }

    public BanType getType() {
        return type;
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
