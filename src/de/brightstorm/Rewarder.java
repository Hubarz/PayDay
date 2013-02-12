package de.brightstorm;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import de.brightstorm.rewarders.EssentialsInterface;

public abstract class Rewarder implements Runnable {
	private EssentialsInterface ei;
	
	public Rewarder() {
		if(payday.conf.isUseEssentials()) ei = new EssentialsInterface();
	}
	
	public abstract void pay(PDPlayer pp);
	
	public void run() {
		try {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				PDPlayer pp = new PDPlayer(p);
				pp.findGroup();
				if(!pp.isIgnore()) {
					payday.users.increase(pp.getP().getName());
					if(payday.conf.isUseEssentials()) if(!ei.isAfk(p)) check(pp);
					else check(pp);
				}
			}
		} catch(Exception e) {
			ExceptionHandler.report(e);
		}
	}

	private void check(PDPlayer pp) {
		if(payday.users.get(pp.getP().getName())>=pp.getGroup().getTime() && !(payday.conf.getWorldRestriction().contains(pp.getP().getLocation().getWorld().getName())))
			pay(pp);
	}
	
	protected void log(PDPlayer pp, String amount) {
		String raw = payday.conf.getMessage();
		String temp = StringUtils.replace(raw, "%a", amount);
		pp.getP().sendMessage(ChatColor.BLUE+StringUtils.replace(temp, "%t", String.valueOf(pp.getGroup().getTime())));
		payday.log.info(pp.getP().getName()+" got "+amount+" for being online "+String.valueOf(pp.getGroup().getTime())+" minutes.");
	}
	
}
