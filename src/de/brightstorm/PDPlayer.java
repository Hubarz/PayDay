package de.brightstorm;

import org.bukkit.entity.Player;

public class PDPlayer {
	private Player p;
	private boolean ignore = false;
	private de.brightstorm.config.group Group;
	
	public PDPlayer(Player p) {
		super();
		this.p = p;
	}
	public Player getP() {
		return p;
	}
	public void setP(Player p) {
		this.p = p;
	}
	public boolean isIgnore() {
		return ignore;
	}
	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}
	
	public de.brightstorm.config.group getGroup() {
		return Group;
	}
	public void setGroup(de.brightstorm.config.group group) {
		Group = group;
	}
	
	public void findGroup() {
		this.ignore = true;
		for (de.brightstorm.config.group g : payday.conf.getGroups()) {
			if (this.p.hasPermission("payday." + g.getName())) {
				this.Group = g;
				this.ignore = false;
				return;
			}
		}
	}
}
