package com.LagBug.ThePit.GUIListners;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.GUIs.ItemsGUI;
import com.LagBug.ThePit.Others.PlayerManager;

import net.md_5.bungee.api.ChatColor;

public class ItemsGUIListener implements Listener {

	private Main main = Main.getPlugin(Main.class);
	private YamlConfiguration ifile = main.getItemsFile();
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();

		if (e.getCurrentItem() == null || e.getClickedInventory() == null || e.getInventory() == null || e.getCurrentItem().getType().equals(Material.AIR)) return;
		if (e.getClickedInventory().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', ifile.getString("title")))) {
			double getGold = main.getDataFile().getDouble("pdata." + player.getUniqueId().toString() + ".gold");
			e.setCancelled(true);
			int price = 0;

			String[] colors = ifile.getString("availabilityColors").split(":");
			String[] messages = ifile.getString("availabilityMessages").split(":");
			
			for (String key : ifile.getConfigurationSection("items").getKeys(false)) {
				if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', ifile.getString("items." + key + ".name").replace("%color%", getGold >= ifile.getInt("items." + key + ".price") ? colors[0] : colors[1]).replace("%price%", Integer.toString(ifile.getInt("items." + key + ".price"))).replace("%message%", getGold >= ifile.getInt("items." + key + ".price") ? messages[0] : messages[1])))) {
					price = ifile.getInt("items." + key + ".price");
				}
			}
			
			ItemStack newItem = new ItemStack(e.getCurrentItem().getType(), e.getCurrentItem().getAmount(), e.getCurrentItem().getDurability());
			
			if (getGold >= price) {
				
				switch (getArmorType(e.getCurrentItem())) {
				case "helmet":
					player.getInventory().addItem(player.getInventory().getHelmet());
					player.getInventory().setHelmet(newItem);
					break;
				case "chestplate":
					player.getInventory().addItem(player.getInventory().getChestplate());
					player.getInventory().setChestplate(newItem);
					break;
				case "leggings":
					player.getInventory().addItem(player.getInventory().getLeggings());
					player.getInventory().setLeggings(newItem);
					break;
				case "boots":
					player.getInventory().addItem(player.getInventory().getBoots());
					player.getInventory().setBoots(newItem);
					break;
				default:
					player.getInventory().addItem(newItem);
					break;
				}
				
				new PlayerManager(player).removeGold(price);
				player.sendMessage(main.getMessage("guis.bought").replace("%item%", ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())));
			} else {
				player.sendMessage(main.getMessage("guis.not-enough-gold"));
			}

			player.closeInventory();
			new ItemsGUI().OpenGUI(player);
		}
	}
	
	private String getArmorType(ItemStack item) {
		String type = item.getType().toString().toLowerCase();
		
		if (type.contains("helmet")) {
			return "helmet";
		} else if (type.contains("chestplate")) {
			return "chestplate";
		} else if (type.contains("leggings")) {
			return "leggings";
		} else if (type.contains("boots")) {
			return "boots";
		}
		return "";
	}
}