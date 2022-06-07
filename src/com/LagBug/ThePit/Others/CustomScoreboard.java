package com.LagBug.ThePit.Others;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.LagBug.ThePit.Main;

public class CustomScoreboard {
	private Main main;

	public CustomScoreboard(Main main) {
		this.main = main;

	}

	public void enableScoreboard(Player player) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("Scoreboard", "Dummy");

		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName(replace(main.getConfig().getString("scoreboard.title"), player));

		int count = main.getConfig().getStringList("scoreboard.rows").size();
		
		for (String text : main.getConfig().getStringList("scoreboard.rows")) {
			String result = getResult(text, player);
			
			Team team = board.registerNewTeam(result.substring(0, result.length()/2));
			
			team.addEntry(Integer.toString(count));	
			
			
			String prefix = result.substring(0, result.length() / 2);
			String suffix = result.substring(result.length() / 2);
			
			team.setPrefix(prefix);
			team.setSuffix(suffix);
			obj.getScore(result).setScore(count);
			count--;

		}

		player.setScoreboard(board);

	}
	
	public void updateScoreboard(Player player) {
		Scoreboard board = player.getScoreboard();
		int current = 0;
		List<String> lines = main.getConfig().getStringList("scoreboard.rows");
		for (Team team : board.getTeams()) {
			String text = lines.get(current);
			String result = getResult(text, player);
			
			String prefix = result.substring(0, result.length() / 2);
			String suffix = result.substring(result.length() / 2);
			
			team.setPrefix(prefix);
			team.setSuffix(suffix);

			current++;
		}

	}

	public void disableScoreboard(Player p) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		board.clearSlot(DisplaySlot.SIDEBAR);
		p.setScoreboard(board);
	}

	public String status(Player p) {
		if (main.isFighting.contains(p)) {
			return ChatColor.translateAlternateColorCodes('&', "&cFighting");
		}
		return ChatColor.translateAlternateColorCodes('&', "&aIdling");
	}
	
	public String getResult(String text, Player p) {
		return replace(replace(text.substring(0, text.length() / 2), p) + replace(text.substring(text.length() / 2), p), p);
	}

	public String replace(String s, Player p) {

		double gold = main.getDataFile().getDouble("pdata." + p.getUniqueId().toString() + ".gold");

		return ChatColor.translateAlternateColorCodes('&',
				s.replace("%lvl%", Integer.toString(p.getLevel())).replace("%date%", getDate())
						.replace("%xp%", Integer.toString(p.getExpToLevel())).replace("%gold%", Double.toString(gold))
						.replace("%status%", (status(p))));
	}

	public String getDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		return sdf.format(cal.getTime());
	}

}