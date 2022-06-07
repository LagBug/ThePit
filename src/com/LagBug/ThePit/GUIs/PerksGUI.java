package com.LagBug.ThePit.GUIs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.LagBug.ThePit.Main;

public class PerksGUI {
	private Main main;
	
	public PerksGUI(Main main) {
		this.main = main;
	}
	
	
	public ItemStack setItem(String name, Material material, List<String> oldLore, Player player) {
		String nameid = name.toLowerCase().replace(" ", "").replace("-", "");
		double getGold = main.getDataFile().getDouble("pdata." + player.getUniqueId().toString() + ".gold");
		List<String> boughtPrk = main.getDataFile().getStringList("pdata." + player.getUniqueId().toString() + ".boughtPerks");
		List<String> activePrk = main.getDataFile().getStringList("pdata." + player.getUniqueId().toString() + ".activePerks");
		ItemStack item = null;
		ItemMeta itemMeta;
		List<String> lore = new ArrayList<>();
		for (String s : oldLore) { lore.add(s); }
		
		if (material.equals(Material.SKULL_ITEM)) {
			item = new ItemStack(material, 1, (short) 3);
			SkullMeta t = (SkullMeta) item.getItemMeta();
			itemMeta = t;
			((SkullMeta) itemMeta).setOwner(main.getConfig().getString("general.goldenheads-head"));			
		} else {
			item = new ItemStack(material);
			itemMeta = item.getItemMeta();
		}
		
		lore.add("");
		if (!activePrk.contains(nameid)) {		
			if (!boughtPrk.contains(nameid)) {
				lore.add("§7Cost: §6" + main.getConfig().getInt("prices.perks." +nameid) + "g");
					if (getGold >= main.getConfig().getInt("prices.perks." + nameid)) {
						itemMeta.setDisplayName("§e" +name);
						lore.add("§eClick to purchase!");
					} else {
						itemMeta.setDisplayName("§c" +name);
						lore.add("§cNot enough gold!");
					}
				} else {
					itemMeta.setDisplayName("§a" +name);
					lore.add("§eClick to select!");		
				}		
			} else {
				itemMeta.setDisplayName("§a" +name);
				lore.add("§aAlready selected!");
			}
		
		
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		
		return item;
	}
	
	public void OpenGUI(Player player) {
		int level = player.getLevel();		
		Inventory gui = Bukkit.createInventory(null, 36, "Choose a perk");
			
			List<String> backLore = new ArrayList<>();
			ItemStack back = new ItemStack(Material.ARROW);
			ItemMeta backMeta = back.getItemMeta();		
			backMeta.setDisplayName("§aGo Back");
			backLore.add("§7To permanent upgrades");
			backMeta.setLore(backLore);
			back.setItemMeta(backMeta);

			
			gui.setItem(31, back);
			gui.setItem(10, setItem("Golden Heads", Material.SKULL_ITEM, lores().get("goldenheads"), player));	
			gui.setItem(11, setItem("Fishing Rod", Material.FISHING_ROD, lores().get("fishingrod"), player));
			gui.setItem(12, setItem("Lava Bucket", Material.LAVA_BUCKET, lores().get("lavabucket"), player));
			
			gui.setItem(13, level >= 20 ? setItem("Strength-Chaining", Material.REDSTONE, lores().get("strength"), player) : reqLevel("§320"));
			gui.setItem(14, level >= 20 ? setItem("Endless Quiver", Material.BOW, lores().get("endless"), player) : reqLevel("§320"));

			gui.setItem(15, level >= 20 ? setItem("Mineman", Material.COBBLESTONE, lores().get("mineman"), player) : reqLevel("§230"));
			gui.setItem(16, level >= 20 ? setItem("Safety First", Material.CHAINMAIL_HELMET, lores().get("safety"), player) : reqLevel("§230"));
			
			gui.setItem(19, level >= 40 ? setItem("Trickle-down", Material.GOLD_INGOT, lores().get("trickle"), player) : reqLevel("§a40"));
			gui.setItem(20, level >= 40 ? setItem("Lucky Diamond", Material.DIAMOND, lores().get("lucky"), player) : reqLevel("§a40"));
			gui.setItem(21, level >= 40 ? setItem("Spammer", Material.STRING, lores().get("spammer"), player) : reqLevel("§a40"));

			gui.setItem(22, level >= 50 ? setItem("Bounty Hunter", Material.GOLD_LEGGINGS, lores().get("bounty"), player) : reqLevel("§e50"));
			gui.setItem(23, level >= 50 ? setItem("Streaker", Material.HAY_BLOCK, lores().get("streaker"), player) : reqLevel("§e50"));

			gui.setItem(24, level >= 60 ? setItem("Gladiator", Material.BONE, lores().get("gladiator"), player) : reqLevel("§6§l60"));
			gui.setItem(25, level >= 60 ? setItem("Vampire", Material.SPIDER_EYE, lores().get("vampire"), player) : reqLevel("§6§l60"));


		player.openInventory(gui);
	}
	
	public ItemStack reqLevel(String level) {
		List<String> lore = new ArrayList<>();
		ItemStack item = new ItemStack(Material.BEDROCK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§cUnknown perk"); 
		lore.add("§7Required level: [" + level + "§r§7]");
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public HashMap<String, List<String>> lores() {
		HashMap<String, List<String>> lores = new HashMap<>();
		lores.put("goldenheads", Arrays.asList("§7Golden apples you earn turn", "§7into §6Golden Heads§7."));
		lores.put("fishingrod", Arrays.asList("§7Spawn with a fishing rod."));
		lores.put("lavabucket", Arrays.asList("§7Spawn with a laval bucket."));
		lores.put("strength", Arrays.asList("§c+5% damage", "§7stacking on kill."));
		lores.put("endless", Arrays.asList("§7Get 3 arrows on arrow hit."));
		lores.put("mineman", Arrays.asList("§7Spawn  with §f24 Cobblestone", "§7and a diamond pickaxe", "", "§7+§f3 blocks §7on kill."));
		lores.put("safety", Arrays.asList("§7Spawn  with a helmet."));
		lores.put("trickle", Arrays.asList("§7Picked up gold ingot", "§7rewards 7x more coins."));
		lores.put("lucky", Arrays.asList("§730% chance to upgrade", "§7dropped armor pieces from", "§7kills to §bdiamond§7.", "", "§7Upgraded pieces warp to", "§7your inventory."));
		lores.put("spammer", Arrays.asList("§7Double base gold reward on", "§7targets you've shot an", "§7arrow in.", "", "§6+2g §7on assists."));
		lores.put("bounty", Arrays.asList("§6+4g §7on all kills.", "§7Earn bounty assists shares.", "", "§c+1% damage§7/100g bounty", "§7on target."));
		lores.put("streaker", Arrays.asList("§7Triple streak kill §bXP", "§7bonus."));
		lores.put("gladiator", Arrays.asList("§7Receive §9-3% §7damage per", "§7nearby player.", "", "§712 blocks range.", "§7Minimum 3, max 10 players."));
		lores.put("vampire", Arrays.asList("§7Don't earn golden apples", "§7Heal §c0.5❤ §7on melee hit.", "§7Heal §c1.5❤ §7on arrow hit."));
		
		return lores;
	}
	


}


