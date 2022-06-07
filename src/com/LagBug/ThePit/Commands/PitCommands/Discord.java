package com.LagBug.ThePit.Commands.PitCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Discord {
	
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args, Main main) {
    	Player player = (Player) sender; 
    	
		if (!player.hasPermission("pit.admin.discord") || !player.hasPermission("pit.admin.*") || !player.hasPermission("pit.*")) {
			player.sendMessage(main.getMessage("general.no-permission"));
			return false;
		}
		
		TextComponent msg = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&8&l > &6Join the support Discord: &ehttps://discord.gg/sJg2J56"));
		msg.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://discord.gg/sJg2J56"));
		msg.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&6Click this message to join.")).create()));
		
		player.spigot().sendMessage(msg);
    	
    	return false;
    }
}
