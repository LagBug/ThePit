package com.LagBug.ThePit.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.LagBug.ThePit.Main;

public class OnMobSpawn implements Listener {

	private Main main = Main.getPlugin(Main.class);
	
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent e) {
		if (main.getConfig().getBoolean("general.spawn-mobs") == false) {
			e.setCancelled(true);
		}

	}
}