package adiitya.adisrealm.commands;

import adiitya.adisrealm.command.SingleCommand;
import adiitya.adisrealm.command.completion.TabCompleter;
import adiitya.adisrealm.utils.AFKManager;
import adiitya.adisrealm.utils.name.NameManager;
import org.bukkit.Bukkit;
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
		String reason = "";

		if (!args.isEmpty())
			reason = String.join(" ", args);

		boolean isAFK = AFKManager.isAFK(player.getUniqueId());

		if (!isAFK) {

			String name = NameManager.getFormattedName(player.getUniqueId());
			String message = "§6§l[§a§l+§6§l]§f" + name + (reason.isEmpty() ? "" : ": §6§l" + reason);

			Bukkit.getConsoleSender().sendMessage(message);
			Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(message));
		}

		AFKManager.enterAFK(player.getUniqueId(), reason);
	}

	@Override
	public List<String> tabComplete(CommandSender sender, List<String> args) {
		return TabCompleter.empty();
	}
}
