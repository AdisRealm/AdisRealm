package adiitya.adisrealm.cmd.msg;

import adiitya.adisrealm.cmd.ICommand;
import adiitya.adisrealm.utils.MinecraftUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public final class ReplyCommand implements ICommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) sender.sendMessage("You must be a player to use that!");
		else {

			Player player = (Player) sender;
			Optional<UUID> lastRecipient = MessageManager.getLastRecipient(player.getUniqueId());

			if (!lastRecipient.isPresent()) sender.sendMessage("You don't have anyone to reply to");
			else if (!Bukkit.getOfflinePlayer(lastRecipient.get()).isOnline()) sender.sendMessage("That player is offline");
			else if (args.length < 1) sender.sendMessage("Please enter a message!");
			else {

				String message = String.join(" ", args);
				Player target = Bukkit.getPlayer(lastRecipient.get());
				MessageManager.sendMessage(player, target, message);
			}
		}

		return true;
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
