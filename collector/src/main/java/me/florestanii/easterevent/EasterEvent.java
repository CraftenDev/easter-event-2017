package me.florestanii.easterevent;

import de.craften.plugins.playerdatastore.api.PlayerDataStore;
import de.craften.plugins.playerdatastore.api.PlayerDataStoreService;
import me.florestanii.easterevent.commands.EggCommand;
import me.florestanii.easterevent.handler.EggInteractHandler;
import me.florestanii.easterevent.handler.EggParticles;
import me.florestanii.easterevent.util.CustomSkull;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class EasterEvent extends JavaPlugin {
    private static final String[] SKINS = new String[]{
            "http://textures.minecraft.net/texture/8c50aee88013e8faf427e19f3b89828b86bbf03dd2f17c4c760d1dde22de3",
            "http://textures.minecraft.net/texture/eeb335182db5f3be80fccf6eabe599f4107d4ff0e9f44f34174cefa6e2b5768",
            "http://textures.minecraft.net/texture/b2cd5df9d7f1fa8341fcce2f3c118e2f517e4d2d99df2c51d61d93ed7f83e13",
            "http://textures.minecraft.net/texture/c76595effcc5627e85b14c9c8824671b5ec2965659c8c417849a667878fa490",
            "http://textures.minecraft.net/texture/4eb5abb123527a7a1f5c9589715964b59f76824926d3b982fa8411d4606c79",
            "http://textures.minecraft.net/texture/264430e493feb5eaa145582e54e761a8603fb16cc0ff1268a5d1e864e6f479f6",
            "http://textures.minecraft.net/texture/b3d69b23ae592c647eb8dceb9daace44139f748e734dc84962613c366a08b",
            "http://textures.minecraft.net/texture/656f7f3f3536506626d5f35b45d7fdf2d8aab26008456659efeb91e4c3a9c5",
            "http://textures.minecraft.net/texture/58b9e29ab1a795e2b887faf1b1a31025e7cc3073330afec375393b45fa335d1",
            "http://textures.minecraft.net/texture/fb8b2bdfc5d13b9f45990ee2eaa82e44afb2a69b56ac9276a13292b24c3e1de",
            "http://textures.minecraft.net/texture/9889f11c8838c09e1ecf2f83439ebcb9f324e567b0e9dc4b7c25d93e50ff2b",
            "http://textures.minecraft.net/texture/83c25a7a188196b18717264ffe837ca348cf719e827179edc4b78cbcb8c7dd8"};

    private final Map<Location, Egg> eggs = new HashMap<>();

    @Override
    public void onEnable() {
        getCommand("addegg").setExecutor(new EggCommand(this));
        getServer().getPluginManager().registerEvents(new EggInteractHandler(this), this);
        loadEggs();

        getServer().getScheduler().scheduleSyncRepeatingTask(this, new EggParticles(this), 0, 4);
    }

    public void loadEggs() {
        ConfigurationSection section = getConfig().getConfigurationSection("eggs");

        if (section == null) {
            section = getConfig().createSection("eggs");
        }

        for (String key : section.getKeys(false)) {
            Egg egg = Egg.load(key, section.getConfigurationSection(key));
            if (egg != null) {
                eggs.put(egg.getLocation(), egg);
                spawnEgg(egg.getLocation());
            } else {
                getLogger().warning("Invalid egg with id " + key);
            }
        }
    }

    public void addEgg(Egg egg) {
        Location loc = egg.getLocation();
        eggs.put(loc, egg);

        ConfigurationSection section = getConfig().getConfigurationSection("eggs");
        if (section == null) {
            section = getConfig().createSection("eggs");
        }
        section = section.createSection(egg.getId());
        egg.save(section);

        saveConfig();
        spawnEgg(loc);
    }

    public void spawnEgg(Location loc) {
        CustomSkull.setBlock(loc, SKINS[new Random().nextInt(SKINS.length)]);
    }

    public Collection<Egg> getEggs() {
        return eggs.values();
    }

    public Optional<Egg> getEgg(Location loc) {
        return Optional.ofNullable(eggs.get(loc));
    }

    public PlayerDataStore getPlayerStore(Player player) {
        return getServer().getServicesManager().getRegistration(PlayerDataStoreService.class).getProvider().getStore(player);
    }

    public boolean hasAlreadyCollected(Player p, Egg egg) {
        return "true".equalsIgnoreCase(getPlayerStore(p).get("easter2017.egg." + egg.getId()));
    }

    public void collect(Player p, Egg egg) {
        getPlayerStore(p).put("easter2017.egg." + egg.getId(), "true");
        p.playSound(p.getLocation(), Sound.LEVEL_UP, 0.4f, 1);
        egg.open(p);
    }
}
