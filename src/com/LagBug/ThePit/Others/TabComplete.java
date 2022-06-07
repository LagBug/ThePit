package com.LagBug.ThePit.Others;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.LagBug.ThePit.Main;


public class TabComplete implements TabCompleter {
	
	private Main main = Main.getPlugin(Main.class);

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
    	List<String> empty = new ArrayList<>();
    	if (sender instanceof Player) {
        	if (cmd.getName().equalsIgnoreCase("pit")) {
    			if (args.length == 1) {
    				
        			List<String> list = new ArrayList<>();
        			
        			for (String s : main.commands()) {
        				list.add(s.substring(5));
        			}
        			Collections.sort(list);
        			return list;
    			} else if (args.length == 2){
        			if (args[0].equalsIgnoreCase("launchpad") || args[0].equalsIgnoreCase("spawn") ||args[0].equalsIgnoreCase("respawn") || args[0].equalsIgnoreCase("setspawn")) {
        				return empty;
        			} else if (args[0].equalsIgnoreCase("goldloc")) {
    	    			List<String> list = new ArrayList<>();
    	    			list.add("add");
    	    			list.add("rem");
    	    			return list;
    	    		}
    		} else if (args.length >= 3){
 	    			return empty;
 	    		}
    	}	
    	} 
    	
    	return null;
    }

}