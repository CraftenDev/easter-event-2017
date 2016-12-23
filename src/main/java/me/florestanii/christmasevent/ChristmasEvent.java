package me.florestanii.christmasevent;

import java.util.ArrayList;
import java.util.Random;

import me.florestanii.christmasevent.commands.PresentCommand;
import me.florestanii.christmasevent.util.CustomSkull;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class ChristmasEvent extends JavaPlugin{

	private final ArrayList<Location> presents = new ArrayList<Location>();
	
	private final String[] skins = new String[]{
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
	
	@Override
	public void onLoad() {
		
		super.onLoad();
	}
	
	@Override
	public void onEnable() {
		
		getCommand("addpresent").setExecutor(new PresentCommand(this));
		
		loadPresents();
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		
		super.onDisable();
	}
	
	public void loadPresents() {
		ConfigurationSection section = getConfig().getConfigurationSection("presents");
		
		if (section == null) {
			section = getConfig().createSection("presents");
		}
		
		for (String key : section.getKeys(false)) {
			Location loc = loadLocation(section.getConfigurationSection(key));
			loadPresent(loc);
		}
		
	}
	
	public void addPresent(Location loc) {
		presents.add(loc);
		
		ConfigurationSection section = getConfig().getConfigurationSection("presents");
		
		if (section == null) {
			section = getConfig().createSection("presents");
		}
		
		saveLocation(section.createSection("" + (presents.size()-1)), loc);
		
		saveConfig();
		
		loadPresent(loc);
		
	}
	
	public void loadPresent(Location loc) {
		CustomSkull.setBlock(loc, skins[new Random().nextInt(skins.length)]);
	}
	
	public ArrayList<Location> getPresents() {
		return presents;
	}
	
	public Location loadLocation(ConfigurationSection section) {
		try {
			return new Location(getServer().getWorld(section.getString("world")), section.getDouble("x"), section.getDouble("y"), section.getDouble("z"),(float) section.getDouble("yaw"),(float) section.getDouble("pitch"));
		} catch (Exception e) {
			return new Location(null, section.getDouble("x"), section.getDouble("y"), section.getDouble("z"),(float) section.getDouble("yaw"),(float) section.getDouble("pitch"));
		}
		
	}
	
	public void saveLocation(ConfigurationSection section, Location loc) {
		if (loc != null) {
			section.set("world", loc.getWorld().getName());
			section.set("x", loc.getX());
			section.set("y", loc.getY());
			section.set("z", loc.getZ());
			section.set("yaw", loc.getYaw());
			section.set("pitch", loc.getPitch());
		}
	}
}
