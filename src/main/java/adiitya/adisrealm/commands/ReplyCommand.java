package adiitya.adisrealm.commands;

import adiitya.adisrealm.command.PlayerCommand;
import adiitya.adisrealm.command.completion.TabCompleter;
import adiitya.adisrealm.command.completion.TabCompletion;
import adiitya.adisrealm.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class ReplyCommand extends PlayerCommand {

	public ReplyCommand() {
		super("r");
	}

	@Override
	public void execute(Player sender, String label, String[] args) {

		Optional<UUID> lastRecipient = MessageManager.getLastRecipient(sender.getUniqueId());

		if (args.length < 1) {
			sender.sendMessage("§cUsage: " + getUsage());
		} else if (!lastRecipient.isPresent()) {
			sender.sendMessage("§9You don't have anyone to reply to");
		} else if (!Bukkit.getOfflinePlayer(lastRecipient.get()).isOnline()) {
			sender.sendMessage("§cThat player is offline");
		} else {

			String message = String.join(" ", args);
			Player target = Bukkit.getPlayer(lastRecipient.get());
			MessageManager.sendMessage(sender, target, message);
		}
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String[] args) {
		return new TabCompleter()
				.add(1, getReplyTarget(sender))
				.get(args);
	}

	private TabCompletion getReplyTarget(CommandSender sender) {

		if (!(sender instanceof Player))
			return new TabCompletion("Nobody", test -> true);

		Player player = (Player) sender;
		Optional<UUID> uuid = MessageManager.getLastRecipient(player.getUniqueId());

		if (!uuid.isPresent())
			return new TabCompletion("Nobody", test -> true);

		Player target = Bukkit.getPlayer(uuid.get());

		if (!target.isOnline())
			return new TabCompletion("Nobody", test -> true);

		return new TabCompletion(target.getDisplayName(), test -> true);
	}
}
