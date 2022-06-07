package com.LagBug.ThePit.Commands.PitCommands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;

public class Launchpad {
	
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
    	
    	Player player = (Player) sender; 
    	Location loc = player.getLocation();
    	
		if (!player.hasPermission("pit.admin.launchpad") || !player.hasPermission("pit.admin.*") || !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("general.no-permission"));
			return false;
		}
		
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		
		loc.getWorld().getBlockAt(loc).getRelative(0, -1, 0).setType(Material.SLIME_BLOCK);
		Location newLoc = new Location(player.getWorld(), x, y + 1.5, z);

		if (player.getAllowFlight() == false) {
			player.setAllowFlight(true);
			player.setFlying(true);	
			
			
			Bukkit.getScheduler().runTaskLaterAsynchronously(main, () -> {
				player.setAllowFlight(false);
				player.setFlying(false);				
			}, 100);

		newLoc.setYaw(loc.getYaw());
		newLoc.setPitch(loc.getPitch());
		player.teleport(newLoc);
		player.sendMessage(main.getMessage("commands.launchpad.add"));
    	
    }
	
	return false;
   }
}