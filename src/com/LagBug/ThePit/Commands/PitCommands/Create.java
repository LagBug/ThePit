package com.LagBug.ThePit.Commands.PitCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;


public class Create {
	
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
    	Player player = (Player) sender; 
    	FileConfiguration afile = main.getArenaFile();
    	int counter = afile.getInt("arenasCounter");
    	
		if (!player.hasPermission("pit.admin.create") || !player.hasPermission("pit.admin.*") || !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("genral.no-permissions"));
			return false;
		}
		
		if (args.length <= 1) {
			player.sendMessage(main.getMessage("commands.right-usage").replace("%usage%", "/pit create <name>"));
			return false;
		}
		
		afile.set("arenas." + (counter + 1) + ".name", args[1]);
		afile.set("arenasCounter", counter + 1);
		main.saveFiles();
		player.sendMessage(main.getMessage("commands.arena.create").replace("%arena%", args[1]));
    	
    	return false;
    }
}
