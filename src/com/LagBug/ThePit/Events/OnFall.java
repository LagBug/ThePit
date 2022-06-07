package com.LagBug.ThePit.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.LagBug.ThePit.Main;

public class OnFall implements Listener {

	private Main main = Main.getPlugin(Main.class);

	@EventHandler
	public void onFall(EntityDamageEvent e) {
		Player player = (Player) e.getEntity();
		if (player == null) return;
		if ((main.playerArena.get(player) != null && !main.playerArena.get(player).equals("")) || (main.getDataFile().getString("lobby.world") != null && player.getWorld().getName().equals(main.getDataFile().getString("lobby.world")))) {
			if (main.getConfig().getBoolean("general.fall-damage") == false) {
				if (e.getCause() == DamageCause.FALL) {
					e.setCancelled(true);
				}
			}	
		}
	}
}
