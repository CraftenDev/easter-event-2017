package me.florestanii.easterevent.commands;

import me.florestanii.easterevent.*;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EggCommand implements CommandExecutor {
    private final EasterEvent plugin;

    public EggCommand(EasterEvent plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("easterevent.egg.add")) {
                Player p = (Player) sender;
                Location loc = p.getLocation().getBlock().getLocation();
                Egg egg;
                switch (args[0].toLowerCase()) {
                    case "money":
                        egg = new MoneyEgg("" + System.currentTimeMillis(), loc, Integer.parseInt(args[1]));
                        break;
                    case "gems":
                        egg = new GemsEgg("" + System.currentTimeMillis(), loc, Integer.parseInt(args[1]));
                        break;
                    default:
                        egg = new ItemEgg("item", loc);
                        break;
                }
                plugin.addEgg(egg);
                sender.sendMessage("Add egg " + egg.getId() + " at: " + loc.toString());
            } else {
                sender.sendMessage(ChatColor.DARK_RED + "You have no permission to use this command.");
            }
        } else {
            sender.sendMessage(ChatColor.DARK_RED + "This is a player command.");
        }
        return true;
    }
}
