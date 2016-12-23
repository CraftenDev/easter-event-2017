package me.florestanii.christmasevent.commands;

import me.florestanii.christmasevent.ChristmasEvent;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PresentInteractHanlder implements Listener{

	private final ChristmasEvent plugin;
	
	public PresentInteractHanlder(ChristmasEvent plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockInteract(PlayerInteractEvent event) {
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			
			if (block.getType() == Material.SKULL) {
				
				if (plugin.isPresent(block.getLocation())) {
					
				}
				
			}
			
		}
	}
	
}
