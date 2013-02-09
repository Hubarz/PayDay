package de.brightstorm.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.plugin.Plugin;

public class UserDB {
	private HashMap<String, Long> users;
	private File file;
	private Writer w;
	private Reader r;
	private Gson gson;

	@SuppressWarnings("unchecked")
	public UserDB(Plugin pl) throws IOException {
		users = new HashMap<String, Long>();
		file = new File(pl.getDataFolder() + System.getProperty("file.separator") + "users.json");
		gson = new Gson();
		if(file.exists()) {
			r = new FileReader(file);
			users = gson.fromJson(r, users.getClass());
			r.close();
		} else 	file.createNewFile();
		w = new FileWriter(file);
	}
	
	public void increase(String name) {
		if(users.containsKey(name)) users.put(name, new Long(1));
		else users.put(name, new Long(users.get(name)+1));

	}
	
	public long get(String name) {
		if(!users.containsKey(name)) users.put(name, new Long(1));
		return users.get(name);
	}
	
	public void reset(String name) {
		users.put(name, new Long(0));
	}
	
	public void save() throws IOException {
		w = new FileWriter(file);
		gson.toJson(users, users.getClass(), w);
		w.close();
	}
	
	public void reset() {
		users.clear();
	}
}
