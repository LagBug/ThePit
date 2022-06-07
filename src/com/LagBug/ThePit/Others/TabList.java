package com.LagBug.ThePit.Others;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;

public class TabList {
	
	public static String getLevel(Player player) {
		int lvl = player.getLevel(); 
		int min = (int) Math.floor(lvl/10.0) * 10;
		int max = (int) Math.ceil(lvl/9.9) * 10 - 1;
		if (lvl <= 0) { max = 9; }
		if (lvl >= 120) { return "120"; }
	
		return min+"to"+max;
		
	}
	
	public static void setTabName(Player player, Main main) {
		int lvl = player.getLevel();

		player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', 
				main.getConfig().getString("format.tablist-format")
				.replace("%lvl%", ChatColor.translateAlternateColorCodes('&', 
					main.getConfig().getString("leveling-system." + getLevel(player) + ".color") + lvl))
						.replace("%player%", player.getName())));
	}
}
