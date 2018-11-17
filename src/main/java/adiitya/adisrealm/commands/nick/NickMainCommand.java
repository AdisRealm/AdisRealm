package adiitya.adisrealm.commands.nick;

import adiitya.adisrealm.command.MainCommand;
import adiitya.adisrealm.command.completion.TabCompleter;
import adiitya.adisrealm.command.completion.TabCompletion;
import adiitya.adisrealm.command.completion.TabCompletions;
import adiitya.adisrealm.utils.DataManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class NickMainCommand extends MainCommand {

	public NickMainCommand() {
		super("nick", "", 0);
	}

	@Override
	protected void initChildren() {

		children.add(new NickListCommand(this));
		children.add(new NickAddCommand(this));
		children.add(new NickRemoveCommand(this));
	}

	@Override
	public List<String> tabComplete(CommandSender sender, List<String> args) {

		return new TabCompleter()
				.add(1, new TabCompletion("add", test -> TabCompletions.startsWith(test, "add")))
				.add(1, new TabCompletion("remove", test -> TabCompletions.startsWith(test, "remove")))
				.add(1, new TabCompletion("list", test -> TabCompletions.startsWith(test, "list")))
				.add(2, getRemoveCompletions(sender, args))
				.add(2, getListCompletions(args))
				.get(args);
	}

	private List<TabCompletion> getListCompletions(List<String> args) {

		List<TabCompletion> tabCompletions = new ArrayList<>();

		if (args.size() < 2)
			return tabCompletions;

		if (!"list".equalsIgnoreCase(args.get(0)))
			return tabCompletions;

		return TabCompletions.players();
	}

	private List<TabCompletion> getRemoveCompletions(CommandSender sender, List<String> args) {

		if (args.size() < 2)
			return Collections.emptyList();

		if (!args.get(0).equalsIgnoreCase("remove"))
			return Collections.emptyList();

		if (!(sender instanceof Player))
			return Collections.emptyList();

		Player player = (Player) sender;

		return DataManager.getNicknames(player.getUniqueId())
				.stream()
				.map(nick -> new TabCompletion(nick, args.get(0)::startsWith))
				.collect(Collectors.toList());
	}
}
