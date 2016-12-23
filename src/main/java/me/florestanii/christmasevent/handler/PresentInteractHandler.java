package me.florestanii.christmasevent.handler;

import me.florestanii.christmasevent.ChristmasEvent;
import me.florestanii.christmasevent.util.ActionBar;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PresentInteractHandler implements Listener {
    private final ChristmasEvent plugin;

    public PresentInteractHandler(ChristmasEvent plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            plugin.getPresent(block.getLocation()).ifPresent((p) -> {
                if (plugin.hasAlreadyCollected(event.getPlayer(), p)) {
                    new ActionBar(event.getPlayer(), ChatColor.DARK_RED + "Du hast dieses Geschenk bereits eingesammelt.", 5, plugin);
                } else {
                    new ActionBar(event.getPlayer(), ChatColor.GREEN + "Du hast ein neues Geschenk eingesammelt. Frohe Weihnachten!", 5, plugin);
                    plugin.collect(event.getPlayer(), p);
                }
            });
        }
    }
}
