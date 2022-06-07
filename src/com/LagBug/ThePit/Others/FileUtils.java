package com.LagBug.ThePit.Others;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import com.LagBug.ThePit.Main;

public class FileUtils {
	private Main main = Main.getPlugin(Main.class); 
	private File dataFile, arenaFile, messagesFile, itemsFile, upgradesFile;
	private YamlConfiguration modifyData, modifyArena, modifyMessages, modifyItems, modifyUpgrades;
	
	public FileUtils() {
		initializeConfig();
		initializeFiles();
	}
	
	private void initializeFiles() {
		dataFile = new File(main.getDataFolder(), "data.yml");
		if (!dataFile.exists()) {
			File directory = new File(dataFile.getParentFile().getAbsolutePath());
			directory.mkdirs();
			try {
				dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modifyData = YamlConfiguration.loadConfiguration(dataFile);


		arenaFile = new File(main.getDataFolder(), "arenas.yml");
		if (!arenaFile.exists()) {
			File directory = new File(arenaFile.getParentFile().getAbsolutePath());
			directory.mkdirs();
			try {
				arenaFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modifyArena = YamlConfiguration.loadConfiguration(arenaFile);
		
		
		messagesFile = new File(main.getDataFolder(), "messages.yml");
		if (!messagesFile.exists()) {
			main.saveResource("messages.yml", false);
		}
		modifyMessages = YamlConfiguration.loadConfiguration(messagesFile);
		
		
		itemsFile = new File(main.getDataFolder(), "guis" + File.separator + "items.yml");
		if (!itemsFile.exists()) {
			main.saveResource("guis" + File.separator + "items.yml", false);
		}
		modifyItems = YamlConfiguration.loadConfiguration(itemsFile);
		
		upgradesFile = new File(main.getDataFolder(), "guis" + File.separator + "upgrades.yml");
		if (!upgradesFile.exists()) {
			main.saveResource("guis" + File.separator + "upgrades.yml", false);
		}
		modifyUpgrades = YamlConfiguration.loadConfiguration(upgradesFile);
		
	}
	
	private void initializeConfig() {
		main.getConfig().options().copyDefaults(true);
		main.getConfig().options().copyHeader(true);
		main.saveDefaultConfig();
		main.reloadConfig();
		
		File guisFolder = new File(main.getDataFolder(), "guis");
		if (!guisFolder.exists()) { guisFolder.mkdirs(); }
	}
	
	
	public YamlConfiguration getDataFile() { return modifyData; }
	public File getDataData() { return dataFile; }
	
	public YamlConfiguration getArenaFile() { return modifyArena; }
	public File getArenaData() { return arenaFile; }
	
	public YamlConfiguration getMessagesFile() { return modifyMessages; }
	public File getMessagesData() { return messagesFile; }
	
	public YamlConfiguration getItemsFile() { return modifyItems; }
	public File getItemsData() { return itemsFile; }
	
	public YamlConfiguration getUpgradesFile() { return modifyUpgrades; }
	public File getUpgradesData() { return upgradesFile; }	
}
