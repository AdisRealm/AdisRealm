package adiitya.adisrealm.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.*;

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

	protected String getUsage() {
		return Bukkit.getPluginCommand(getName()).getUsage();
	}

	public final String getName() {
		return name;
	}

	abstract void execute(CommandSender sender, String alias, String[] args);
}
