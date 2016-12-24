package me.florestanii.christmasevent;

import de.craften.plugins.playerdatastore.api.PlayerDataStore;
import de.craften.plugins.playerdatastore.api.PlayerDataStoreService;
import me.florestanii.christmasevent.commands.PresentCommand;
import me.florestanii.christmasevent.handler.PresentInteractHandler;
import me.florestanii.christmasevent.handler.PresentParticles;
import me.florestanii.christmasevent.util.CustomSkull;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ChristmasEvent extends JavaPlugin {
    private static final String[] SKINS = new String[]{
            "http://textures.minecraft.net/texture/9715f537fe7af6f5aa6eb98ad6902c13d05fb36c16b311ed832b09b598828",
            "http://textures.minecraft.net/texture/6b4cde16a4014de0a7651f6067f12695bb5fed6feaec1e9413ca4271e7c819",
            "http://textures.minecraft.net/texture/512e9451cdb196b78195a8f0a4b9c1c0a04f5827887927b6a82aad39cab2f430",
            "http://textures.minecraft.net/texture/6cef9aa14e884773eac134a4ee8972063f466de678363cf7b1a21a85b7",
            "http://textures.minecraft.net/texture/87fcae581cd67e612d355c114b6b818942360d03e4a1ba2bc61b81a7e107262",
            "http://textures.minecraft.net/texture/928e692d86e224497915a39583dbe38edffd39cbba457cc95a7ac3ea25d445",
            "http://textures.minecraft.net/texture/1b6730de7e5b941efc6e8cbaf5755f9421a20de871759682cd888cc4a81282",
            "http://textures.minecraft.net/texture/d08ce7deba56b726a832b61115ca163361359c30434f7d5e3c3faa6fe4052",
            "http://textures.minecraft.net/texture/47e55fcc809a2ac1861da2a67f7f31bd7237887d162eca1eda526a7512a64910",
            "http://textures.minecraft.net/texture/7fcd1c82e2fb3fa368cfa9a506ab6c98647595d215d6471ad47cce29685af",
            "http://textures.minecraft.net/texture/4acb3c1e1b34f8734aedfabd1e1f5e0b280bef924fb8bbf3e692d2538266f4",
            "http://textures.minecraft.net/texture/10c75a05b344ea043863974c180ba817aea68678cbea5e4ba395f74d4803d1d"};

    private final Map<Location, Present> presents = new HashMap<>();

    @Override
    public void onEnable() {
        getCommand("addpresent").setExecutor(new PresentCommand(this));
        getServer().getPluginManager().registerEvents(new PresentInteractHandler(this), this);
        loadPresents();

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new PresentParticles(this), 0, 4);
    }

    public void loadPresents() {
        ConfigurationSection section = getConfig().getConfigurationSection("presents");

        if (section == null) {
            section = getConfig().createSection("presents");
        }

        for (String key : section.getKeys(false)) {
            Present present = Present.load(key, section.getConfigurationSection(key));
            if (present != null) {
                presents.put(present.getLocation(), present);
                spawnPresent(present.getLocation());
            } else {
                getLogger().warning("Invalid present with id " + key);
            }
        }
    }

    public void addPresent(Present present) {
        Location loc = present.getLocation();
        presents.put(loc, present);

        ConfigurationSection section = getConfig().getConfigurationSection("presents");
        if (section == null) {
            section = getConfig().createSection("presents");
        }
        section = section.createSection(present.getId());
        present.save(section);

        saveConfig();
        spawnPresent(loc);
    }

    public void spawnPresent(Location loc) {
        CustomSkull.setBlock(loc, SKINS[new Random().nextInt(SKINS.length)]);
    }

    public Collection<Present> getPresents() {
        return presents.values();
    }

    public Optional<Present> getPresent(Location loc) {
        return Optional.ofNullable(presents.get(loc));
    }

    public PlayerDataStore getPlayerStore(Player player) {
        return getServer().getServicesManager().getRegistration(PlayerDataStoreService.class).getProvider().getStore(player);
    }

    public boolean hasAlreadyCollected(Player p, Present present) {
        return "true".equalsIgnoreCase(getPlayerStore(p).get("xmas2016.present." + present.getId()));
    }

    public void collect(Player p, Present present) {
        getPlayerStore(p).put("xmas2016.present." + present.getId(), "true");
        p.playSound(p.getLocation(), Sound.LEVEL_UP, 0.4f, 1);
        present.open(p);
    }
}
