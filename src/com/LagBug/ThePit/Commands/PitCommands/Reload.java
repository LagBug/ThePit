package com.LagBug.ThePit.Commands.PitCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;

public class Reload {
	
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
    	Player player = (Player) sender; 
    	
		if (!player.hasPermission("pit.admin.reload") || !player.hasPermission("pit.admin.*") || !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("general.no-permission"));
			return false;
		}
			
		main.saveFiles();
		player.sendMessage(main.getMessage("commands.reload.success"));
    	
    	return false;
    }
}
