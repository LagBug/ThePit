package com.LagBug.ThePit.GUIs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.LagBug.ThePit.Main;

public class ItemsGUI {

	private Main main = Main.getPlugin(Main.class);
	private YamlConfiguration ifile = main.getItemsFile();

	public void OpenGUI(Player player) {
		double getGold = main.getDataFile().getDouble("pdata." + player.getUniqueId().toString() + ".gold");
		Inventory gui = Bukkit.createInventory(null, ifile.getInt("slots"), ChatColor.translateAlternateColorCodes('&', ifile.getString("title")));
		String[] colors = ifile.getString("availabilityColors").split(":");
		String[] messages = ifile.getString("availabilityMessages").split(":");

		for (String key : ifile.getConfigurationSection("items").getKeys(false)) {
			String[] mats = ifile.getString("items." + key + ".material").split(":");
			List<String> lore = new ArrayList<>();

			ItemStack item = new ItemStack(Material.valueOf(mats[0].toUpperCase()), Integer.parseInt(mats[1]), (byte) Integer.parseInt(mats[2]));
			ItemMeta meta = item.getItemMeta();

			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', ifile.getString("items." + key + ".name").replace("%color%", getGold >= ifile.getInt("items." + key + ".price") ? colors[0] : colors[1]).replace("%price%", Integer.toString(ifile.getInt("items." + key + ".price"))).replace("%message%", getGold >= ifile.getInt("items." + key + ".price") ? messages[0] : messages[1])));
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			for (String loreC : ifile.getStringList("items." + key + ".lore")) { lore.add(ChatColor.translateAlternateColorCodes('&', loreC.replace("%color%", getGold >= ifile.getInt("items." + key + ".price") ? colors[0] : colors[1]).replace("%price%", Integer.toString(ifile.getInt("items." + key + ".price"))).replace("%message%", getGold >= ifile.getInt("items." + key + ".price") ? messages[0] : messages[1]))); }
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			gui.setItem(ifile.getInt("items." + key + ".slot"), item);
		}
		player.openInventory(gui);
	}

}
