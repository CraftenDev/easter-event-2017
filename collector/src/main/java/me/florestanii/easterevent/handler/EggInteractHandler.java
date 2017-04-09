package me.florestanii.easterevent.handler;

import me.florestanii.easterevent.EasterEvent;
import me.florestanii.easterevent.util.ActionBar;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EggInteractHandler implements Listener {
    private final EasterEvent plugin;

    public EggInteractHandler(EasterEvent plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            plugin.getEgg(block.getLocation()).ifPresent((egg) -> {
                if (plugin.hasAlreadyCollected(event.getPlayer(), egg)) {
                    new ActionBar(event.getPlayer(), ChatColor.DARK_RED + "Du hast dieses Ei bereits eingesammelt.", 5, plugin);
                } else {
                    new ActionBar(event.getPlayer(), ChatColor.GREEN + "Du hast ein neues Ei eingesammelt. Frohe Ostern!", 5, plugin);
                    plugin.collect(event.getPlayer(), egg);
                }
            });
        }
    }
}
