package de.brightstorm.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.com.google.gson.annotations.SerializedName;

public class Config {
	private boolean money;
	private boolean autoUpdate;
	private boolean useEssentials;
	private short rewardItem;
	private String message;
	private String paycheckMessage;
	private List<group> groups;
	@SerializedName("restrictedWorlds")
	private List<String> worldRestriction;
	private String version;
	
	public Config() {
		version=Bukkit.getServer().getPluginManager().getPlugin("PayDay").getDescription().getVersion();
		money=true;
		autoUpdate=true;
		useEssentials=Bukkit.getServer().getPluginManager().getPlugin("Essentials")!=null;
		rewardItem=264;
		message="You just got %a for being online %t minutes.";
		paycheckMessage="Your next payday is in %t minutes.";
		groups = new ArrayList<group>();
		group normal = new group("normal", (short) 2, 60, 20, 5000, 0.5f);
		group vip = new group("vip", (short) 1, 60, 50, 10000, 2.f);
		groups.add(vip);
		groups.add(normal);
		worldRestriction = new ArrayList<String>();
	}

	public boolean isMoney() {
		return money;
	}

	public void setMoney(boolean money) {
		this.money = money;
	}

	public boolean isAutoUpdate() {
		return autoUpdate;
	}

	public void setAutoUpdate(boolean autoUpdate) {
		this.autoUpdate = autoUpdate;
	}

	public boolean isUseEssentials() {
		return useEssentials;
	}

	public void setUseEssentials(boolean useEssentials) {
		this.useEssentials = useEssentials;
	}

	public short getRewardItem() {
		return rewardItem;
	}

	public void setRewardItem(short rewardItem) {
		this.rewardItem = rewardItem;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPaycheckMessage() {
		return paycheckMessage;
	}

	public void setPaycheckMessage(String paycheckMessage) {
		this.paycheckMessage = paycheckMessage;
	}

	public List<group> getGroups() {
		return groups;
	}

	public void setGroups(List<group> groups) {
		this.groups = groups;
		Collections.sort(this.groups);
	}
	
	public List<String> getWorldRestriction() {
		return worldRestriction;
	}
	
	public void setWorldRestriction(List<String> worldRestriction) {
		this.worldRestriction = worldRestriction;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
