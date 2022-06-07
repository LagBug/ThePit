package com.LagBug.ThePit.Events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.LagBug.ThePit.Main;

public class OnMove implements Listener {
	
	private Main main = Main.getPlugin(Main.class);

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Block stand = e.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
		if (stand.getType() == Material.SLIME_BLOCK) {
			double lpPower = main.getConfig().getDouble("general.launchpad-power");
			e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(lpPower));
			e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getX(), 1.0D, e.getPlayer().getVelocity().getZ()));
		}
	}
}
