package adiitya.adisrealm.commands;

import adiitya.adisrealm.command.PlayerCommand;
import adiitya.adisrealm.command.completion.TabCompleter;
import adiitya.adisrealm.command.completion.TabCompletion;
import adiitya.adisrealm.utils.MessageManager;
import adiitya.adisrealm.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public final class MessageCommand extends PlayerCommand {

	public MessageCommand() {
		super("msg");
	}

	@Override
	public void execute(Player sender, String label, String[] args) {

		if (args.length < 2)
			sender.sendMessage("§cUsage: " + getUsage());
		else {

			Optional<UUID> target = Utils.getUUID(args[0]);

			if (target.isPresent())
				processMessage(sender, target.get(), args);
			else
				sender.sendMessage("§cPlayer not found");
		}
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String[] args) {
		return new TabCompleter()
				.add(1, getPlayerCompletions(args))
				.get(args);
	}

	private List<TabCompletion> getPlayerCompletions(String[] args) {

		if (args.length < 1)
			return Collections.singletonList(new TabCompletion("", test -> false));

		return Bukkit.getOnlinePlayers().stream()
				.map((Player p) -> new TabCompletion(p.getName(), test -> p.getName().startsWith(test)))
				.collect(Collectors.toList());
	}

	private void processMessage(Player sender, UUID target, String[] args) {

		OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(target);

		if (!targetPlayer.isOnline())
			sender.sendMessage(targetPlayer.getName() + "§9 is offline");
		else {

			String message = Arrays.stream(args, 1, args.length)
					.collect(Collectors.joining(" "));

			MessageManager.sendMessage(sender, targetPlayer.getPlayer(), message);
		}
	}
}
