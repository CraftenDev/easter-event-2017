package me.florestanii.christmasevent.redeemer;

import de.craften.plugins.playerdatastore.api.PlayerDataStore;
import de.craften.plugins.playerdatastore.api.PlayerDataStoreService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChristmasEventRedeemer extends JavaPlugin implements Listener {
    public ChristmasEventRedeemer() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // TODO
    }

    public PlayerDataStore getPlayerStore(Player player) {
        return getServer().getServicesManager().getRegistration(PlayerDataStoreService.class).getProvider().getStore(player);
    }
}
