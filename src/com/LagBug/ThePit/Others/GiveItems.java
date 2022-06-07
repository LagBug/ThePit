package com.LagBug.ThePit.Others;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.LagBug.ThePit.Main;


public class GiveItems implements Listener {
	
	private Main main = Main.getPlugin(Main.class);
	
	public void setupItems(Player player) {

		
    	player.getInventory().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
    	player.getInventory().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
    	player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));	
    	player.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));
    	player.getInventory().setItem(1, new ItemStack(Material.BOW));
    	player.getInventory().setItem(8, new ItemStack(Material.ARROW, 32, (short) 0));
    	
    	if (main.getDataFile().getStringList("pdata." + player.getUniqueId().toString() + ".activePerks").contains("safetyfirst")) { player.getInventory().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET)); }
    	if (main.getDataFile().getStringList("pdata." + player.getUniqueId().toString() + ".activePerks").contains("fishingrod"))  { player.getInventory().addItem(new ItemStack(Material.FISHING_ROD)); }	
    	if (main.getDataFile().getStringList("pdata." + player.getUniqueId().toString() + ".activePerks").contains("lavabucket")) { player.getInventory().addItem(new ItemStack(Material.LAVA_BUCKET)); }
    	
    	if (main.getDataFile().getStringList("pdata." + player.getUniqueId().toString() + ".activePerks").contains("mineman")) {
    		player.getInventory().addItem(new ItemStack(Material.COBBLESTONE, 24));
    		player.getInventory().addItem(new ItemStack(Material.DIAMOND_PICKAXE));
    	}
    	
	}

}