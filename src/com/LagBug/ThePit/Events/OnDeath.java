package com.LagBug.ThePit.Events;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.Actiobar.Actionbar;

public class OnDeath implements Listener {
	
	private Main main = Main.getPlugin(Main.class);
	private HashMap<Player, Integer> ks;
	private HashMap<Player, Integer> bn;
	private FileConfiguration config;

	public OnDeath() {
		this.ks = main.ks;
		this.bn = main.bounty;
		this.config = main.getConfig();
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
	
		Player killer = e.getEntity().getKiller();
		final Player killed = e.getEntity();

		if (onArena(killed)) {
		
			if (main.isFighting.contains(killed)) { main.isFighting.remove(killed); }
			
			sendTitle("death-title", "killed", killer, killed);
			e.setDeathMessage("");
			e.getDrops().clear();
			e.setKeepLevel(true);
			e.setDroppedExp(0);
			
			if (config.getBoolean("general.auto-respawn")) {
				Bukkit.getScheduler().runTaskLater(main, new Runnable() {
					@Override
					public void run() {
						killed.spigot().respawn();
					}
				}, 10);
			}

			if (e.getEntity().getKiller() != null) {
				
				Random rnd = new Random();
				int x = rnd.nextInt(100);

				killed.sendMessage(replace(config.getString("messages.death-by-player"), killer, killed));
				Actionbar.sendActionBar(killer, replace(config.getString("actionbar.kill.format"), killed, killer));
				
				givePerksAndUpgrades(killer, killed);
				giveRewards(killer, killed);
				checkBountyKillstreak(killer, killed, x);
				
			} else {
				killed.sendMessage(replace(config.getString("messages.death-by-unknown"), killer, killed));
			}
			
		}

	}


	public boolean onArena(Player killed) {
		return main.playerArena.get(killed) != null && !main.playerArena.get(killed).equals("");
	}
	
	public void giveRewards(Player killer, Player killed) {
		
		String lvl = getLevel(killer);
		double gold = main.getDataFile().getDouble("pdata." + killer.getUniqueId().toString() + ".gold");
		double goldToGive = config.getDouble("leveling-system." + lvl + ".gold-on-kill");
		int xpToGive = config.getInt("leveling-system." + lvl + ".xp-on-kill");
		boolean containsGatoBattler = main.getDataFile().getStringList("pdata." + killer.getUniqueId().toString() + ".boughtUpgrades").contains("gatobattler");
		boolean containsGoldBoost = main.getDataFile().getStringList("pdata." + killer.getUniqueId().toString() + ".boughtUpgrades").contains("goldboost");
		boolean containsXpBoost = main.getDataFile().getStringList("pdata." + killer.getUniqueId().toString() + ".boughtUpgrades").contains("xoboost");
		
		if (containsGatoBattler) {
			goldToGive = goldToGive + 5.0;
			xpToGive = xpToGive + 6;
			if (main.ElGato.contains(killer)) main.ElGato.remove(killer);
		}
		
		if (containsXpBoost) { xpToGive = xpToGive + 10; };
		if (containsGoldBoost) { goldToGive = goldToGive + 10; }

		if (killed.getLevel() >= 120) { xpToGive = 0; }

		killer.sendMessage(replace(config.getString("messages.kill-by-player"), killer, killed).replace("%xp%", Integer.toString(xpToGive)).replace("%gold%", Double.toString(goldToGive)));
		killer.giveExp(xpToGive);
		main.getDataFile().set("pdata." + killer.getUniqueId().toString() + ".gold", gold + goldToGive);
		main.saveFiles();
	}
	
	public String getLevel(Player player) {
		
		int lvl = player.getLevel(); 
		int min = (int) Math.floor(lvl/10.0) * 10;
		int max = (int) Math.ceil(lvl/9.9) * 10 - 1;
		if (lvl <= 0) { max = 9; }
		if (lvl >= 120) { return "120"; }
	
		return min+"to"+max;
		
	}
	
