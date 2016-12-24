package me.florestanii.christmasevent.redeemer;

import de.craften.plugins.playerdatastore.api.PlayerDataStore;
import de.craften.plugins.playerdatastore.api.PlayerDataStoreService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChristmasEventRedeemer extends JavaPlugin implements Listener {
    private static final String SANTA_SKULL = "http://textures.minecraft.net/texture/8a159236d7512bdb4326a24e14502167b76bcd85c041931c2194201b17f5e7";

    public ChristmasEventRedeemer() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if ("true".equalsIgnoreCase(getPlayerStore(event.getPlayer()).get("xmas2016.present.1482606302203.redeem"))) {
            event.getPlayer().getInventory().addItem(CustomSkull.getSkullUrl(SANTA_SKULL)).entrySet().forEach((e) -> {
                e.getValue().setAmount(e.getKey());
                event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), e.getValue());
            });
            getPlayerStore(event.getPlayer()).remove("xmas2016.present.1482606302203.redeem");
        }
    }

    public PlayerDataStore getPlayerStore(Player player) {
        return getServer().getServicesManager().getRegistration(PlayerDataStoreService.class).getProvider().getStore(player);
    }
}
