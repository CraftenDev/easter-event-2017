package me.florestanii.christmasevent.commands;

import me.florestanii.christmasevent.ChristmasEvent;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PresentCommand implements CommandExecutor{

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
				
				plugin.addPresent(loc);
				
				sender.sendMessage("Add the " + plugin.getPresents().size() +  ". present at: " + loc.toString());
				
			} else {
				sender.sendMessage(ChatColor.DARK_RED + "You have no permission to use this command.");
			}
			
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "This is a player command.");
		}
		
		return true;
	}

	
	
}
