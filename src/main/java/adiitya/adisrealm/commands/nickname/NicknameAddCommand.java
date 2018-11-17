package adiitya.adisrealm.commands.nickname;

import adiitya.adisrealm.command.SubCommand;
import adiitya.adisrealm.command.completion.TabCompleter;
import adiitya.adisrealm.utils.DataManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class NicknameAddCommand extends SubCommand {

	public NicknameAddCommand(NickMainCommand parent) {
		super("add", i -> i > 0, parent);
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use that!");
			return;
		}

		Player p = (Player) sender;
		String nick = args.get(0);
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
	}

	@Override
	public List<String> tabComplete(CommandSender sender, List<String> args) {
		return new TabCompleter().get(args);
	}
}
