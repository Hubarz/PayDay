package de.brightstorm;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

public class EssentialsInterface {
	Essentials ie;
	public EssentialsInterface() {
		ie = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
	}
	
	public boolean isAfk(Player p) {
		User user = (User) ie.getUser(p);
		return user.isAfk();
	}
}
