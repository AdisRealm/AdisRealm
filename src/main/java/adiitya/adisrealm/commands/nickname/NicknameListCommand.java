package adiitya.adisrealm.commands.nickname;

import adiitya.adisrealm.command.SubCommand;
import adiitya.adisrealm.utils.DataManager;
import adiitya.adisrealm.utils.MinecraftUtils;
import adiitya.adisrealm.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public final class NicknameListCommand extends SubCommand {

	public NicknameListCommand(NickMainCommand parent) {
		super("list", count -> true, parent);
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {

		if (args.size() > 1)
			listFromUsername(sender, args.get(1));
		else if (sender instanceof Player)
			listFromUsername(sender, sender.getName());
		else
			sender.sendMessage("§cYou must be a player to use that!");
	}

	@Override
	public List<String> tabComplete(CommandSender sender, List<String> args) {
		return Collections.emptyList();
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
