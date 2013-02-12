package de.brightstorm.CommandHandler;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.brightstorm.PDPlayer;
import de.brightstorm.payday;

public class PlayerCommandHandler implements CommandExecutor {
	public PlayerCommandHandler() {
		
	}
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] arg) {
		String c = cmd.getName();
		if(c=="paycheck" && s instanceof Player) {
			PDPlayer pp = new PDPlayer((Player)s);
			if(!pp.isIgnore()) {
				s.sendMessage(ChatColor.BLUE+StringUtils.replace(payday.conf.getPaycheckMessage(), "%t", String.valueOf(pp.getGroup().getTime()-payday.users.get(pp.getP().getName()))));
			} else {
				s.sendMessage(ChatColor.RED + "You don't have permission!");
			}
			return true;
		}
		return false;
	}
}
