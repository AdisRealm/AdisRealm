package adiitya.adisrealm.commands.nickname;

import adiitya.adisrealm.command.MainCommand;
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
	    super("nickname", "/nickname", 0, Arrays.asList(
	    		new NicknameListCommand()
	    ));
	}

	/*@Override
	public void execute(CommandSender sender, List<String> args) {

		//*if (!args.isEmpty()) {

			String arg = args.get(0);

			if (arg.equalsIgnoreCase("list"))
				list(sender, args);
			else if (arg.equalsIgnoreCase("add"))
				addNickname(sender, args);
			else if (arg.equalsIgnoreCase("remove"))
				removeNickname(sender, args);
			else
				sender.sendMessage(getUsage());
		} else {
			sender.sendMessage(getUsage());
		}*./
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
	}*/

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

	private void addNickname(CommandSender sender, List<String> args) {

		if (args.size() > 1) {
			if (sender instanceof Player) {

				Player p = (Player) sender;
				String nick = args.get(1);
				int status = DataManager.addNickname(p.getUniqueId(), nick);

				if (status == 0) // success
					sender.sendMessage("§9Successfully nicknamed you §6" + nick);
				else if (status == 1) // taken
					sender.sendMessage("§9The nickname §6" + nick + " §9is taken");
				else if (status == 2) // duplicate (user already has nick)
					sender.sendMessage("§9You already have the nickname §6" + nick);
				else if (status == 3) // username
					sender.sendMessage("§9The nickname §6" + nick + " §9is somebodies username");
				else if (status == 5) // bad length
					sender.sendMessage("§9The nickname §6" + nick + " §9is too long or short");
				else // error
					sender.sendMessage("§9An unknown error occurred and your nickname hasn't been added");
			} else {
				sender.sendMessage("§cYou must be a player to use that!");
			}
		} else {
			sender.sendMessage("§cUSAGE: /nickname add <nickname>");
		}
	}

	private void removeNickname(CommandSender sender, List<String> args) {

		if (args.size() > 1) {
			if (sender instanceof Player) {
				DataManager.removeNickname(((Player) sender).getUniqueId(), args.get(1));
				sender.sendMessage("§9Removed nickname §c" + args.get(1));
			} else {
				sender.sendMessage("§cYou must be a player to use that!");
			}
		} else {
			sender.sendMessage("§cUSAGE: /nickname remove <nickname>");
		}
	}

	private void list(CommandSender sender, List<String> args) {

		if (args.size() > 1)
			listFromUsername(sender, args.get(1));
		else if (sender instanceof Player)
			listFromUsername(sender, sender.getName());
		else
			sender.sendMessage("§cYou must be a player to use that!");
	}

	private void listFromUsername(CommandSender sender, String name) {

		Optional<UUID> uuid = Utils.getUUID(name);

		if (uuid.isPresent()) {
			listFromUuid(sender, uuid.get());
		} else {
			sender.sendMessage("§cPlayer not found");
		}
	}

	private void listFromUuid(CommandSender sender, UUID uuid) {

		boolean targetExists = MinecraftUtils.playerExists(uuid);

		if (targetExists) {

			String username = MinecraftUtils.getUsername(uuid).orElse("");

			List<String> nicknames = DataManager.getNicknames(uuid).stream()
					.filter(n -> !n.equalsIgnoreCase(username))
					.collect(Collectors.toList());

			if (nicknames.isEmpty()) {
				sender.sendMessage(username + " §9has no nicknames");
			} else {
				sender.sendMessage("§9Nicknames for §r" + username);
				nicknames.forEach(nick -> sender.sendMessage("§c> §9" + nick));
			}
		} else {
			sender.sendMessage("§cPlayer not found");
		}
	}
}
