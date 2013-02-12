package de.brightstorm;

import java.util.logging.Logger;

import org.bukkit.Bukkit;

public class ExceptionHandler {
	private static int exceptionCount=0;
	public static void report(Exception e) {
		// TODO add code to automatically report exception
		Logger log = payday.log;
		exceptionCount++;
		log.severe("-----------------------------------------");
		log.severe("------ PayDay encountered an error ------");
		log.severe("Please report this at the Bukkit Dev page");
		log.severe("-----------------------------------------\n");
		e.printStackTrace();
		log.severe("-----------------------------------------");
		log.severe("------------ Exceptions: "+exceptionCount+"/3 ------------");
		if(exceptionCount<3) {
			log.severe("----------- Trying to keep up. ----------");
			log.severe("-----------------------------------------");
		}
		else {
			log.severe("-- Disabling due to too many exceptions --");
			Bukkit.getScheduler().cancelTasks(Bukkit.getServer().getPluginManager().getPlugin("PayDay"));
			Bukkit.getServer().getPluginManager().disablePlugin(Bukkit.getServer().getPluginManager().getPlugin("PayDay"));
		}
	}
}
