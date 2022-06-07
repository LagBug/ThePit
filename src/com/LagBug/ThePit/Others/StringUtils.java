package com.LagBug.ThePit.Others;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class StringUtils {
	
	public static Location LocFromString(String string) {
		String[] loc = string.split(":");
		return new Location(Bukkit.getWorld(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]),
				Double.parseDouble(loc[3]), (float) Double.parseDouble(loc[4]), (float) Double.parseDouble(loc[5]));
	}

	public static String SringFromLoc(Location loc) {
		return loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw()
				+ ":" + loc.getPitch();
	}
}
