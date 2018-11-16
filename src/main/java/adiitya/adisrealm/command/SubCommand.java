package adiitya.adisrealm.command;

import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public abstract class SubCommand extends Command {

	public SubCommand(String name, Predicate<Integer> argumentCount) {
		super(name, "", argumentCount);
	}

	@Override
	public final boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		execute(sender, Arrays.asList(args));
		return true;
	}

	@Override
	public final List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
		return tabComplete(sender, Arrays.asList(args));
	}
}
