package com.LagBug.ThePit.Commands.PitCommands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;

public class Adminmode {
	
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
    	Player player = (Player) sender; 
    	
		if (!player.hasPermission("pit.admin.adminmode") || !player.hasPermission("pit.admin.*") || !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("general.no-permission"));
			return false;
		}
		if (!main.adminmode.contains(player)) {
			main.adminmode.add(player);
			player.setGameMode(GameMode.CREATIVE);
			player.sendMessage(main.getMessage("commands.adminmode.enabled"));
		} else {
			main.adminmode.remove(player);
			player.setGameMode(GameMode.SURVIVAL);
			player.sendMessage(main.getMessage("commands.adminmode.disabled"));
		}
    	
    	return false;
    }
}
