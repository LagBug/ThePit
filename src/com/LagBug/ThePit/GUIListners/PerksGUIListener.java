package com.LagBug.ThePit.GUIListners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.GUIs.UpgradesGUI;

public class PerksGUIListener implements Listener {
	
	private Main main = Main.getPlugin(Main.class);

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();

		if (e.getCurrentItem() == null || e.getClickedInventory() == null || e.getInventory() == null|| e.getCurrentItem().getType().equals(Material.AIR))return;
		if (e.getClickedInventory().getTitle().contains("Choose a perk")) {
			e.setCancelled(true);
			if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aGo Back")) {
				new UpgradesGUI().OpenGUI(player);
			} else {
				for (int i = 0; i < items().size(); i++) {
					checkItem(player, items().get(i), e);
										
				}
	
			}
	
			
		}
		
	}
	
	public void checkItem(Player player, String name, InventoryClickEvent e) {
		
		double getGold = main.getDataFile().getDouble("pdata." + player.getUniqueId().toString() + ".gold");
		String bPrk = "pdata." + player.getUniqueId().toString() + ".boughtPerks";
		String aPrk = "pdata." + player.getUniqueId().toString() + ".activePerks";
		List<String> boughtPrk = main.getDataFile().getStringList("pdata." + player.getUniqueId().toString() + ".boughtPerks");
		List<String> activePrk = main.getDataFile().getStringList("pdata." + player.getUniqueId().toString() + ".activePerks");
		String newName = name.replace("-", "").replace(" ", "").toLowerCase();
		
		if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§e" + name) || e.getCurrentItem().getItemMeta().getDisplayName().equals("§c" + name) || e.getCurrentItem().getItemMeta().getDisplayName().equals("§a" + name)) {
			if (!boughtPrk.contains(newName)) {
				if (getGold >= main.getConfig().getInt("prices.perks." + newName)) {
					boughtPrk.add(newName);
					player.sendMessage("§a§lPURCHASE! §6" + name);
					main.getDataFile().set(bPrk, boughtPrk);
					main.getDataFile().set("pdata." + player.getUniqueId().toString() + ".gold",
							getGold - main.getConfig().getInt("prices.perks." + newName));	
					main.saveFiles();
					player.closeInventory();
				} else {
					player.sendMessage("§cNot enough gold!");
				}
			} else {
				if (activePrk.contains(newName)) {
					player.sendMessage("§cPerk already selected!");
					
					return;
				} else {
					if (player.getLevel() < 35 && activePrk.size() >= 1) {
						player.sendMessage("§cYou already have 1 perk active!");
					} else if ((player.getLevel() >= 35 && player.getLevel() < 70) && activePrk.size() >= 2) {
						player.sendMessage("§cYou already have 2 perks active!");
					} else if (player.getLevel() >= 70 && activePrk.size() >= 3) {
						player.sendMessage("§cYou already have 3 perks active!");
					} else {
						activePrk.add(newName);
						main.getDataFile().set(aPrk, activePrk);
						main.saveFiles();
						new UpgradesGUI().OpenGUI(player);
						}
					
				}
			}
		}

		
	}
	
	public List<String> items() {
		List<String> items = new ArrayList<>();
		items.add("Golden Heads");
		items.add("Fishing Rod");
		items.add("Lava Bucket");
		items.add("Strength-Chaining");
		items.add("Endless Quiver");
		items.add("Mineman");
		items.add("Safety First");
		items.add("Trickle-down");
		items.add("Lucky Diamond");
		items.add("Spammer");
		items.add("Bounty Hunter");
		items.add("Streaker");
		items.add("Gladiator");
		items.add("Vampire");
		
		return items;
	}
}