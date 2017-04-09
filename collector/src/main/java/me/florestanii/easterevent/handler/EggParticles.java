package me.florestanii.easterevent.handler;

import me.florestanii.easterevent.EasterEvent;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class EggParticles implements Runnable {
    private final EasterEvent plugin;
    private int i = 0;

    public EggParticles(EasterEvent plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        i++;
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            plugin.getEggs().stream()
                    .filter((egg) -> !plugin.hasAlreadyCollected(player, egg))
                    .forEach((egg) -> {
                        if (player.getLocation().distanceSquared(egg.getLocation()) <= 400) {
                            player.spigot().playEffect(
                                    egg.getLocation().clone().add(0.5f, 0.6f, 0.5f),
                                    Effect.FIREWORKS_SPARK, 0, 0, 0, 0, 0, 0.05f, 1, 20);
                            if (i >= 30) {
                                player.playSound(egg.getLocation(), Sound.PORTAL_TRAVEL, 0.25f, 1);
                                i = 0;
                            }
                        }
                    });
            
        }
    }
}
