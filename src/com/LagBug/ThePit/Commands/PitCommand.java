package com.LagBug.ThePit.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;
import com.LagBug.ThePit.Commands.PitCommands.AddGold;
import com.LagBug.ThePit.Commands.PitCommands.AddLevel;
import com.LagBug.ThePit.Commands.PitCommands.Adminmode;
import com.LagBug.ThePit.Commands.PitCommands.Create;
import com.LagBug.ThePit.Commands.PitCommands.Delete;
import com.LagBug.ThePit.Commands.PitCommands.Discord;
import com.LagBug.ThePit.Commands.PitCommands.GoldLoc;
import com.LagBug.ThePit.Commands.PitCommands.Join;
import com.LagBug.ThePit.Commands.PitCommands.Launchpad;
import com.LagBug.ThePit.Commands.PitCommands.Leave;
import com.LagBug.ThePit.Commands.PitCommands.List;
import com.LagBug.ThePit.Commands.PitCommands.Reload;
import com.LagBug.ThePit.Commands.PitCommands.RemGold;
import com.LagBug.ThePit.Commands.PitCommands.RemLevel;
import com.LagBug.ThePit.Commands.PitCommands.SetGold;
import com.LagBug.ThePit.Commands.PitCommands.SetLevel;
import com.LagBug.ThePit.Commands.PitCommands.SetLobby;
import com.LagBug.ThePit.Commands.PitCommands.SetMax;
import com.LagBug.ThePit.Commands.PitCommands.SetSpawn;
import com.LagBug.ThePit.GUIs.HelpGUI;

public class PitCommand implements CommandExecutor {
	
	private Main main = Main.getPlugin(Main.class);
	private HelpGUI helpgui;

	public PitCommand() {
		this.helpgui = new HelpGUI(main);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			if (cmd.getName().equalsIgnoreCase("pit")) {
				Bukkit.getConsoleSender().sendMessage(main.getMessage("general.no-console"));
			}
		} else {
			
			final Player player = (Player) sender;
			
				if (args.length == 0) {
					helpgui.OpenGUI(player);
				} else if (args.length >= 1) {
					
					switch (args[0].toLowerCase()) {
					
						case "launchpad":
							Launchpad.onCommand(sender, cmd, label, args, main);
							break;
							
						case "reload":
							Reload.onCommand(sender, cmd, label, args, main);
							break;
						
						case "goldloc":
							GoldLoc.onCommand(sender, cmd, label, args, main);
							break;
							
						case "setlobby":
							SetLobby.onCommand(sender, cmd, label, args, main);
							break;
							
						case "create":
							Create.onCommand(sender, cmd, label, args, main);
							break;
						
						case "delete":
							Delete.onCommand(sender, cmd, label, args, main);
							break;

						case "list":
							List.onCommand(sender, cmd, label, args, main);
							break;
							
						case "join":
							Join.onCommand(sender, cmd, label, args, main);
							break;
							
						case "leave":
							Leave.onCommand(sender, cmd, label, args, main);
							break;
							
						case "setmax":
							SetMax.onCommand(sender, cmd, label, args, main);
							break;
							
						case "setspawn":
							SetSpawn.onCommand(sender, cmd, label, args, main);
							break;
							
						case "discord":
							Discord.onCommand(sender, cmd, label, args, main);
							break;
							
						case "adminmode":
							Adminmode.onCommand(sender, cmd, label, args, main);
							break;
							
						case "addlevel":
							AddLevel.onCommand(sender, cmd, label, args, main);
							break;

						case "remlevel":
							RemLevel.onCommand(sender, cmd, label, args, main);
							break;
							
						case "setlevel":
							SetLevel.onCommand(sender, cmd, label, args, main);
							break;
							
						case "addgold":
							AddGold.onCommand(sender, cmd, label, args, main);
							break;
							
						case "remgold":
							RemGold.onCommand(sender, cmd, label, args, main);
							break;
							
						case "setgold":
							SetGold.onCommand(sender, cmd, label, args, main);
							break;
							
						default:
							helpgui.OpenGUI(player);
							break;
					}
				}

			}
		
		return false;
	}
}
