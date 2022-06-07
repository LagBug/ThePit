package com.LagBug.ThePit.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.LagBug.ThePit.Main;

public class OnChat implements Listener {

	private Main main = Main.getPlugin(Main.class);

	@EventHandler
	public void AsyncPlayerChatEvent(AsyncPlayerChatEvent e) {

		Player player = e.getPlayer();
		int level = player.getLevel();

		e.setFormat(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("format.chat-format")
				.replace("%lvl%", main.getConfig().getString("leveling-system." + getLevel(player) + ".color") + level)
				.replace("%player%", player.getName())
				.replace("%message%", e.getMessage())));

	}

	public String getLevel(Player player) {

		int lvl = player.getLevel();
		int min = (int) Math.floor(lvl / 10.0) * 10;
		int max = (int) Math.ceil(lvl / 9.9) * 10 - 1;
		
		if (lvl <= 0) { max = 9; }
		if (lvl >= 120) { return "120"; }

		return min + "to" + max;

	}
}