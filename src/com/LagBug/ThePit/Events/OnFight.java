package com.LagBug.ThePit.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.Actiobar.Actionbar;

import net.md_5.bungee.api.ChatColor;

public class OnFight implements Listener {
	
	
	private Main main = Main.getPlugin(Main.class);
	private Player damager;

	@EventHandler
	public void onFight(EntityDamageByEntityEvent e) {
		final Player player = (Player) e.getEntity();

		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			this.damager = (Player) e.getDamager();
		} else if (e.getDamager() instanceof Arrow) {
			Arrow oldShooter = (Arrow) e.getDamager();
			this.damager = (Player) oldShooter.getShooter();
		}

		Actionbar.sendActionBar(damager,
				ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("actionbar.hearts.format")
						.replace("%player%", player.getName()).replace("%hearts%", getHeartLevel(player))));

		if (!main.isFighting.contains(player)) {
			main.isFighting.add(player);
		}
		if (!main.isFighting.contains(damager)) {
			main.isFighting.add(damager);
		}

		Bukkit.getScheduler().runTaskLater(main, new Runnable() {
			public void run() {
				main.isFighting.remove(player);
				main.isFighting.remove(damager);
			}
		}, main.getConfig().getInt("general.fighting-time") * 20);

	}

	private String getHeartLevel(Player player) {

		int currentHealth = (int) player.getHealth() / 2;
		int maxHealth = (int) player.getMaxHealth() / 2;
		String rHeartColor = ChatColor.translateAlternateColorCodes('&',
				main.getConfig().getString("actionbar.hearts.remain-hearts-color"));
		String lHeartColor = ChatColor.translateAlternateColorCodes('&',
				main.getConfig().getString("actionbar.hearts.lose-hearts-color"));
		int lostHealth = maxHealth - currentHealth;

		String rHeart = "";
		String lHeart = "";

		for (int i = 0; i < currentHealth; i++) {
			rHeart = rHeart + rHeartColor + "❤";
		}
		for (int i = 0; i < lostHealth; i++) {
			lHeart = lHeart + lHeartColor + "❤";
		}

		String heartResult = rHeart + lHeart;
		return heartResult;
	}

	public void givePerksUpgrds(Player player, Player damager, EntityDamageByEntityEvent e) {
		if (main.getDataFile().getStringList("pdata." + damager.getUniqueId().toString() + ".boughtUpgrades")
				.contains("damagereduction")) {
			e.setDamage(e.getDamage() - 1.0);
		}
		if (main.getDataFile().getStringList("pdata." + player.getUniqueId().toString() + ".activePerks")
				.contains("gladiator")) {
			e.setDamage(e.getDamage() - 3.0);
		}
		if (main.getDataFile().getStringList("pdata." + player.getUniqueId().toString() + ".activePerks")
				.contains("vampire")) {
			if (player.getHealth() <= 18.5) {
				player.setHealth(player.getHealth() + 1.5);
			}
		}

		if (e.getDamager() instanceof Arrow) {
			if (main.getDataFile().getStringList("pdata." + player.getUniqueId().toString() + ".activePerks")
					.contains("endlessquiver")) {
				player.getInventory().addItem(new ItemStack(Material.ARROW, 3));
			}
			if (main.getDataFile().getStringList("pdata." + damager.getUniqueId().toString() + ".boughtUpgrades")
					.contains("bowdamage")) {
				e.setDamage(e.getDamage() + 3.0);	
			}
				
		} else {
			if (main.getDataFile().getStringList("pdata." + damager.getUniqueId().toString() + ".boughtUpgrades")
					.contains("meleedamage")) {
				e.setDamage(e.getDamage() + 1.0);
			}
		}

	}
}
