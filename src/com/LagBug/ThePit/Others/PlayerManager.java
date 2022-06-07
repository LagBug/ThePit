package com.LagBug.ThePit.Others;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;

public class PlayerManager {

	private Player player;
	private Main main = Main.getPlugin(Main.class);
	private FileConfiguration dfile = main.getDataFile();
	
	public PlayerManager(Player player) {
		this.player = player;
	}
	
	public void setGold(int amount) {
		dfile.set("pdata." + player.getUniqueId().toString() + ".gold", amount);
		main.saveFiles();		
	}
	
	public void addGold(int amount) {
		dfile.set("pdata." + player.getUniqueId().toString() + ".gold", dfile.getDouble("pdata." + player.getUniqueId().toString() + ".gold") + amount);
		main.saveFiles();
	}
	
	public void removeGold(int amount) {
		dfile.set("pdata." + player.getUniqueId().toString() + ".gold", dfile.getDouble("pdata." + player.getUniqueId().toString() + ".gold") - amount);
		main.saveFiles();		
	}	

	
	public void joinArena(String key) {
		FileConfiguration afile = main.getArenaFile();
		
		if (main.playerArena.get(player) == null || main.playerArena.get(player).equals("")) {			
			CustomScoreboard sb = new CustomScoreboard(main);
			
			sb.enableScoreboard(player);
			
			player.teleport(StringUtils.LocFromString(afile.getString("arenas." + key + ".spawn")));
			player.setGameMode(GameMode.SURVIVAL);

			main.arenaCounter.put(afile.getString("arenas." + key + ".name"), main.arenaCounter.get(afile.getString("arenas." + key + ".name")) + 1);
			main.playerArena.put(player, afile.getString("arenas." + key + ".name"));
			new GiveItems().setupItems(player);
			
			player.sendMessage(main.getMessage("commands.arena.join").replace("%arena%", afile.getString("arenas." + key + ".name")));
		
		} else {
			player.sendMessage(main.getMessage("commands.arena.already-in-arena"));
		}		


	}
}
