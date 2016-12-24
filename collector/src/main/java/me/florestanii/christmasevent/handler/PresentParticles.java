package me.florestanii.christmasevent.handler;

import me.florestanii.christmasevent.ChristmasEvent;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PresentParticles implements Runnable {
    private final ChristmasEvent plugin;
    private int i = 0;

    public PresentParticles(ChristmasEvent plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        i++;
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            plugin.getPresents().stream()
                    .filter((present) -> !plugin.hasAlreadyCollected(player, present))
                    .forEach((present) -> {
                        if (player.getLocation().distanceSquared(present.getLocation()) <= 400) {
                            player.spigot().playEffect(
                                    present.getLocation().clone().add(0.5f, 0.6f, 0.5f),
                                    Effect.FIREWORKS_SPARK, 0, 0, 0, 0, 0, 0.05f, 1, 20);
                            if (i >= 30) {
                                player.playSound(present.getLocation(), Sound.PORTAL_TRAVEL, 0.25f, 1);
                                i = 0;
                            }
                        }
                    });
        }
    }
}
