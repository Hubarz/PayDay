package de.brightstorm.rewarders;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import de.brightstorm.PDPlayer;
import de.brightstorm.Rewarder;

public class MoneyRewarder extends Rewarder {
	private Economy economy;

	private boolean setupEconomy()
	{
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			this.economy = ((Economy)economyProvider.getProvider());
		}
		return this.economy != null;
	}
	
	public MoneyRewarder() throws Exception {
		if(!setupEconomy()) throw new Exception("Can't setup Vault!");
	}
	
	@Override
	public void pay(PDPlayer pp) {
		if(pp.getGroup().getLimit() >=0.D || economy.getBalance(pp.getP().getName())>pp.getGroup().getLimit()) {
			double amount = pp.getGroup().getAmount()+(economy.getBalance(pp.getP().getName()) / 100.0D * pp.getGroup().getInterest());
			economy.depositPlayer(pp.getP().getName(), amount);
			log(pp, amount+" "+economy.currencyNamePlural());
		}
	}
}
