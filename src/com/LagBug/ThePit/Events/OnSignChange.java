package com.LagBug.ThePit.Events;


import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.LagBug.ThePit.Main;


public class OnSignChange implements Listener {

	private Main main = Main.getPlugin(Main.class);
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		boolean work = false;
		Player player = e.getPlayer();
		
		if (!player.hasPermission("pit.admin.sign") || !player.hasPermission("pit.admin.*") || !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("general.no-permission"));
			return;
		}
		
		if (e.getLine(0).equalsIgnoreCase("[thepit]") || e.getLine(0).equalsIgnoreCase("[pit]")) {
			if (main.getArenaFile().getConfigurationSection("arenas") == null) {
				player.sendMessage(main.getMessage("commands.arena.not-found"));
			} else {
				for (String s : main.getArenaFile().getConfigurationSection("arenas").getKeys(false)){
					if (e.getLine(1).equalsIgnoreCase(main.getArenaFile().getString("arenas." + s + ".name"))){
						List<String> list = main.getConfig().getStringList("arenas." + s + ".signs");
						list.add(loc2str(e.getBlock().getLocation()));
						main.getArenaFile().set("arenas." + s + ".signs", list);
						main.saveFiles();
						work = true;
						break;
						}
					}
				
				if (!work) {
					e.setLine(0, ChatColor.translateAlternateColorCodes('&', "&8[&6&lThePit&8]"));
					e.setLine(1, ChatColor.translateAlternateColorCodes('&', "&8Arena not found"));
				}
	
			}			
		}

			}

 
    public String loc2str(Location loc){
        return loc.getWorld().getName()+":"+loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ();
    }
}
