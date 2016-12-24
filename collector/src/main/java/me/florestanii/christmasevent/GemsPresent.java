package me.florestanii.christmasevent;

import me.mickyjou.plugins.gems.api.GemProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class GemsPresent extends Present {
    private final int amount;

    public GemsPresent(String id, Location loc, int amount) {
        super(id, loc);
        this.amount = amount;
    }

    @Override
    public void open(Player player) {
        Bukkit.getServer().getServicesManager().getRegistration(GemProvider.class).getProvider()
                .addGems(player, amount);

        player.sendMessage(ChatColor.GREEN + "Was gelitzert denn da? Sind das etwa...");
        player.sendMessage(ChatColor.GREEN + "Das Geschänk enthält " + ChatColor.YELLOW + amount + " Gems" + ChatColor.GREEN + " für dich!");
    }

    @Override
    public void save(ConfigurationSection section) {
        super.save(section);
        section.set("type", "gems");
        section.set("amount", amount);
    }
}
