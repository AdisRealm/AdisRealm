package adiitya.adisrealm.commands;

import adiitya.adisrealm.AdisRealm;
import adiitya.adisrealm.command.SingleCommand;
import adiitya.adisrealm.command.completion.TabCompleter;
import adiitya.adisrealm.command.completion.TabCompletions;
import adiitya.adisrealm.utils.MessageManager;
import adiitya.adisrealm.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

import static adiitya.adisrealm.utils.NameManager.*;

public final class MessageCommand extends SingleCommand {

	public MessageCommand() {
		super(AdisRealm.getInstance(), "msg", "<player> <msg>");
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {

		if (!(sender instanceof Player))
			sender.sendMessage("You must be a player to use that!");
		else if (args.size() < 2)
			sender.sendMessage("§cUsage: " + getUsage());
		else {

			Player player = (Player) sender;
			Optional<UUID> target = Utils.getUUID(args.get(0));

			if (target.isPresent())
				processMessage(player, target.get(), args);
			else
				sender.sendMessage("§cPlayer not found");
		}
	}

	@Override
	public List<String> tabComplete(CommandSender sender, List<String> args) {
		return new TabCompleter()
				.add(1, TabCompletions.players())
				.get(args);
	}

	private void processMessage(Player sender, UUID target, List<String> args) {

		List<String> list = new ArrayList<>(args);
		OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(target);

		list.remove(0);

		if (!targetPlayer.isOnline())
			sender.sendMessage(getColoredName(targetPlayer.getName()) + "§9 is offline");
		else {

			String message = String.join(" ", list);

			MessageManager.sendMessage(sender, targetPlayer.getPlayer(), message);
		}
	}
}
