package adiitya.adisrealm.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends Command {

	public PlayerCommand(String name) {
		super(name);
	}

	@Override
	public final void execute(CommandSender sender, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("§cYou must be a player to use that!");
		} else {
			execute((Player) sender, label, args);
		}
	}

	public abstract void execute(Player player, String label, String[] args);
}
