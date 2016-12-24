package me.florestanii.christmasevent.commands;

import me.florestanii.christmasevent.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PresentCommand implements CommandExecutor {
    private final ChristmasEvent plugin;

    public PresentCommand(ChristmasEvent plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("christmasevent.present.add")) {
                Player p = (Player) sender;
                Location loc = p.getLocation().getBlock().getLocation();
                Present present;
                switch (args[0].toLowerCase()) {
                    case "money":
                        present = new MoneyPresent("" + System.currentTimeMillis(), loc, Integer.parseInt(args[1]));
                        break;
                    case "gems":
                        present = new GemsPresent("" + System.currentTimeMillis(), loc, Integer.parseInt(args[1]));
                        break;
                    default:
                        present = new ItemPresent("" + System.currentTimeMillis(), loc);
                        break;
                }
                plugin.addPresent(present);
                sender.sendMessage("Add present " + present.getId() + " at: " + loc.toString());
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "You have no permission to use this command.");
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "This is a player command.");
        }
        return true;
    }
}
