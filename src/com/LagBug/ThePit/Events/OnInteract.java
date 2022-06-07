package com.LagBug.ThePit.Events;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.Others.PlayerManager;

public class OnInteract implements Listener {
	
	private Main main = Main.getPlugin(Main.class);

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		boolean work = false;
		Player player = e.getPlayer();
		Block block = e.getClickedBlock();
		FileConfiguration afile = main.getArenaFile();
		PlayerManager pm = new PlayerManager(player);

		if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){ return; }
		BlockState state = block.getState();
		
		if (state instanceof Sign) {
			Sign sign = (Sign) state;
			if (afile.getConfigurationSection("arenas") == null) { return; }

			for (String s : main.getArenaFile().getConfigurationSection("arenas").getKeys(false)){
				
					if (sign.getLine(1).equals(replace(main.getConfig().getStringList("signs.lines").get(1), s, 0))) {
						if (afile.getString("arenas." + s + ".spawn") != null) {
							pm.joinArena(s);
							work = true;
							break;
						}					
					}
			
			}
			
			if (!work) {
				player.sendMessage(main.getMessage("commands.arena.no-spawn-loc"));
			}

			
		}
		try {
			if (player.getItemInHand().getItemMeta().getDisplayName().equals("§6Golden Head")) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 8 * 20, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 8 * 20, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 8 * 20, 1));
				player.getInventory().removeItem(player.getItemInHand());
			}
		} catch (Exception ex) { return; }


	}
	
	public String replace(String s, String sec, int current) {
		
		return ChatColor.translateAlternateColorCodes('&', 
				s.replace("%current%", Integer.toString(current))
				.replace("%arena%", main.getArenaFile().getString("arenas." + sec + ".name"))
				.replace("%max%", Integer.toString(main.getArenaFile().getInt("arenas." + sec + ".maxPlayers"))));
		
		
	}
}

