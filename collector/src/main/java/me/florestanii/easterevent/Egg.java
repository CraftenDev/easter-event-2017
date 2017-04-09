package me.florestanii.easterevent;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public abstract class Egg {
    private final String id;
    private final Location loc;

    public Egg(String id, Location loc) {
        this.id = id;
        this.loc = loc;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return loc;
    }

    public abstract void open(Player player);

    public void save(ConfigurationSection section) {
        section.set("world", loc.getWorld().getName());
        section.set("x", loc.getX());
        section.set("y", loc.getY());
        section.set("z", loc.getZ());
    }

    public static Egg load(String id,ConfigurationSection section) {
        Location loc = new Location(
                Bukkit.getServer().getWorld(section.getString("world")),
                section.getDouble("x"), section.getDouble("y"), section.getDouble("z"));
        switch (section.getString("type").toLowerCase()) {
            case "gems":
                return new GemsEgg(id, loc, section.getInt("amount"));
            case "money":
                return new MoneyEgg(id,loc,section.getInt("amount"));
            case "item":
                return new ItemEgg(id,loc);
            default:
                return null;
        }
    }
}


