package adiitya.adisrealm.command;

import org.bukkit.Bukkit;
import org.bukkit.command.*;

import java.util.List;

public abstract class Command implements TabExecutor {

	protected final String name;

	public Command(String name) {
		this.name = name;
	}

	@Override
	public final boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

		execute(sender, label, args);

		return true;
	}

	@Override
	public final List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
		return tabComplete(sender, args);
	}

	protected String getUsage() {
		return Bukkit.getPluginCommand(getName()).getUsage();
	}

	public final String getName() {
		return name;
	}

	public abstract void execute(CommandSender sender, String alias, String[] args);
	public abstract List<String> tabComplete(CommandSender sender, String[] args);
}
