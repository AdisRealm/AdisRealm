package adiitya.adisrealm.command;

import org.bukkit.command.CommandSender;

import java.util.*;

public abstract class MainCommand extends Command {

	private final int requiredArgs;

	public MainCommand(String name, String usage, int requiredArgs) {
		super(name, usage, i -> false);
		this.requiredArgs = requiredArgs;
		initChildren();
	}

	public MainCommand(String name, String usage, int requiredArgs, List<Command> children) {
		super(name, usage, i -> false, children);
		this.requiredArgs = requiredArgs;
	}

	protected abstract void initChildren();

	@Override
	public final boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		execute(sender, Arrays.asList(args));
		return true;
	}

	@Override
	public final List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
		return tabComplete(sender, Arrays.asList(args));
	}

	public void execute(CommandSender sender, List<String> args) {

		if (args.size() < requiredArgs || args.isEmpty()) {
			sender.sendMessage(String.format("§cUSAGE: %s", getUsage()));
			return;
		}

		Optional<Command> subOptional = getChildren().stream()
				.filter(child -> child.getName().equalsIgnoreCase(args.get(Math.max(requiredArgs - 1, 0))))
				.findAny();

		if (!subOptional.isPresent()) {
			sender.sendMessage(String.format("§cUSAGE: %s", getUsage()));
			return;
		}

		Command subCommand = subOptional.get();
		List<String> subArgs = new ArrayList<>();

		if (args.size() > requiredArgs)
				subArgs.addAll(args.subList(Math.max(requiredArgs, 1), args.size()));

		if (!subCommand.getArgumentCount().test(subArgs.size())) {
			sender.sendMessage(String.format("§cUSAGE: %s", subCommand.getUsage()));
			return;
		}

		subCommand.execute(sender, subArgs);
	}

	public List<String> tabComplete(CommandSender sender, List<String> args) {
		return Collections.emptyList();
	}

	@Override
	public String getUsage() {

		if (getChildren().isEmpty())
			return getRawUsage();

		List<String> usageList = new ArrayList<>();
		usageList.add(getRawUsage());

		StringBuilder subUsage = new StringBuilder("<");
		List<String> subList = new ArrayList<>();

		for (Command child : getChildren())
			subList.add(child.getName());

		subUsage.append(String.join(" | ", subList)).append(">");
		usageList.add(subUsage.toString());

		return String.join(" ", usageList);
	}

	@Override
	public String getRawUsage() {
		return "/" + super.getRawUsage();
	}
}
