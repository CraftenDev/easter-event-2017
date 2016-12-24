package me.florestanii.christmasevent;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ItemPresent extends Present {
    public ItemPresent(String id, Location loc) {
        super(id, loc);
    }

    @Override
    public void open(Player player) {
        player.sendMessage(ChatColor.GREEN + "Das Geschenk erhälst du auf dem Survival- oder Skyblock-Server, sobald du ihn betrittst!");
        ChristmasEvent.getPlugin(ChristmasEvent.class).getPlayerStore(player).put("xmas2016.present." + getId() + ".redeem", "true");
    }

    @Override
    public void save(ConfigurationSection section) {
        super.save(section);
        section.set("type","item");
    }
}
