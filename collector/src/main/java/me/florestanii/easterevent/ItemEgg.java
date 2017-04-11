package me.florestanii.easterevent;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ItemEgg extends Egg {
    public ItemEgg(String id, Location loc) {
        super(id, loc);
    }

    @Override
    public void open(Player player) {
        player.sendMessage(ChatColor.GREEN + "Das Ei erhälst du auf dem Survival- oder Skyblock-Server, sobald du ihn betrittst!");
        EasterEvent.getPlugin(EasterEvent.class).getPlayerStore(player).put("easter2017.egg." + getId() + ".redeem", "true");
    }

    @Override
    public void save(ConfigurationSection section) {
        super.save(section);
        section.set("type","item");
    }
}
