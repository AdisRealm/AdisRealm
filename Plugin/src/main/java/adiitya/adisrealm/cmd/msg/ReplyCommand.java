package adiitya.adisrealm.cmd.msg;

import adiitya.adisrealm.cmd.PlayerCommand;
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

		if (!lastRecipient.isPresent()) {
			sender.sendMessage("You don't have anyone to reply to");
		} else if (!Bukkit.getOfflinePlayer(lastRecipient.get()).isOnline()) {
			sender.sendMessage("That player is offline");
		} else if (args.length < 1) {
			sender.sendMessage("Please enter a message!");
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
