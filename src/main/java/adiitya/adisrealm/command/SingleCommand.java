package adiitya.adisrealm.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.List;

public abstract class SingleCommand extends Command implements TabExecutor {

	public SingleCommand(String name, String usage) {
		super(name, usage);
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		execute(sender, Arrays.asList(args));
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
		return tabComplete(sender, Arrays.asList(args));
	}

	@Override
	public abstract void execute(CommandSender sender, List<String> args);

	@Override
	public abstract List<String> tabComplete(CommandSender sender, List<String> args);
}
