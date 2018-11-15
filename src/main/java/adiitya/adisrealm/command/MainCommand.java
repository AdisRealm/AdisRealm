package adiitya.adisrealm.command;

import org.bukkit.Bukkit;
import org.bukkit.command.*;

import java.util.Arrays;
import java.util.List;

public abstract class MainCommand implements TabExecutor {

	protected final String name;

	public MainCommand(String name) {
		this.name = name;
	}

	@Override
	public final boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

		execute(sender, label, Arrays.asList(args));

		return true;
	}

	@Override
	public final List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
		return tabComplete(sender, Arrays.asList(args));
	}

	protected String getUsage() {
		return Bukkit.getPluginCommand(getName()).getUsage();
	}

	public final String getName() {
		return name;
	}

	public abstract void execute(CommandSender sender, String alias, List<String> args);
	public abstract List<String> tabComplete(CommandSender sender, List<String> args);
}
