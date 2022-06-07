package com.LagBug.ThePit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.LagBug.ThePit.Commands.ItemsCommand;
import com.LagBug.ThePit.Commands.PerksCommand;
import com.LagBug.ThePit.Commands.PitCommand;
import com.LagBug.ThePit.Commands.UpgradesCommand;
import com.LagBug.ThePit.Events.OnBreak;
import com.LagBug.ThePit.Events.OnChat;
import com.LagBug.ThePit.Events.OnDeath;
import com.LagBug.ThePit.Events.OnFall;
import com.LagBug.ThePit.Events.OnFight;
import com.LagBug.ThePit.Events.OnHunger;
import com.LagBug.ThePit.Events.OnInteract;
import com.LagBug.ThePit.Events.OnJoin;
import com.LagBug.ThePit.Events.OnLeave;
import com.LagBug.ThePit.Events.OnLevelUp;
import com.LagBug.ThePit.Events.OnMobSpawn;
import com.LagBug.ThePit.Events.OnMove;
import com.LagBug.ThePit.Events.OnPickUp;
import com.LagBug.ThePit.Events.OnPlace;
import com.LagBug.ThePit.Events.OnRespawn;
import com.LagBug.ThePit.Events.OnSignChange;
import com.LagBug.ThePit.GUIListners.HelpGUIListener;
import com.LagBug.ThePit.GUIListners.ItemsGUIListener;
import com.LagBug.ThePit.GUIListners.PerksGUIListener;
import com.LagBug.ThePit.GUIListners.UpgradesGUIListener;
import com.LagBug.ThePit.Others.CustomScoreboard;
import com.LagBug.ThePit.Others.FileUtils;
import com.LagBug.ThePit.Others.StringUtils;
import com.LagBug.ThePit.Others.TabComplete;
import com.LagBug.ThePit.Others.UpdateChecker;
import com.LagBug.ThePit.Runnables.GoldRunnable;

public class Main extends JavaPlugin {

	public ArrayList<Player> adminmode = new ArrayList<>();
	public ArrayList<Player> isFighting = new ArrayList<>();
	public ArrayList<Player> ElGato = new ArrayList<>();
	
	public HashMap<Player, Integer> ks = new HashMap<>();
	public HashMap<Player, Integer> bounty = new HashMap<>();
	public HashMap<Player, String> playerArena = new HashMap<>();
	public HashMap<String, Integer> arenaCounter = new HashMap<>();

	private ConsoleCommandSender c = Bukkit.getConsoleSender();
	public UpdateChecker updater = new UpdateChecker(this, 61016);
	private FileUtils futils;

	@Override
	public void onEnable() {
		futils = new FileUtils();
		updateSigns();
		registerCommands();
		registerEvents();
		updateScoreboard();
		c.sendMessage("--------------------------------------------------");
		c.sendMessage(" [ThePit] Successfully enabled ThePit.");
		c.sendMessage(" [ThePit] Running version " + updater.getCurrentVersion());
		c.sendMessage("--------------------------------------------------");
		
		switch (updater.getResult()) {
		case ERROR:
			c.sendMessage(" [ThePit] Failed to check for updates.");
			break;
		case FOUND:
			c.sendMessage(" [ThePit] Found a new update! Download it using the following link:");
			c.sendMessage(" [ThePit] https://www.spigotmc.org/resources/ThePit.61016/");
			break;
		case NOT_FOUND:
			c.sendMessage(" [ThePit] No updates were found, you are using the latest version.");
			break;
		case DEVELOPMENT:
			c.sendMessage(" [ThePit] You are running a development build, this is not stable.");
			break;
		}
		c.sendMessage("--------------------------------------------------");

	}

	@Override
	public void onDisable() {
		unregisterPlayers();
		c.sendMessage("--------------------------------------------------");
		c.sendMessage(" [ThePit] Successfully disabled ThePit.");
		c.sendMessage("--------------------------------------------------");

	}

	
	public YamlConfiguration getDataFile() {
		return futils.getDataFile();
	}
	
	public YamlConfiguration getArenaFile() {
		return futils.getArenaFile();
	}
	
	public YamlConfiguration getMessagesFile() {
		return futils.getMessagesFile();
	}
	
	public YamlConfiguration getItemsFile() {
		return futils.getItemsFile();
	}
	
	public YamlConfiguration getUpgradesFile() {
		return futils.getUpgradesFile();
	}
	
	public void saveFiles() {
		try {
			getDataFile().save(futils.getDataData());
			getArenaFile().save(futils.getArenaData());
			getItemsFile().save(futils.getItemsData());
			getMessagesFile().save(futils.getMessagesData());
			getUpgradesFile().save(futils.getUpgradesData());
			reloadConfig();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getMessage(String path) {
		return ChatColor.translateAlternateColorCodes('&', getMessagesFile().getString(path));
	}
	
	private void unregisterPlayers() {

		FileConfiguration dfile = getDataFile();

		if (dfile.getString("lobby") != null) {
			if (!Bukkit.getOnlinePlayers().isEmpty()) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (playerArena.get(p) != null && !playerArena.get(p).equals("")
							&& dfile.getString("lobby") != null) {
						p.teleport(StringUtils.LocFromString(dfile.getString("lobby")));
					}
					if (playerArena.get(p) != null && !playerArena.get(p).equals("")) {
						new CustomScoreboard(this).disableScoreboard(p);
						p.getInventory().clear();
					}
				}
			}
		}

	}

