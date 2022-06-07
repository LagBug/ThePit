package com.LagBug.ThePit.Events;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.LagBug.ThePit.Main;


public class OnLeave implements Listener{

	private Main main = Main.getPlugin(Main.class);
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		FileConfiguration dfile = main.getDataFile();
		Player player = e.getPlayer();
		Location loc = null;
		if (dfile.getString("lobby") != null) {
			World world = Bukkit.getWorld(dfile.getString("lobby.world"));
			double x = dfile.getDouble("lobby.x");
			double y = dfile.getDouble("lobby.y");
			double z = dfile.getDouble("lobby.z");
			float yaw = (float) dfile.getDouble("lobby.yaw");
			float pitch = (float) dfile.getDouble("lobby.pitch");			

			loc = new Location(world, x, y, z, yaw, pitch);
		}
		if (main.playerArena.get(player) != null && !main.playerArena.get(player).equals("")) {
			main.arenaCounter.put(main.playerArena.get(player), main.arenaCounter.get(main.playerArena.get(player))-1);
			main.playerArena.put(player, "");
			player.getInventory().clear();
			if (loc != null) player.teleport(loc);
			
		}
	}
}