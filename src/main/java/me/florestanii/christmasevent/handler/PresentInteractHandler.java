package me.florestanii.christmasevent.handler;

import me.florestanii.christmasevent.ChristmasEvent;
import me.florestanii.christmasevent.Present;
import me.florestanii.christmasevent.util.ActionBar;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PresentInteractHandler implements Listener{

	private final ChristmasEvent plugin;
	
	public PresentInteractHandler(ChristmasEvent plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockInteract(PlayerInteractEvent event) {
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			
			if (block.getType() == Material.SKULL) {
				
				if (plugin.isPresent(block.getLocation())) {
					
					Present p = plugin.getPresent(block.getLocation());
					
					if (plugin.hasAlreadyCollected(event.getPlayer(), p)) {
						new ActionBar(event.getPlayer(), "§4Du hast dieses Geschenk bereits eingesammelt.", 5);
					} else {
						new ActionBar(event.getPlayer(), "§aDu hast ein neues Geschenk eingesammelt.", 5);
						plugin.collect(event.getPlayer(), p);
					}
					
				}
				
			}
			
		}
	}
	
}
