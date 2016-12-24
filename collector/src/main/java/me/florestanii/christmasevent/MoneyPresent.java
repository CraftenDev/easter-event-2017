package me.florestanii.christmasevent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class MoneyPresent extends Present {
    private final int amount;

    public MoneyPresent(String id, Location loc, int amount) {
        super(id, loc);
        this.amount = amount;
    }

    @Override
    public void open(Player player) {
        Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class).getProvider()
                .depositPlayer(player, amount);

        player.sendMessage(ChatColor.GREEN + "Das Geschenk enthält nur Geld, aber immerhin nett verpackt.");
        player.sendMessage(ChatColor.GREEN + "Hier sind " + ChatColor.YELLOW + amount + " C" + ChatColor.GREEN + " für dich!");
    }

    @Override
    public void save(ConfigurationSection section) {
        super.save(section);
        section.set("type", "money");
        section.set("amount", amount);
    }
}
