package adiitya.adisrealm.cmd;

import adiitya.adisrealm.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class ReplyCommand extends PlayerCommand {

	@Override
	public void execute(Player sender, Command command, String label, String[] args) {

		Optional<UUID> lastRecipient = MessageManager.getLastRecipient(sender.getUniqueId());

		if (args.length < 1) {
			sender.sendMessage("§cUsage: " + command.getUsage());
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
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return Collections.emptyList();
	}

	@Override
	public String getName() {
		return "r";
	}
}
