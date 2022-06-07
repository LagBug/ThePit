package com.LagBug.ThePit.Commands.PitCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;

public class AddLevel {

	public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
		Player player = (Player) sender;

		if (!player.hasPermission("pit.admin.addlevel") || !player.hasPermission("pit.admin.*") || !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("general.no-permission"));
			return false;
		}
		if (args.length <= 2) {
			player.sendMessage(main.getMessage("commands.right-usage").replace("%usage%", "/pit addlevel <player> <amount>"));
		} else if (args.length >= 3) {
			Player leveledupPlayer = Bukkit.getPlayer(args[1]);
			int level = 0;
			try {
				level = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				player.sendMessage(main.getMessage("commands.must-be-number"));
				return false;
			}
			if (leveledupPlayer == null) {
				player.sendMessage(main.getMessage("commands.player-not-found").replace("%player%", args[1]));
			} else {
				if (level < 0 || level > 120) {
					player.sendMessage(main.getMessage("commands.level.between"));
				} else {
					player.sendMessage(main.getMessage("commands.level.add").replace("%player%", leveledupPlayer.getName()).replace("%amount%", Integer.toString(level)));
					leveledupPlayer.setLevel(leveledupPlayer.getLevel() + level);
				}

			}
		}

		return false;
	}
}
