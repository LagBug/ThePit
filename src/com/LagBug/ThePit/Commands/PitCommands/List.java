package com.LagBug.ThePit.Commands.PitCommands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;

public class List {

	public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
		Player player = (Player) sender;
		java.util.List<String> arenas = new ArrayList<>();	
		
		if (!player.hasPermission("pit.user.list") || !player.hasPermission("pit.user.*")|| !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("general.no-permission"));
			return false;
		}

		if (main.getArenaFile().getConfigurationSection("arenas") == null) {
			player.sendMessage(main.getMessage("commands.arena.not-found"));
			return false;
		}
		
		for (String s : main.getArenaFile().getConfigurationSection("arenas").getKeys(false)) {
			arenas.add(main.getArenaFile().getString("arenas." + s + ".name"));
		}
		
		player.sendMessage(main.getMessage("commands.arena.list").replace("%arenas%", arenas.toString()));
		
		return false;
	}
}