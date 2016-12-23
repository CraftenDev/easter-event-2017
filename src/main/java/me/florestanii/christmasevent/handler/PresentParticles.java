package me.florestanii.christmasevent.handler;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.florestanii.christmasevent.ChristmasEvent;
import me.florestanii.christmasevent.Present;

public class PresentParticles {

	private final ChristmasEvent plugin;
	
	public PresentParticles(ChristmasEvent plugin) {
		this.plugin = plugin;
		
		this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {

			int i = 0;
			
			@Override
			public void run() {
				i++;
				for (Player player : plugin.getServer().getOnlinePlayers()) {
					
					for (Present present : plugin.getPresents()) {
						
						if (!plugin.hasAlreadyCollected(player, present)) {
							if (player.getLocation().distanceSquared(present.getLocation()) <= 400) {
								
								player.spigot().playEffect(present.getLocation().clone().add(0.5f, 0.6f, 0.5f), Effect.FIREWORKS_SPARK, 0, 0, 0, 0, 0, 0.05f, 1, 20);
								
								if (i >= 30) {

									player.playSound(present.getLocation().clone().add(0.5f, 0.6f, 0.5f), Sound.PORTAL_TRAVEL, 0.25f, 1);
									i = 0;
									
								}
								
							}
						}
						
					}
					
				}
				
			}
		}, 0, 4);
		
	}
	
}
