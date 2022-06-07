package com.LagBug.ThePit.Events;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.LagBug.ThePit.Main;

import net.md_5.bungee.api.ChatColor;


public class OnPickUp implements Listener {

	private Main main = Main.getPlugin(Main.class);
	
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		final Player player = e.getPlayer();
		String uuid = player.getUniqueId().toString();
		double gold = main.getDataFile().getDouble("pdata." + uuid + ".gold");
		double goldOnPickup = main.getConfig().getDouble("general.gold-on-pickup");
		
		
	      if (e.getItem().getItemStack().isSimilar(new ItemStack (Material.GOLD_INGOT))){	    			
	    	    if (main.getDataFile().getStringList("pdata." + player.getUniqueId().toString() + ".activePerks").contains("trickledown")) { goldOnPickup = goldOnPickup*7; }
    			player.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("messages.gold-pickup-message").replace("%amount%", Double.toString(goldOnPickup).replace("%player%", player.getName()))));
    			main.getDataFile().set("pdata." + uuid + ".gold", gold + (goldOnPickup * 7));
    			main.saveFiles();
	    		Bukkit.getScheduler().runTaskLater(main, new Runnable() {
	    			public void run() {
	    				player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 64));
	    			}
	    		}, 5);
                	
		}
	}
}