package com.LagBug.ThePit.Commands.PitCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;

public class Delete {
	
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
    	boolean found = false;
    	Player player = (Player) sender; 
    	FileConfiguration afile = main.getArenaFile();
    	int counter = afile.getInt("arenasCounter");
    	
		if (!player.hasPermission("pit.admin.delete") || !player.hasPermission("pit.admin.*") || !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("general.no-permission"));
			return false;
		}
		
		if (args.length <= 1) {
			player.sendMessage(main.getMessage("commands.right-usage").replace("%usage%", "/pit delete <name>"));
			return false;
		}

		if (afile.getConfigurationSection("arenas") == null || counter <= 0 ) {
			player.sendMessage(main.getMessage("commands.arena.not-found"));
			return false;
		}
		
			for (String s : afile.getConfigurationSection("arenas").getKeys(false)) {
				
				if (args[1].equalsIgnoreCase(afile.getString("arenas." + s + ".name"))) {
					afile.set("arenas." + s, null);
					afile.set("arenasCounter", counter - 1);
					main.saveFiles();
					player.sendMessage(main.getMessage("commands.arena.delete").replace("%arena%", args[1]));
					found = true;
					break;
				}					
			}
			
			if (!found) {
				player.sendMessage(main.getMessage("commands.arena.not-found"));
				found = false;
			}
			
	
		
    	
    	return false;
    }
}
