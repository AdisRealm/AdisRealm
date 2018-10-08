package adiitya.adisrealm.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public interface ICommand extends CommandExecutor, TabCompleter {

	@Override
	boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args);

	@Override
	List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args);
}
