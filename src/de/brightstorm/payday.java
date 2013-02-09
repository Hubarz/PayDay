package de.brightstorm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.brightstorm.config.Config;
import de.brightstorm.config.ConfigReader;
import de.brightstorm.config.UserDB;
import de.brightstorm.rewarders.ItemRewarder;
import de.brightstorm.rewarders.MoneyRewarder;

@SuppressWarnings("unused")
public class payday extends JavaPlugin {
	public static Logger log;
	private ConfigReader confReader;
	public static Config conf;
	public static UserDB users;
	
	private Rewarder rewarder;
	private Statistics stats;
	private int exceptionCount=0;
	private Updater updater;
	
	public void onEnable() {
		try {
			log = getLogger();
			
			confReader = new ConfigReader(this);
			conf = confReader.parse();
			stats = new Statistics(this);
			
			users = new UserDB(this);
			
			if(conf.isAutoUpdate()) updater=new Updater(this, "payday", this.getFile(), Updater.UpdateType.NO_VERSION_CHECK, true);
			
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
			
			log.info(this.toString()+" enabled!");
		} catch (java.lang.Exception e) {
			ExceptionHandler.report(e);
		}
	}
	
	public void onDisable() {
		try {
			users.save();
		} catch (Exception e) {
			ExceptionHandler.report(e);
		}
		log.info(this.toString()+" disabled!");
	}
}
