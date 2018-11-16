package adiitya.adisrealm.commands;

import adiitya.adisrealm.command.SingleCommand;
import adiitya.adisrealm.command.completion.TabCompleter;
import adiitya.adisrealm.command.completion.TabCompletion;
import adiitya.adisrealm.command.completion.TabCompletions;
import adiitya.adisrealm.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class ReplyCommand extends SingleCommand {

	public ReplyCommand() {
		super("r", "/r <message>");
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use that!");
			return;
		}

		Player player = (Player) sender;
		Optional<UUID> lastRecipient = MessageManager.getLastRecipient(player.getUniqueId());

		if (args.isEmpty()) {
			sender.sendMessage("§cUsage: " + getUsage());
		} else if (!lastRecipient.isPresent()) {
			sender.sendMessage("§9You don't have anyone to reply to");
		} else if (!Bukkit.getOfflinePlayer(lastRecipient.get()).isOnline()) {
			sender.sendMessage("§cThat player is offline");
		} else {

			String message = String.join(" ", args);
			Player target = Bukkit.getPlayer(lastRecipient.get());
			MessageManager.sendMessage(player, target, message);
		}
	}

	@Override
	public List<String> tabComplete(CommandSender sender, List<String> args) {
		return new TabCompleter()
				.add(1, getReplyTarget(sender))
				.get(args);
	}

	private TabCompletion getReplyTarget(CommandSender sender) {

		if (!(sender instanceof Player))
			return TabCompletions.always("Nobody");

		Player player = (Player) sender;
		Optional<UUID> uuid = MessageManager.getLastRecipient(player.getUniqueId());

		if (!uuid.isPresent())
			return TabCompletions.always("Nobody");

		Player target = Bukkit.getPlayer(uuid.get());

		if (!target.isOnline())
			return TabCompletions.always("Nobody");

		return new TabCompletion(target.getDisplayName(), test -> true);
	}
}
