package com.LagBug.ThePit.Commands;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.GUIs.ItemsGUI;


public class ItemsCommand implements CommandExecutor {
	
	private Main main = Main.getPlugin(Main.class);
	private ItemsGUI itemsgui;
	
	public ItemsCommand () {
		this.itemsgui = new ItemsGUI();
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			if (cmd.getName().equalsIgnoreCase("items")) {
				Bukkit.getConsoleSender().sendMessage(main.getMessage("general.no-console"));
			}
		} else {
			Player player = (Player) sender;
			if (!player.hasPermission("pit.user.items") || !player.hasPermission("pit.user.*")) {
				player.sendMessage(main.getMessage("general.no-permission"));
			} else {
				if (main.playerArena.get(player) == null || main.playerArena.get(player).equals("")) {
					player.sendMessage(main.getMessage("commands.arena.not-in-arena"));	
					return false;
				}
				itemsgui.OpenGUI(player);
		}
	}
		return false;	
  }
}