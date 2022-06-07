package com.LagBug.ThePit.Commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.GUIs.PerksGUI;


public class PerksCommand implements CommandExecutor {
	private Main main = Main.getPlugin(Main.class);
	private PerksGUI pgui;
	
	public PerksCommand () {
		this.pgui = new PerksGUI(main);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player)) {
			if (cmd.getName().equalsIgnoreCase("perks")) {
				Bukkit.getConsoleSender().sendMessage(main.getMessage("general.no-console"));
			}
		} else {
			Player player = (Player) sender;
			if (!player.hasPermission("pit.user.perks") || !player.hasPermission("pit.user.*")) {
				player.sendMessage(main.getMessage("general.no-permission"));
			} else {
				if (main.playerArena.get(player) == null || main.playerArena.get(player).equals("")) {
					player.sendMessage(main.getMessage("commands.arena.not-in-arena"));	
					return false;
				}
				pgui.OpenGUI(player);
		}
	}
		return false;
  }
}