package com.LagBug.ThePit.GUIListners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.GUIs.PerksGUI;

public class UpgradesGUIListener implements Listener {
	
	private Main main = Main.getPlugin(Main.class);
	private PerksGUI pgui;
	
	public UpgradesGUIListener() {
		this.pgui = new PerksGUI(main);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();

		if (e.getCurrentItem() == null || e.getClickedInventory() == null || e.getInventory() == null|| e.getCurrentItem().getType().equals(Material.AIR))return;
		if (e.getClickedInventory().getTitle().contains("Permanent upgrades")) {
			e.setCancelled(true);

			if (e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§aPerk Slot") || e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§ePerk Slot")) {
				pgui.OpenGUI(player);
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§cPerk Slot")) {
				player.sendMessage("§cSlot not unlocked yet!");
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§cUnknown upgrade")){
				player.sendMessage("§cUpgrade not unlocked yet!"); 
			} else {
				for (int i = 0; i < items().size(); i++) {
					checkItem(player, items().get(i), e);
										
				}	
			}	
			
		}
		
	}
	
	public void checkItem(Player player, String name, InventoryClickEvent e) {
		
		double getGold = main.getDataFile().getDouble("pdata." + player.getUniqueId().toString() + ".gold");
		String bUpg = "pdata." + player.getUniqueId().toString() + ".boughtUpgrades";
		player.closeInventory();
		List<String> boughtUpg = main.getDataFile().getStringList("pdata." + player.getUniqueId().toString() + ".boughtUpgrades");
		String newName = name.replace("-", "").replace(" ", "").toLowerCase();
		
		if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§e" + name) || e.getCurrentItem().getItemMeta().getDisplayName().equals("§c" + name) || e.getCurrentItem().getItemMeta().getDisplayName().equals("§a" + name)) {
			if (!boughtUpg.contains(newName)) {
				if (getGold >= main.getConfig().getInt("prices.upgrades.xpboost")) {
					boughtUpg.add(newName);
					player.sendMessage("§a§lPURCHASE! §6" + name);
					main.getDataFile().set(bUpg, boughtUpg);
					main.getDataFile().set("pdata." + player.getUniqueId().toString() + ".gold",
					getGold - main.getConfig().getInt("prices.upgrades." + newName));	
					main.saveFiles();
				} else {
					player.sendMessage("§cNot enough gold!");
				}
			} else {
				player.sendMessage("§cYou already bought that upgrade!");
			}
		}

		
	}
	
	public List<String> items() {
		List<String> items = new ArrayList<>();
		items.add("XP Boost");
		items.add("Gold Boost");
		items.add("Melee Damage");
		items.add("Bow Damage");
		items.add("Damage Reduction");
		items.add("Build Battler");
		items.add("Gato Battler");
		
		return items;
	}
}