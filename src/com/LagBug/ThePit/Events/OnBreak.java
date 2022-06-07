package com.LagBug.ThePit.Events;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.LagBug.ThePit.Main;


public class OnBreak implements Listener {

	private Main main = Main.getPlugin(Main.class);

	@EventHandler
	public void onPlace(final BlockBreakEvent e) {
		Player player = e.getPlayer();
		
		if ((main.playerArena.get(player) != null && !main.playerArena.get(player).equals("")) || (main.getDataFile().getString("lobby.world") != null && player.getWorld().getName().equals(main.getDataFile().getString("lobby.world")))) {
			if (!(main.adminmode.contains(player))) {
				if (!(e.getBlock().getType().equals(Material.COBBLESTONE) || e.getBlock().getType().equals(Material.OBSIDIAN))) { 
					e.setCancelled(true);
				}
			}	
		}
	}
}