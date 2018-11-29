package adiitya.adisrealm.commands;

import adiitya.adisrealm.command.SingleCommand;
import adiitya.adisrealm.command.completion.TabCompleter;
import adiitya.adisrealm.gui.SettingsGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public final class SettingsCommand extends SingleCommand {

	public SettingsCommand() {
		super("settings", "");
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {

		if (sender instanceof Player) {
			SettingsGUI.show((Player) sender);
		} else {
			sender.sendMessage("You must be a player to use that!");
		}
	}

	@Override
	public List<String> tabComplete(CommandSender sender, List<String> args) {
		return TabCompleter.empty();
	}
}
