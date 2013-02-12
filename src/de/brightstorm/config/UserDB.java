package de.brightstorm.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;
import org.bukkit.plugin.Plugin;

public class UserDB {
	private Map<String, Integer> users;
	private File file;
	private Writer w;
	private Reader r;
	private Gson gson;
	private Type mapType = new TypeToken<Map<String, Integer> >() {}.getType();
	
	public UserDB(Plugin pl) throws IOException {
		users = new HashMap<String, Integer>();
		file = new File(pl.getDataFolder() + System.getProperty("file.separator") + "users.json");
		gson = new Gson();
		if(file.exists()) {
			r = new FileReader(file);
			users = gson.fromJson(r, mapType);
			r.close();
		} else 	file.createNewFile();
		w = new FileWriter(file);
	}
	
	public void increase(String name) {
		if(!users.containsKey(name)) users.put(name, new Integer(1));
		else users.put(name, new Integer(users.get(name)+1));

	}
	
	public int get(String name) {
		if(!users.containsKey(name)) users.put(name, new Integer(1));
		return users.get(name);
	}
	
	public void reset(String name) {
		users.put(name, new Integer(0));
	}
	
	public void save() throws IOException {
		w = new FileWriter(file);
		gson.toJson(users, mapType, w);
		w.close();
	}
	
	public void reset() {
		users.clear();
	}
}
