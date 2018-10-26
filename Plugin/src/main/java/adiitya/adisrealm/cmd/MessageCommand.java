package adiitya.adisrealm.cmd;

import adiitya.adisrealm.utils.MessageManager;
import adiitya.adisrealm.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
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

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

		List<String> results = new ArrayList<>();

		if (args.length == 1)
			results.addAll(
					Bukkit.getOnlinePlayers()
							.stream()
							.map(Player::getName)
							.collect(Collectors.toList()));
		else if (args.length == 2)
			results.add("Hello, " + args[0] + "!");

		return results;
	}
}
