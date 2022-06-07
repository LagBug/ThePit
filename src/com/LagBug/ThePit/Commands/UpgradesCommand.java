package com.LagBug.ThePit.Commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.GUIs.UpgradesGUI;


public class UpgradesCommand implements CommandExecutor {
	
	private Main main = Main.getPlugin(Main.class);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(main.getMessage("general.no-console"));
		} else {
			Player player = (Player) sender;
			if (!player.hasPermission("pit.user.upgrades") || !player.hasPermission("pit.user.*")) {
				player.sendMessage(main.getMessage("general.no-permission"));
			} else {
				if (main.playerArena.get(player) == null || main.playerArena.get(player).equals("")) {
					player.sendMessage(main.getMessage("commands.arena.not-in-arena"));	
					return false;
				}
				new UpgradesGUI().OpenGUI(player);
		}
	}
		return false;	
  }
}