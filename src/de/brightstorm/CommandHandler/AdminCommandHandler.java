package de.brightstorm.CommandHandler;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.brightstorm.payday;

public class AdminCommandHandler implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender p, Command cmd, String label, String[] arg) {
		String c = cmd.getName();
		if(c=="payday") {
			if(p.hasPermission("payday.admincommand")) {
				if(arg.length==0 || arg[0]=="help") help(p);
				else if(arg[0]=="reload") payday.reload();
				else if(arg[0]=="reset") payday.users.reset();
			} else warn(p);
			return true;
		}
		return false;
	}

	void warn(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "You don't have permission!");
	}

	void help(CommandSender sender) {
		sender.sendMessage(ChatColor.RED
				+ "-----------------------------------------------------");
		sender.sendMessage(ChatColor.RED + "--------------------"
				+ ChatColor.BOLD + "PayDay Help" + ChatColor.RESET
				+ ChatColor.RED + "---------------------");
		sender.sendMessage(ChatColor.RED
				+ "-----------------------------------------------------");
		sender.sendMessage(ChatColor.RED
				+ "/payday reset     | Deletes ALL user data!");
		sender.sendMessage(ChatColor.RED
				+ "/payday reload    | Reloads the config.json");
		sender.sendMessage(ChatColor.RED
				+ "-----------------------------------------------------");
		sender.sendMessage(ChatColor.RED
				+ ""
				+ ChatColor.ITALIC
				+ "For more info visit http://dev.bukkit.org/server-mods/payday/");
		sender.sendMessage(ChatColor.RED
				+ "-----------------------------------------------------");
	}

}
