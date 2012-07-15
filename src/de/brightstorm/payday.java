package de.brightstorm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.brightstorm.Metrics.Graph;

public class payday extends JavaPlugin {
	public static List<String> groups;
	public static Plugin dies;
	public static FileConfiguration users;
	public static Logger log;
	public static boolean money=true;
    
    public void onEnable() {
    	dies = this;
    	log = this.getLogger();
    	if(!getConfig().contains("groups")) {
    		List<String> grouplist = new ArrayList<String>();
    		grouplist.add("vip");
    		grouplist.add("normal");
    		getConfig().addDefault("groups", grouplist);
    		getConfig().addDefault("normal.time", 60);
    		getConfig().addDefault("normal.amount", 20);
    		getConfig().addDefault("vip.time", 60);
    		getConfig().addDefault("vip.amount", 50);
    		getConfig().addDefault("message", "You just got %a for being online %t minutes.");
    	}
	getConfig().addDefault("use_vault", true);
	getConfig().addDefault("reward_item", 264);
	getConfig().addDefault("use_essentials", false);
    	getConfig().set("version", Double.parseDouble(this.getDescription().getVersion()));
	getConfig().options().copyDefaults(true);
    	saveConfig();
    	g.useEssentials=getConfig().getBoolean("use_essentials");
    	if(g.useEssentials) {
    		if(getServer().getPluginManager().isPluginEnabled("Essentials")) {
    			log.info("Hooked into Essentials!");
    		} else {
    			log.severe("Essentials support could not be activated, cause Essentials isn't installed!");
    			g.useEssentials=false;
    		}
    	}
    	File userFile = new File(getDataFolder()+System.getProperty("file.separator")+"users.yml");
    	money = getConfig().getBoolean("use_vault");
    	if(money) {
    		if(!getServer().getPluginManager().isPluginEnabled("Vault")) {
    			log.warning("Vault seemms to be not installed. Falling back to item reward...");
    			money = false;
    		}
    	}
    	users = new YamlConfiguration();
    	if(!userFile.exists()) {
			try {
				userFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
		try {
			users.load(userFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		if(money) {
			moneyRewarder rewarder = new moneyRewarder();
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, rewarder, 1200, 1200);
		} else {
			itemRewarder rewarder = new itemRewarder();
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, rewarder, 1200, 1200);
		}
		TPSmeter meter = new TPSmeter();
		getServer().getScheduler().scheduleSyncRepeatingTask(this,meter,0,g.interval);
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(dies, new Runnable() {
			   public void run() {
			       if(g.tps<17.0) {
			    	   log.warning("Your TPS rate is too low: "+g.tps);
			    	   log.warning("If you get this warning frequently, you should try to reduce the servers load!");
			       }
			   }
		}, 2500L, 3600L);
		try {
			sendStats();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info(this.toString()+" enabled!");
	}
    
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(cmd.getName().equalsIgnoreCase("payday")){
			if(sender.hasPermission("payday.admincommand")) {
				if(args.length==0) {
					sender.sendMessage(ChatColor.RED+"-------------------------------------------------");
					sender.sendMessage(ChatColor.RED+"--------------------PayDay Help-------------------");
					sender.sendMessage(ChatColor.RED+"-------------------------------------------------");
					sender.sendMessage(ChatColor.RED+"/payday reset     | Deletes ALL user data!");
					sender.sendMessage(ChatColor.RED+"/payday reload   | Reloads the config.yml");
					sender.sendMessage(ChatColor.RED+"/payday tps         | Find out the servers tps rate");
					sender.sendMessage(ChatColor.RED+"-------------------------------------------------");
					sender.sendMessage(ChatColor.RED+"For more info visit http://dev.bukkit.org/server-mods/payday/");
					sender.sendMessage(ChatColor.RED+"-------------------------------------------------");
					return true;
				}
				if(args[0].equalsIgnoreCase("reload")) {
					reloadConfig();
					sender.sendMessage(ChatColor.DARK_GREEN+"config.yml reloaded!");
				} else if(args[0].equalsIgnoreCase("reset")) {
					File userFile = new File(getDataFolder()+System.getProperty("file.separator")+"users.yml");
					try {
						users.save(userFile);
						userFile.delete();
						userFile.createNewFile();
						users.load(userFile);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InvalidConfigurationException e) {
						e.printStackTrace();
					}
					sender.sendMessage(ChatColor.DARK_GREEN+"users.yml has been cleared.");
				} else if(args[0].equalsIgnoreCase("tps")) {
					sender.sendMessage(ChatColor.GOLD+""+g.tps+" TPS ["+(20*(g.tps/100))+"%]");
				} else {
					sender.sendMessage(ChatColor.RED+"-------------------------------------------------");
					sender.sendMessage(ChatColor.RED+"--------------------PayDay Help-------------------");
					sender.sendMessage(ChatColor.RED+"-------------------------------------------------");
					sender.sendMessage(ChatColor.RED+"/payday reset     | Deletes ALL user data!");
					sender.sendMessage(ChatColor.RED+"/payday reload   | Reloads the config.yml");
					sender.sendMessage(ChatColor.RED+"/payday tps         | Find out the servers tps rate");
					sender.sendMessage(ChatColor.RED+"-------------------------------------------------");
					sender.sendMessage(ChatColor.RED+"For more info visit http://dev.bukkit.org/server-mods/payday/");
					sender.sendMessage(ChatColor.RED+"-------------------------------------------------");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED+"You don't have permission!");
			}
			return true;
		}
		return false; 
	}
    
    public void onDisable() {
    	Bukkit.getScheduler().cancelAllTasks();
    	File userFile = new File(getDataFolder()+System.getProperty("file.separator")+"users.yml");
    	try {
			users.save(userFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	log.info(this.toString()+" disabled!"); 
    }
    
	public void sendStats() throws IOException {
        Metrics metrics = new Metrics(this);
        Graph graph = metrics.createGraph("Reward type");
        if(payday.money) graph.addPlotter(new Metrics.Plotter("Money") {
        		@Override
        		public int getValue() {
                    return 1;
        		}
        	});
        else graph.addPlotter(new Metrics.Plotter("Item") {
    		@Override
    		public int getValue() {
                return 1;
    		}
    	});
        Graph graph2 = metrics.createGraph("Money paid");
        graph2.addPlotter(new Metrics.Plotter("$") {
    		@Override
    		public int getValue() {
                return g.paid;
    		}
    		@Override
    		public void reset() {
    			g.paid=0;
    		}
    	});
        graph2.addPlotter(new Metrics.Plotter("Items") {
    		@Override
    		public int getValue() {
                return g.given;
    		}
    		@Override
    		public void reset() {
    			g.given=0;
    		}
    	});
        metrics.start();
	}
}
