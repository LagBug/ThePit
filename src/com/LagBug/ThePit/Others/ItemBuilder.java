package com.LagBug.ThePit.Others;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemBuilder {

	ItemStack item;
	ItemMeta meta;

	public ItemBuilder(Material material, int amount, int data) {
		item = new ItemStack(material, amount, (byte) data);
		meta = item.getItemMeta();
	}

	public ItemBuilder setDisplayName(String name) {
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		List<String> loreR = new ArrayList<>();
		for (String s : lore) {
			loreR.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		meta.setLore(loreR);
		return this;
	}

	public ItemStack build() {
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);
		return item;
	}

}
