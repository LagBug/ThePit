package com.LagBug.ThePit.Events;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.Others.TabList;
import com.LagBug.ThePit.Others.UpdateResult;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;


public class OnJoin implements Listener {
	
	private Main main = Main.getPlugin(Main.class);
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		FileConfiguration dfile = main.getDataFile();
		final Player player = e.getPlayer();
		
		if (main.isFighting.contains(player)) main.isFighting.remove(player);
		TabList.setTabName(player, main);
		
	if (main.getDataFile().getStringList("pdata." + player.getUniqueId().toString() + ".boughtUpgrades").contains("gatobattler")) main.ElGato.add(player);
		
		if (dfile.getString("lobby.world") != null) {
			World world = Bukkit.getWorld(dfile.getString("lobby.world"));
			long pitch = Math.round(dfile.getDouble("lobby.pitch"));
			long yaw = Math.round(dfile.getDouble("lobby.yaw"));
			long x = Math.round(dfile.getDouble("lobby.x"));
			long y = Math.round(dfile.getDouble("lobby.y"));
			long z = Math.round(dfile.getDouble("lobby.z"));
			
			Location loc = new Location(world, x, y, z, yaw, pitch);
			player.teleport(loc);	
		}
		
		if (player.isOp() && main.updater.getResult().equals(UpdateResult.FOUND)) {
			for (String s : main.getMessagesFile().getStringList("general.update-found")) {
				TextComponent msg = new TextComponent(ChatColor.translateAlternateColorCodes('&', s.replace("%link%", "https://www.spigotmc.org/resources/61016/")));
				
				if (s.contains("%link%") || s.contains("https://www.spigotmc.org/resources/61016/")) { 
					msg.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&6Click this message to open the url.")).create()));
					msg.setClickEvent( new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/61016/"));
				}
				player.spigot().sendMessage(msg);	
			}
		}
		

	}

}