package de.brightstorm.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.plugin.Plugin;

public class UserDB {
	private Map<String, Integer> users;
	private File file;
	private Writer w;
	private Reader r;
	private Gson gson;

	@SuppressWarnings("unchecked")
	public UserDB(Plugin pl) throws IOException {
		users = new HashMap<String, Integer>();
		file = new File(pl.getDataFolder() + System.getProperty("file.separator") + "users.json");
		if(!file.exists()) file.createNewFile();
		gson = new Gson();
		r = new FileReader(file);
		users = gson.fromJson(r, users.getClass());
		r.close();
		
		w = new FileWriter(file);
	}
	
	public void increase(String name) {
		if(users.containsKey(name)) users.put(name, new Integer(1));
		else users.put(name, users.get(name)+1);
		gson.toJson(users, users.getClass(), w);
	}
	
	public int get(String name) {
		if(users.containsKey(name)) users.put(name, new Integer(1));
		return users.get(name);
	}
	
	public void reset(String name) {
		users.put(name, 0);
	}
	
	public void save() throws IOException {
		gson.toJson(users, users.getClass(), w);
		w.flush();
	}
}
