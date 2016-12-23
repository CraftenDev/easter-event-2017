package me.florestanii.christmasevent;

import org.bukkit.Location;

public class Present {
    private final String id;
    private final Location loc;

    public Present(String id, Location loc) {
        this.id = id;
        this.loc = loc;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return loc;
    }
}
