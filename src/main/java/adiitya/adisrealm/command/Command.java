package adiitya.adisrealm.command;

import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {

	@Getter private final String name;
	@Getter private final String usage;
	@Getter private final List<Command> children;

	public Command(String name, String usage) {
		this(name, usage, new ArrayList<>());
	}

	public Command(String name, String usage, List<Command> children) {
		this.name = name;
		this.usage = usage;
		this.children = children;
	}

	public abstract void execute(CommandSender sender, List<String> args);
	public abstract List<String> tabComplete(CommandSender sender, List<String> args);
}
