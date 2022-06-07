package com.LagBug.ThePit.Runnables;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import com.LagBug.ThePit.Main;

public class GoldRunnable implements Runnable {
	private Main main;
	public GoldRunnable(Main main) {
		this.main = main;
	}
	
	@Override
	public void run() {
		final List<Location> possibleLocs = new ArrayList<>();
		Random random = new Random();
		FileConfiguration dfile = main.getDataFile();
		int onlinePlayers = Bukkit.getOnlinePlayers().size();
		if (onlinePlayers >= main.getConfig().getInt("gold-generators.required-players")) {
			if (main.getDataFile().getConfigurationSection("goldlocs") != null) {
				for (String section : main.getDataFile().getConfigurationSection("goldlocs").getKeys(false)) {
					final World newWorld = Bukkit.getWorld(dfile.getString("goldlocs." + section + ".world"));
					long newX = Math.round(dfile.getDouble("goldlocs." + section + ".x")) ;
					long newY = Math.round(dfile.getDouble("goldlocs." + section + ".y"));
					long newZ = Math.round(dfile.getDouble("goldlocs." + section + ".z"));
					final Location newLoc = new Location(newWorld, newX, newY, newZ);
					possibleLocs.add(newLoc);
				}
				Location newLoc = possibleLocs.get(random.nextInt(possibleLocs.size()));
				World newWorld = newLoc.getWorld();
				newWorld.dropItemNaturally(newLoc, new ItemStack(Material.GOLD_INGOT));
			}
		
	}
	}
}
