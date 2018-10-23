package adiitya.adisrealm.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand implements ICommand {

	@Override
	public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use that!");
		} else {
			execute((Player) sender, command, label, args);
		}

		return true;
	}

	public abstract void execute(Player player, Command command, String label, String[] args);
}
