package adiitya.adisrealm.commands;

import adiitya.adisrealm.command.SingleCommand;
import adiitya.adisrealm.command.completion.TabCompleter;
import adiitya.adisrealm.utils.AFKManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class AFKCommand extends SingleCommand {

	public AFKCommand() {
		super("afk", "[reason]");
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use that!");
			return;
		}

		Player player = (Player) sender;
		String reason = String.join(" ", args);

		AFKManager.enterAFK(player, reason);
	}

	@Override
	public List<String> tabComplete(CommandSender sender, List<String> args) {
		return TabCompleter.empty();
	}
}
