package com.LagBug.ThePit.GUIs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.LagBug.ThePit.Main;


public class HelpGUI {
	private Main main;
	
	public HelpGUI(Main main) {
		this.main = main;
	}
	public void OpenGUI(Player player) {
		Inventory gui = Bukkit.createInventory(null, 
				(int) Math.min(54,9 * (Math.ceil(main.commands().size() / 9.0)))
				,"ThePit Help Menu");
		
	
		for (int i = 0; i < main.commands().size(); i++) {
			String cmd = (main.commands().get(i));
			
			List<String> itemLore = new ArrayList<>();
			ItemStack item = new ItemStack(Material.PAPER);
			ItemMeta itemMeta = item.getItemMeta();		
			itemMeta.setDisplayName("§8§l> §6" + cmd);
			itemLore.add("§8§l> §7Click to run");
			itemLore.add("§8§l> §7this command!");
			itemMeta.setLore(itemLore);
			item.setItemMeta(itemMeta);
	        
			
			
	        gui.setItem(i, item);
		}
		player.openInventory(gui);
	}
}
