package com.LagBug.ThePit.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import com.LagBug.ThePit.Main;

public class OnHunger implements Listener {

	private Main main = Main.getPlugin(Main.class);

	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		Player player = (Player)e.getEntity();
		if (player == null) return;
		if ((main.playerArena.get(player) != null && !main.playerArena.get(player).equals("")) || (main.getDataFile().getString("lobby.world") != null && player.getWorld().getName().equals(main.getDataFile().getString("lobby.world")))) {
			if (main.getConfig().getBoolean("general.lose-hunger") == false) {
				e.setCancelled(true);
				e.setFoodLevel(20);
			}			
		}


	}
}