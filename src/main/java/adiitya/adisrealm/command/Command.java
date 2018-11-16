package adiitya.adisrealm.command;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class Command implements TabExecutor {

	@Getter private final String name;
	protected final String usage;
	@Getter private final Predicate<Integer> argumentCount;
	@Getter private final List<Command> children;

	public Command(String name, String usage, Predicate<Integer> argumentCount) {
		this(name, usage, argumentCount, new ArrayList<>());
	}

	public Command(String name, String usage, Predicate<Integer> argumentCount, List<Command> children) {
		this.name = name;
		this.usage = usage;
		this.argumentCount = argumentCount;
		this.children = children;
	}

	public abstract void execute(CommandSender sender, List<String> args);
	public abstract List<String> tabComplete(CommandSender sender, List<String> args);

	public abstract String getUsage();
}
