package com.LagBug.ThePit.Commands.PitCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.Others.PlayerManager;

public class Join {
	
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
    	Player player = (Player) sender;
    	boolean work = false;
    	boolean works = false;
    	FileConfiguration afile = main.getArenaFile();
    	PlayerManager pm = new PlayerManager(player);
    	
		if (!player.hasPermission("pit.user.join") || !player.hasPermission("pit.user.*") || !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("general.no-permission"));
			return false;
		}
		
		if (args.length <= 1) {
			player.sendMessage(main.getMessage("commands.right-usage").replace("%usage%", "/pit join <arena>"));
			return false;
		}
		
		if (afile.getConfigurationSection("arenas") == null) {
			player.sendMessage(main.getMessage("commands.arena.not-found"));
			return false;
		}
				
		for (String s : main.getArenaFile().getConfigurationSection("arenas").getKeys(false)){
			if (args[1].equalsIgnoreCase(afile.getString("arenas." + s + ".name"))) {
				work = true;
				if (afile.getString("arenas." + s + ".spawn") != null) {
					pm.joinArena(s);
					works = true;
					break;
				}					
			}
	}
	
	if (!work) {		
		player.sendMessage(main.getMessage("commands.arena.not-found"));
		return false;
	}
	
	if (!works) {		
		player.sendMessage(main.getMessage("commands.arena.no-spawn-loc"));
		return false;
	}

    	
    	return false;
    }
}
