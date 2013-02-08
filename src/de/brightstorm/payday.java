package de.brightstorm;

import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class payday extends JavaPlugin {
	public static Logger log;
	public static Config conf;
	public static FileConfiguration users;
	
	private Statistics stats;
	private int exceptionCount=0;
	private Updater updater;
	
	public void onEnable() {
		try {
			log = getLogger();
			
			conf = new Config(this);
			stats = new Statistics(this);
			
			if(conf.autoUpdate) updater=new Updater(this, "payday", this.getFile(), Updater.UpdateType.DEFAULT, true);
			
			log.info(this.toString()+" enabled!");
		} catch (java.lang.Exception e) {
			ExceptionHandler.report(e);
		}
	}
	
	public void onDisable() {
		
		log.info(this.toString()+" disabled!");
	}
}