	private void registerCommands() {
		getCommand("pit").setExecutor(new PitCommand());
		getCommand("pit").setTabCompleter(new TabComplete());
		getCommand("items").setExecutor(new ItemsCommand());
		getCommand("upgrades").setExecutor(new UpgradesCommand());
		getCommand("perks").setExecutor(new PerksCommand());
	}

	private void updateScoreboard() {
		final CustomScoreboard sb = new CustomScoreboard(this);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				if (!Bukkit.getOnlinePlayers().isEmpty()) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (playerArena.get(p) != null && !playerArena.get(p).equals("")) {
							sb.updateScoreboard(p);
						}
					}
				}
			}
		}, getConfig().getInt("scoreboard.update") * 10, getConfig().getInt("scoreboard.update") * 10);
	}

	private void updateSigns() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				if (getArenaFile().getConfigurationSection("arenas") == null) { return; }
				for (String s : getArenaFile().getConfigurationSection("arenas").getKeys(false)) {
					if (arenaCounter.get(getArenaFile().getString("arenas." + s + ".name")) == null) {
						arenaCounter.put(getArenaFile().getString("arenas." + s + ".name"), 0);
					}
					if (getArenaFile().getStringList("arenas." + s + ".signs") == null)
						return;
					for (String l : getArenaFile().getStringList("arenas." + s + ".signs")) {
						String str2loc[] = l.split("\\:");
						Location loc = new Location(Bukkit.getWorld(str2loc[0]), 0, 0, 0);
						loc.setX(Double.parseDouble(str2loc[1]));
						loc.setY(Double.parseDouble(str2loc[2]));
						loc.setZ(Double.parseDouble(str2loc[3]));

						BlockState state = null;
						Sign sign = null;

						try {
							state = loc.getBlock().getState();
							sign = (Sign) state;
						} catch (Exception ex) {
							return;
						}

						for (int i = 0; i < sign.getLines().length; i++) {
							sign.setLine(i, replace(getConfig().getStringList("signs.lines").get(i), s,
									getArenaFile().getString("arenas." + s + ".name")));
							sign.update();
						}
					}
				}
			}
		}, 20, 20);
	}

	private String replace(String s, String sec, String arena) {
		s = s.replace("%arena%", getArenaFile().getString("arenas." + sec + ".name"));
		s = s.replace("%max%", Integer.toString(getArenaFile().getInt("arenas." + sec + ".maxPlayers")));

		if (arenaCounter.get(arena) != null) {
			s = s.replace("%current%", Integer.toString(arenaCounter.get(arena)));
		}
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	private void registerEvents() {
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new GoldRunnable(this), getConfig().getInt("gold-generators.spawn-interval") * 20, getConfig().getInt("gold-generators.spawn-interval") * 20);
		Bukkit.getPluginManager().registerEvents(new HelpGUIListener(), this);
		Bukkit.getPluginManager().registerEvents(new ItemsGUIListener(), this);
		Bukkit.getPluginManager().registerEvents(new UpgradesGUIListener(), this);
		Bukkit.getPluginManager().registerEvents(new PerksGUIListener(), this);
		Bukkit.getPluginManager().registerEvents(new OnJoin(), this);
		Bukkit.getPluginManager().registerEvents(new OnLeave(), this);
		Bukkit.getPluginManager().registerEvents(new OnPlace(), this);
		Bukkit.getPluginManager().registerEvents(new OnBreak(), this);
		Bukkit.getPluginManager().registerEvents(new OnDeath(), this);
		Bukkit.getPluginManager().registerEvents(new OnRespawn(), this);
		Bukkit.getPluginManager().registerEvents(new OnMove(), this);
		Bukkit.getPluginManager().registerEvents(new OnPickUp(), this);
		Bukkit.getPluginManager().registerEvents(new OnFight(), this);
		Bukkit.getPluginManager().registerEvents(new OnLevelUp(), this);
		Bukkit.getPluginManager().registerEvents(new OnChat(), this);
		Bukkit.getPluginManager().registerEvents(new OnFall(), this);
		Bukkit.getPluginManager().registerEvents(new OnMobSpawn(), this);
		Bukkit.getPluginManager().registerEvents(new OnHunger(), this);
		Bukkit.getPluginManager().registerEvents(new OnInteract(), this);
		Bukkit.getPluginManager().registerEvents(new OnSignChange(), this);
	}

	public List<String> commands() {

		List<String> cmds = new ArrayList<>();

		cmds.add("/pit setspawn");
		cmds.add("/pit adminmode");
		cmds.add("/pit create");
		cmds.add("/pit delete");
		cmds.add("/pit setmax");
		cmds.add("/pit join");
		cmds.add("/pit leave");
		cmds.add("/pit list");
		cmds.add("/pit setlobby");
		cmds.add("/pit setgold");
		cmds.add("/pit addgold");
		cmds.add("/pit remgold");
		cmds.add("/pit setlevel");
		cmds.add("/pit addlevel");
		cmds.add("/pit remlevel");
		cmds.add("/pit goldloc");
		cmds.add("/pit launchpad");
		cmds.add("/pit discord");
		cmds.add("/pit reload");

		Collections.sort(cmds);

		return cmds;
	}

}
