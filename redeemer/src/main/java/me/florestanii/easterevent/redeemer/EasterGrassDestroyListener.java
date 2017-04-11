package me.florestanii.easterevent.redeemer;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class EasterGrassDestroyListener implements Listener {
    private static final Material[] RANDOM_DROP_TYPE = new Material[]{
            Material.RABBIT,
            Material.RABBIT_FOOT,
            Material.RABBIT_HIDE,
            Material.RABBIT_STEW,
            Material.EGG,
            Material.DIAMOND,
            Material.CARROT_ITEM,
            Material.GOLDEN_CARROT
    };

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        if (!event.getBlock().getWorld().getName().equalsIgnoreCase("farm")) return;
        if (ThreadLocalRandom.current().nextDouble() >= 0.02) return;
        if (event.getBlock().getType() == Material.GRASS || event.getBlock().getType() == Material.LONG_GRASS) {
            event.getBlock().getWorld().dropItemNaturally(
                    event.getBlock().getLocation().clone().add(0.5, 0, 0.5),
                    new ItemStack(RANDOM_DROP_TYPE[ThreadLocalRandom.current().nextInt(RANDOM_DROP_TYPE.length)])
            );
        }
    }
}
