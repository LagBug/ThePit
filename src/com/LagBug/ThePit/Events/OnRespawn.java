package com.LagBug.ThePit.Events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.Others.GiveItems;

public class OnRespawn implements Listener {
	
	private Main main = Main.getPlugin(Main.class);
	private GiveItems gi;
	
	public OnRespawn() {
		this.gi = new GiveItems();
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player killed = e.getPlayer();
		FileConfiguration afile = main.getArenaFile();


		if (afile.getConfigurationSection("arenas") != null && main.playerArena.get(killed) != null && !main.playerArena.get(killed).equals("")) {
			gi.setupItems(killed);
			for (String s : afile.getConfigurationSection("arenas").getKeys(false)) {
				if (afile.getString("arenas." + s + ".spawn..world")!= null && afile.getString("arenas." + s + ".name").equals(main.playerArena.get(killed))) {
					String path = "arenas." + s + ".spawn";
					World world = Bukkit.getWorld(afile.getString(path + ".world"));
					long pitch = Math.round(afile.getDouble(path + ".pitch"));
					long yaw = Math.round(afile.getDouble(path + ".yaw"));
					long x = Math.round(afile.getDouble(path + ".x")) ;
					long y = Math.round(afile.getDouble(path + ".y"));
					long z = Math.round(afile.getDouble(path + ".z"));
					
					Location loc = new Location(world, x, y, z, yaw, pitch);
					e.setRespawnLocation(loc);		
				}
			}

		}
	}

		

}

