package com.LagBug.ThePit.Commands.PitCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.Others.PlayerManager;

public class AddGold {

	public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
		
		Player player = (Player) sender;
		PlayerManager pm = new PlayerManager(player);

		if (!player.hasPermission("pit.admin.addgold") || !player.hasPermission("pit.admin.*") || !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("general.no-permission"));
			return false;
		}
		if (args.length <= 2) {
			player.sendMessage(main.getMessage("commands.right-usage").replace("%usage%", "/pit addgold <player> <amount>"));
		} else if (args.length >= 3) {
			Player target = Bukkit.getPlayer(args[1]);

			int amount = 0;
			try {
				amount = Integer.parseInt(args[2]);
			} catch (NumberFormatException ex) {
				player.sendMessage(main.getMessage("commands.must-be-number"));
				return false;
			}
			if (target == null) {
				player.sendMessage(main.getMessage("commands.player-not-found").replace("%player%", args[1]));
			} else {
				player.sendMessage(main.getMessage("commands.gold.add"));
				pm.addGold(amount);
			}
		}

		return false;
	}
}
