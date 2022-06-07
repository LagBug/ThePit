package com.LagBug.ThePit.Commands.PitCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;

public class SetMax {
	
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
    	
    	Player player = (Player) sender; 
    	boolean work = false;
    	FileConfiguration afile = main.getArenaFile();
    	
		if (!player.hasPermission("pit.admin.setmax") || !player.hasPermission("pit.admin.*") || !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("general.no-permission"));
			return false;
		}
		
		if (args.length <= 2) {
			player.sendMessage(main.getMessage("commands.right-usage").replace("%usage%", "/pit setmax <amount> <arena>"));
			return false;
		}
		
		if (afile.getConfigurationSection("arenas") == null) {		
			player.sendMessage(main.getMessage("commands.arena.not-found"));
			return false;
		}
				
		for (String s : main.getArenaFile().getConfigurationSection("arenas").getKeys(false)) {
			if (args[2].equalsIgnoreCase(afile.getString("arenas." + s + ".name"))) {
				int num = 0;
				try {
					num = Integer.parseInt(args[1]);
					} catch(NumberFormatException ex) {
					  player.sendMessage(main.getMessage("commands.must-be-number"));
					  return false;
					}
				
				afile.set("arenas." + s + ".maxPlayers", num);
				main.saveFiles();
				player.sendMessage(main.getMessage("commands.arena.setmax"));
				work = true;
				break;
			}
		}
		
		if (!work) {
			player.sendMessage(main.getMessage("commands.arena.not-found"));
		}

    	
    	return false;
    }
}
