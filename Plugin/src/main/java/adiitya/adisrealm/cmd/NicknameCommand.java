package adiitya.adisrealm.cmd;

import adiitya.adisrealm.DatabaseManager;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class NicknameCommand implements ICommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length > 0) {

			String arg = args[0];

			if (arg.equalsIgnoreCase("list"))
				list(sender, args);
			else if (arg.equalsIgnoreCase("add"))
				addNickname(sender, args);
			else if (arg.equalsIgnoreCase("remove"))
				removeNickname(sender, args);
			else
				sender.sendMessage("§cUSAGE: /" + label + " <add | remove| list> ...");
		} else
			sender.sendMessage("§cUSAGE: /" + label + " <add | remove | list> ...");

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

		List<String> matches = Lists.newArrayList();

		if (args.length < 1)
			matches.addAll(Arrays.asList("add", "remove", "list"));

		return matches;
	}

	@Override
	public String getName() {
		return "nickname";
	}

	private void addNickname(CommandSender sender, String[] args) {

		if (args.length > 1) {
			if (sender instanceof Player) {
				DatabaseManager.addNickname(((Player) sender).getUniqueId(), args[1]);
				sender.sendMessage("§9Added nickname §c" + args[1]);
			} else
				sender.sendMessage("§cYou must be a player to use that!");
		} else
			sender.sendMessage("§cUSAGE: /nickname add <nickname>");
	}

	private void removeNickname(CommandSender sender, String[] args) {

		if (args.length > 1) {
			if (sender instanceof Player) {
				DatabaseManager.removeNickname(((Player) sender).getUniqueId(), args[1]);
				sender.sendMessage("§9Removed nickname §c" + args[1]);
			} else
				sender.sendMessage("§cYou must be a player to use that!");
		} else
			sender.sendMessage("§cUSAGE: /nickname remove <nickname>");
	}

	private void list(CommandSender sender, String[] args) {

		if (args.length > 1)
			list(sender, args[1]);
		else if (sender instanceof Player)
			list(sender, ((Player) sender).getUniqueId());
		else
			sender.sendMessage("§cYou must be a player to use that!");
	}

	private void list(CommandSender sender, String name) {

		Optional<UUID> uuid = DatabaseManager.getUserFromNickname(name);
		list(sender, uuid.orElse(UUID.randomUUID()));
	}

	private void list(CommandSender sender, UUID uuid) {

		Player p = Bukkit.getPlayer(uuid);
		List<String> nicknames = DatabaseManager.getNicknames(uuid).stream()
				.filter(n -> !n.equalsIgnoreCase(p.getName()))
				.collect(Collectors.toList());

		if (nicknames.size() == 0)
			sender.sendMessage(p.getDisplayName() + " §9has no nicknames");
		else {
			sender.sendMessage("§9Nicknames for §r" + p.getDisplayName());
			nicknames.forEach(nick -> sender.sendMessage("§c> §9" + nick));
		}
	}
}
