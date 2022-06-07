package com.LagBug.ThePit.Events;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.Others.TabList;


public class OnLevelUp implements Listener {

	private Main main = Main.getPlugin(Main.class);

	@SuppressWarnings("deprecation")
	@EventHandler
	public void OnLevelup(PlayerLevelChangeEvent e) {
		
		Player player = e.getPlayer();
		player.sendTitle("hello", "there");
		int newLevel = player.getLevel();
		int oldLevel = player.getLevel() - 1;
		if (newLevel >= 121) {
			player.setLevel(120);
			return;
		}
		if (newLevel <= -1) {
			player.setLevel(0);
			return;
		}
		
		player.sendTitle(main.getConfig().getString("titles.level-up-title.title").replace("&", "§").replace("%oldlevel%", Integer.toString(oldLevel)).replace("%newlevel%", Integer.toString(newLevel).replace("%player%", player.getName())), main.getConfig().getString("titles.level-up-title.subtitle").replace("&", "§").replace("%oldlevel%", Integer.toString(oldLevel)).replace("%newlevel%", Integer.toString(newLevel).replace("%player%", player.getName())));
	
		player.sendMessage(main.getConfig().getString("messages.level-up-message").replace("&", "§").replace("%oldlevel%", Integer.toString(oldLevel)).replace("%newlevel%", Integer.toString(newLevel).replace("%player%", player.getName())));
		TabList.setTabName(player, main);
	}


}