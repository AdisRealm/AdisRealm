package adiitya.adisrealm.cmd;

import org.bukkit.command.*;

import java.util.List;

public interface ICommand extends TabExecutor {

	@Override
	boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args);

	@Override
	List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args);

	String getName();
}