	@SuppressWarnings("deprecation")
	public void sendTitle(String path, String who, Player killer, Player killed) {
	
		switch (who) {
			case "killer":
				killer.sendTitle(replace(config.getString("titles." + path + ".title"), killer, killed),
					replace(config.getString("titles." + path + ".subtitle"), killer, killed));			
				break;
			default:
				killed.sendTitle(replace(config.getString("titles." + path + ".title"), killer, killed),
					replace(config.getString("titles." + path + ".subtitle"), killer, killed));
				break;
		}

	}
	
	public void checkBountyKillstreak(Player killer, Player killed, int x) {
		
		if (ks.containsKey(killed)) { ks.remove(killed); }
		if (ks.containsKey(killer)) { ks.put(killer, ks.get(killer) + 1); } else { ks.put(killer, 1); }
		
		for (int i = 0; i < config.getIntegerList("killstreaks").size(); i++) {
			if (ks.get(killer) == config.getIntegerList("killstreaks").get(i)) {
				Bukkit.broadcastMessage(replace(config.getString("messages.killstreak-message"), killer, killed));
			}
		}
		
		if (ks.get(killer) >= config.getInt("bounties.required-ks-for-bounty")) {
			if (x <= config.getInt("bounties.bounty-chance")) {
				bn.put(killer, bn.get(killer) == null ? randomBounty() : bn.get(killer) + randomBounty());	
				Bukkit.broadcastMessage(replace(config.getString("messages.bountied-message").replace("%bounty%", Integer.toString(bn.get(killer))), killer, killed));
			}
		}
		
		if (bn.containsKey(killed)) {
			main.getDataFile().set("pdata." + killer.getUniqueId().toString() + ".gold", main.getDataFile().getDouble("pdata." + killer.getUniqueId().toString() + ".gold")+ bn.get(killed));
			Bukkit.broadcastMessage(replace(config.getString("messages.bounty-claimed").replace("%bounty%", Integer.toString(bn.get(killed))), killer, killed));
			bn.remove(killed);
		}

	}
	
	
	public int randomBounty() {
		List<Integer> format = main.getConfig().getIntegerList("bounties.values");
		Random random = new Random();

		Integer randomInt = random.nextInt(format.size());

		return format.get(randomInt);
	}
	
	public String replace(String s, Player killer, Player killed) {
		
		if (ks.get(killer) != null) { s = s.replace("%kills%", Integer.toString(ks.get(killer))); }
		if (killer != null) { s = s.replace("%killer%", killer.getName()).replace("%player%", killer.getName()); }
		if (killed != null) { s = s.replace("%killed%", killed.getName()); }
		s = ChatColor.translateAlternateColorCodes('&', s);
               
        return s;
	}

	public void givePerksAndUpgrades(Player killer, Player killed) {
		String path = "pdata." + killed.getUniqueId().toString();
		String pathK = "pdata." + killer.getUniqueId().toString();
		
		if (main.getDataFile().getStringList(path + ".boughtUpgrades").contains("gatobattler")) { main.ElGato.add(killed); }		
		if (main.getDataFile().getStringList(pathK + ".activePerks").contains("mineman")) { killer.getInventory().addItem(new ItemStack(Material.COBBLESTONE, 3)); }
		if (main.getDataFile().getStringList(pathK + ".activePerks").contains("strengthchaining")) { killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5 * 20, 1)); }
		if (main.getDataFile().getStringList(pathK + ".activePerks").contains("goldenheads")) {
			ItemStack gh = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			SkullMeta ghMeta = (SkullMeta) gh.getItemMeta();
			ghMeta.setOwner(config.getString("general.goldenheads-head"));
			ghMeta.setDisplayName("§6Golden Head");
			gh.setItemMeta(ghMeta);
				killer.getInventory().addItem(gh);
			} else {
				killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));	
			}
		
	}
}
