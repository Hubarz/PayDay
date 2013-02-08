package de.brightstorm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.Yaml;

import de.brightstorm.config.group;

public class Config {
	Config(Plugin plugin) throws IOException {
		File configFile= new File(plugin.getDataFolder() + System.getProperty("file.separator") + "users.yml");
		if(!configFile.exists()) {
			plugin.getLogger().info("config.yml not found. Creating a new one...");
			FileUtils.copyURLToFile(getClass().getResource("config.yml"), configFile);
		}
		config.load(new FileInputStream(configFile));
		
	}
	
	private Yaml config;
	public boolean money;
	public boolean autoUpdate;
	public ArrayList<group> groups = new ArrayList<group>();
}
