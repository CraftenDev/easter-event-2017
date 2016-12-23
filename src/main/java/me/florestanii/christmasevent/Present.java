package me.florestanii.christmasevent;

import org.bukkit.Location;

public class Present {

	private final String id;
	private final Location loc;
	
	public Present(String id, Location loc) {
		this.id = id;
		this.loc = loc;
	}
	
	public String getId() {
		return id;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Present) {
			Present p = (Present) obj;
			return p.getId().equals(id) && p.getLocation().equals(loc);
		} else if (obj instanceof Location) {
			Location l = (Location) obj;
			return l.equals(loc);
		} else if (obj instanceof String) {
			String s = (String) obj;
			return s.equals(id);
		}
		return false;
	}
	
}
