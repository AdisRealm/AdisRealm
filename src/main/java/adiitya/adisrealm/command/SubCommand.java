package adiitya.adisrealm.command;

import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public abstract class SubCommand extends Command {

	private final Command parent;

	public SubCommand(String name, String usage, Predicate<Integer> argumentCount, Command parent) {

		super(parent.plugin, name, usage, argumentCount);

		this.parent = parent;
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

	@Override
	public String getUsage() {
		return String.format("%s %s", parent.getRawUsage(), getRawUsage()).trim();
	}
}
