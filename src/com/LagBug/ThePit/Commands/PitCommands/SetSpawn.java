package com.LagBug.ThePit.Commands.PitCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.Others.StringUtils;

public class SetSpawn {
	
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
    	boolean found = false;
    	
    	Player player = (Player) sender; 
    	FileConfiguration afile = main.getArenaFile();
    	
		if (!player.hasPermission("pit.admin.setspawn") || !player.hasPermission("pit.admin.*") || !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("general.no-permission"));
			return false;
		}
		
		if (args.length <= 1) {
			player.sendMessage(main.getMessage("commands.right-usage").replace("%usage%", "/pit setspawn <arena>"));
			return false;
		}
		
		if (afile.getConfigurationSection("arenas") == null) {		
			player.sendMessage(main.getMessage("commands.arena.not-found"));
			return false;
		}

		for (String s : main.getArenaFile().getConfigurationSection("arenas").getKeys(false)) {
			if (args[1].equalsIgnoreCase(afile.getString("arenas." + s + ".name"))) {
				afile.set("arenas." + s + ".spawn", StringUtils.SringFromLoc(player.getLocation()));
				main.saveFiles();
				player.sendMessage(main.getMessage("commands.arena.setspawn"));
				found = true;
				break;
			}
		}
		
		if (!found) {
			player.sendMessage(main.getMessage("commands.arena.not-found"));
		}

    	return false;
    }
}