package adiitya.adisrealm.commands.nickname;

import adiitya.adisrealm.command.MainCommand;
import adiitya.adisrealm.command.completion.TabCompleter;
import adiitya.adisrealm.command.completion.TabCompletion;
import adiitya.adisrealm.command.completion.TabCompletions;
import adiitya.adisrealm.utils.DataManager;
import adiitya.adisrealm.utils.MinecraftUtils;
import adiitya.adisrealm.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class NicknameMainCommand extends MainCommand {

	public NicknameMainCommand() {
		super("nickname", "", 0, Arrays.asList(
				new NicknameListCommand(),
				new NicknameAddCommand(),
				new NicknameRemoveCommand()
		));
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
