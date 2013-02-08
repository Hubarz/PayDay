package de.brightstorm;

import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.brightstorm.config.Config;
import de.brightstorm.config.ConfigReader;

@SuppressWarnings("unused")
public class payday extends JavaPlugin {
	public static Logger log;
	private ConfigReader confReader;
	public static Config conf;
	
	private Statistics stats;
	private int exceptionCount=0;
	private Updater updater;
	
	public void onEnable() {
		try {
			log = getLogger();
			
			confReader = new ConfigReader(this);
			conf = confReader.parse();
			stats = new Statistics(this);
			
			if(conf.isAutoUpdate()) updater=new Updater(this, "payday", this.getFile(), Updater.UpdateType.DEFAULT, true);
			
			log.info(this.toString()+" enabled!");
		} catch (java.lang.Exception e) {
			ExceptionHandler.report(e);
		}
	}
	
	public void onDisable() {
		
		log.info(this.toString()+" disabled!");
	}
}
