package me.florestanii.easterevent.redeemer;

import de.craften.plugins.playerdatastore.api.PlayerDataStore;
import de.craften.plugins.playerdatastore.api.PlayerDataStoreService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EasterEventRedeemer extends JavaPlugin implements Listener {
    private static final String EASTER_BUNNY_SKULL = "http://textures.minecraft.net/texture/be50af50438affc65ce3b7a85b8e82cfbc3cab197c25d4a614230a2d1e0605d";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if ("true".equalsIgnoreCase(getPlayerStore(event.getPlayer()).get("easter2017.egg.item.redeem"))) {
            event.getPlayer().getInventory().addItem(CustomSkull.getSkullUrl(EASTER_BUNNY_SKULL)).entrySet().forEach((e) -> {
                e.getValue().setAmount(e.getKey());
                event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), e.getValue());
            });
            getPlayerStore(event.getPlayer()).remove("easter2017.egg.item.redeem");
        }
    }

    public PlayerDataStore getPlayerStore(Player player) {
        return getServer().getServicesManager().getRegistration(PlayerDataStoreService.class).getProvider().getStore(player);
    }
}
