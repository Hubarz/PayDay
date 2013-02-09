package de.brightstorm.rewarders;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
 
public class EssentialsInterface
{
	Essentials ie;

	public EssentialsInterface()
	{
		this.ie = ((Essentials)Bukkit.getServer().getPluginManager().getPlugin("Essentials"));
	}
	
	public boolean isAfk(Player p) {
		User user = this.ie.getUser(p);
		return user.isAfk();
	}
}