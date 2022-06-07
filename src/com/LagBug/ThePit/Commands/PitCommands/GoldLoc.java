package com.LagBug.ThePit.Commands.PitCommands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;

public class GoldLoc {
	
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
    	Player player = (Player) sender; 
    	FileConfiguration dfile = main.getDataFile();
    	Location loc = player.getLocation();
    	
		if (!player.hasPermission("pit.admin.goldloc") || !player.hasPermission("pit.admin.*") || !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("general.no-permission"));
			return false;
		}

		if (args.length <= 1) {
		player.sendMessage(main.getMessage("commands.right-usage").replace("%usage%", "/pit goldloc <add/rem>"));
		
		} else if (args.length >= 1) {
			
			if (args[1].equalsIgnoreCase("add")) {
				int goldLocsCounter = dfile.getInt("counters.goldLocsCounter");
				dfile.set("goldlocs." + (goldLocsCounter + 1) + ".world", loc.getWorld().getName());
				dfile.set("goldlocs." + (goldLocsCounter + 1) + ".x", loc.getX());
				dfile.set("goldlocs." + (goldLocsCounter + 1) + ".y", loc.getY());
				dfile.set("goldlocs." + (goldLocsCounter + 1) + ".z", loc.getZ());
				dfile.set("counters.goldLocsCounter", goldLocsCounter + 1);
				main.saveFiles();
				player.sendMessage(main.getMessage("commands.goldloc.add"));
				
			} else if (args[1].equalsIgnoreCase("rem") || (args[1].equalsIgnoreCase("remove"))) {
				int goldLocsCounter = dfile.getInt("counters.goldLocsCounter");
				dfile.set("goldlocs." + goldLocsCounter, null);
				dfile.set("counters.goldLocsCounter", goldLocsCounter - 1);
				main.saveFiles();
				player.sendMessage(main.getMessage("commands.goldloc.remove"));
				
			} else {
				player.sendMessage(main.getMessage("commands.right-usage").replace("%usage%", "/pit goldloc <add/rem>"));
			}
		}
    	
    	return false;
    }
}
