package adiitya.adisrealm.cmd;

import adiitya.adisrealm.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NicknameCommand implements ICommand {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length > 0) {

			String arg = args[0];

			if (arg.equalsIgnoreCase("list"))
				listNicknames(sender, args.length > 1 ? args[1] : "");
		}

		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return null;
	}

	private void listNicknames(CommandSender sender, String name) {

		Optional<UUID> uuid = DatabaseManager.getUserFromNickname(name);

		if (!uuid.isPresent())
			Bukkit.getPlayer(uuid.get()).sendMessage("User nor found");
		else {

			List<String> nicknames = DatabaseManager.getNicknames(uuid.get());
			Player p = Bukkit.getPlayer(uuid.get());

			sender.sendMessage("Nicknames for " + p.getDisplayName());
			nicknames.forEach(sender::sendMessage);
		}
	}
}
