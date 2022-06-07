package com.LagBug.ThePit.Commands.PitCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.Others.CustomScoreboard;
import com.LagBug.ThePit.Others.StringUtils;

public class Leave {

	public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
		Player player = (Player) sender;
		FileConfiguration afile = main.getArenaFile();

		if (!player.hasPermission("pit.user.leave") || !player.hasPermission("pit.user.*") || !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("general.no-permission"));
			return false;
		}

		if (afile.getString("lobby") != null) {

			if (main.playerArena.get(player) != null && !main.playerArena.get(player).equals("")) {
				main.arenaCounter.put(main.playerArena.get(player), main.arenaCounter.get(main.playerArena.get(player)) - 1);
				new CustomScoreboard(main).disableScoreboard(player);
				player.teleport(StringUtils.LocFromString(afile.getString("lobby")));
				player.getInventory().clear();

				player.sendMessage(main.getMessage("commands.arena.leave").replace("%arena%", main.playerArena.get(player)));
				main.playerArena.put(player, "");
			} else {
				player.sendMessage(main.getMessage("commands.arena.not-in-arena"));
			}

		} else {
			player.sendMessage(main.getMessage("commands.arena.no-lobby-loc"));
		}

		return false;
	}
}
