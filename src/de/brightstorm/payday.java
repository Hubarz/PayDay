package de.brightstorm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import de.brightstorm.CommandHandler.AdminCommandHandler;
import de.brightstorm.CommandHandler.PlayerCommandHandler;
import de.brightstorm.config.Config;
import de.brightstorm.config.ConfigReader;
import de.brightstorm.config.UserDB;
import de.brightstorm.config.group;
import de.brightstorm.rewarders.ItemRewarder;
import de.brightstorm.rewarders.MoneyRewarder;

@SuppressWarnings("unused")
public class payday extends JavaPlugin {
	public static Logger log;
	public static Config conf;
	public static UserDB users;
	
	private Rewarder rewarder;
	private Statistics stats;
	private int exceptionCount=0;
	private Updater updater;
	private static ConfigReader confReader;
	
	public void onEnable() {
		try {
			log = getLogger();
			
			confReader = new ConfigReader(this);
			conf = confReader.parse();
			stats = new Statistics(this);
			
			users = new UserDB(this);
			
			if(conf.isAutoUpdate()) updater=new Updater(this, "payday", this.getFile(), Updater.UpdateType.DEFAULT, true);
			
			if(conf.isMoney()) rewarder = new MoneyRewarder();
			else rewarder = new ItemRewarder();
			
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, rewarder, 1200L, 1200L);
			
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
					@Override
					public void run() {
						try {
							payday.users.save();
						} catch (IOException e) {
							ExceptionHandler.report(e);
						}
					} 
			}, 5000, 5000);
			
			getCommand("paycheck").setExecutor(new PlayerCommandHandler());
			getCommand("payday").setExecutor(new AdminCommandHandler());
			
			getCommand("paydebug").setExecutor(new CommandExecutor() {

				@Override
				public boolean onCommand(CommandSender arg0, Command arg1,
						String arg2, String[] arg3) {
					arg0.sendMessage("Command: "+arg1.getName());
					arg0.sendMessage(ReflectionToStringBuilder.toString(conf)+"\n");
					for(group gr : conf.getGroups()) arg0.sendMessage(ReflectionToStringBuilder.toString(gr)+"\n");
					return false;
				}
				
			});
			
			log.info(this.toString()+" enabled!");
		} catch (java.lang.Exception e) {
			ExceptionHandler.report(e);
		}
	}
	
	public void onDisable() {
		try {
			users.save();
			Bukkit.getScheduler().cancelTasks(this);
		} catch (Exception e) {
			ExceptionHandler.report(e);
		}
		log.info(this.toString()+" disabled!");
	}
	
	public static void reload() {
		conf = confReader.parse();
	}
}
