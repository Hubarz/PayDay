package de.brightstorm;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import de.brightstorm.rewarders.EssentialsInterface;

public abstract class Rewarder {
	private EssentialsInterface ei;
	
	public Rewarder() {
		if(payday.conf.isUseEssentials()) ei = new EssentialsInterface();
	}
	
	public abstract void pay(PDPlayer pp);
	
	public void run() {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			PDPlayer pp = new PDPlayer(p);
			pp.findGroup();
			if(!pp.isIgnore()) {
				if(payday.conf.isUseEssentials()) if(!ei.isAfk(p)) check(pp);
				else check(pp);
			}
		}
	}

	private void check(PDPlayer pp) {
		if(!payday.users.containsKey(pp.getP().getName())) payday.users.put(pp.getP().getName(), new Integer(1));
		if(payday.users.get(pp.getP().getName())>=pp.getGroup().getTime() && !(payday.conf.getWorldRestriction().contains(pp.getP().getLocation().getWorld().getName())))
			pay(pp);
	}
	
	protected void log(PDPlayer pp, String amount) {
		String raw = payday.conf.getMessage();
		String temp = StringUtils.replace(raw, "%a", amount);
		pp.getP().sendMessage(Color.BLUE+StringUtils.replace(temp, "%t", String.valueOf(pp.getGroup().getTime())));
		payday.log.info(pp.getP().getName()+" got "+amount+" for being online "+String.valueOf(pp.getGroup().getTime())+" minutes.");
	}
	
}
