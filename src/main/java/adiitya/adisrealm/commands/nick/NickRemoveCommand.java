package adiitya.adisrealm.commands.nick;

import adiitya.adisrealm.command.SubCommand;
import adiitya.adisrealm.command.completion.TabCompleter;
import adiitya.adisrealm.utils.DataManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class NickRemoveCommand extends SubCommand {

	public NickRemoveCommand(NickMainCommand parent) {
		super("remove", "<nick>", i -> i > 0, parent);
	}

	@SuppressWarnings("Duplicates")
	@Override
	public void execute(CommandSender sender, List<String> args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use that!");
			return;
		}

		Player player = (Player) sender;
		String nick = args.get(0);

		DataManager.removeNickname(player.getUniqueId(), nick);
		sender.sendMessage("§9Removed nick §c" + nick);
	}

	@Override
	public List<String> tabComplete(CommandSender sender, List<String> args) {
		return new TabCompleter().get(args);
	}
}
