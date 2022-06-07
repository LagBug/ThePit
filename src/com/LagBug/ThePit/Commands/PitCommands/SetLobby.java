package com.LagBug.ThePit.Commands.PitCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.Others.StringUtils;

public class SetLobby {
	
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
    	
    	Player player = (Player) sender; 
    	FileConfiguration afile = main.getArenaFile();
    	
		if (!player.hasPermission("pit.admin.setlobby") || !player.hasPermission("pit.admin.*") || !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("general.no-permission"));
			return false;
		}
		
		afile.set("lobby", StringUtils.SringFromLoc(player.getLocation()));
		main.saveFiles();
		player.sendMessage(main.getMessage("commands.setlobby.set"));
    	
    	return false;
    }
}
