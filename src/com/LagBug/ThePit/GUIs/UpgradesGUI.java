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
import com.LagBug.ThePit.Others.ItemBuilder;

public class UpgradesGUI {

	private Main main = Main.getPlugin(Main.class);
	private YamlConfiguration ufile = main.getUpgradesFile();

	public void OpenGUI(Player player) {
		Inventory gui = Bukkit.createInventory(null, ufile.getInt("slots"), ChatColor.translateAlternateColorCodes('&', ufile.getString("title")));


		for (String key : ufile.getConfigurationSection("items").getKeys(false)) {
			String[] mats = ufile.getString("items." + key + ".material").split(":");
			List<String> lore = new ArrayList<>();

			ItemStack item = new ItemStack(Material.valueOf(mats[0].toUpperCase()), Integer.parseInt(mats[1]), (byte) Integer.parseInt(mats[2]));
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', replace(ufile.getString("items." + key + ".name"), key, player)));
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			for (String loreC : ufile.getStringList("items." + key + ".lore")) { lore.add(ChatColor.translateAlternateColorCodes('&', replace(loreC, key, player))); }
			meta.setLore(lore);
			item.setItemMeta(meta);

			String[] perkMats = ufile.getString("not-available.perk.material").split(":");
			@SuppressWarnings("unused")
			ItemStack reqLevelPerk = new ItemBuilder(Material.valueOf(perkMats[0]), Integer.parseInt(perkMats[1]), Integer.parseInt(perkMats[2]))
					.setDisplayName(ufile.getString("not-available.perks.name"))
					.setLore(ufile.getStringList("not-available.perks.lore")).build();
			
			
			gui.setItem(ufile.getInt("items." + key + ".slot"), item);
		}
		player.openInventory(gui);
	}
	
	
	private String replace(String text, String key, Player player) {
		String nameid = ChatColor.stripColor(ufile.getString("items." + key + ".name").replace("%color%", "").replace("%price%", "").replace(" ", "").toLowerCase());
		double getGold = main.getDataFile().getDouble("pdata." + player.getUniqueId().toString() + ".gold");
		List<String> boughtUpg = main.getDataFile().getStringList("pdata." + player.getUniqueId().toString() + ".boughtUpgrades");
		String[] colors = ufile.getString("availabilityColors").split(":");
		String[] messages = ufile.getString("availabilityMessages").split(":");
		
		if (!boughtUpg.contains(nameid)) {
			if (getGold >= ufile.getInt("items." + key + ".price")) {
				text = text.replace("%message%",messages[0]).replace("%color%", colors[0]);
			} else {
				text = text.replace("%message%",messages[1]).replace("%color%", colors[1]);
			}					
		} else {
			text = text.replace("%message%", messages[2]).replace("%color%", colors[2]);
		}
		
		return text.replace("%price%", Integer.toString(ufile.getInt("items." + key + ".price")));
	}

}
