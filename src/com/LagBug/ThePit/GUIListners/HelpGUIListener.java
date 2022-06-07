package com.LagBug.ThePit.GUIListners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class HelpGUIListener implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		if (e.getCurrentItem() == null || e.getClickedInventory() == null || e.getInventory() == null || e.getCurrentItem().getType().equals(Material.AIR)) return;
		if (e.getClickedInventory().getTitle().contains("ThePit Help Menu")) {
				e.setCancelled(true);
				player.closeInventory();
				hoverMessage(player, e.getCurrentItem().getItemMeta().getDisplayName().replace(ChatColor.translateAlternateColorCodes('&', "&8&l> &6"), ""));
		}

				
	}
	
    public void hoverMessage(Player player, String command) {
        TextComponent msg = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&8&l > &6Click &ehere &6to perform the command!"));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&6Yup, right here!")).create()));
        
        player.spigot().sendMessage(msg);
        
    }
}
