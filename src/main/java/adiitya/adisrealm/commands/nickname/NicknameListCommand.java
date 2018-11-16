package adiitya.adisrealm.commands.nickname;

import adiitya.adisrealm.command.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public final class NicknameListCommand extends SubCommand {

	public NicknameListCommand() {
		super("list", count -> count > 0);
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {

		sender.sendMessage("list nicknames");
	}

	@Override
	public List<String> tabComplete(CommandSender sender, List<String> args) {
		return Collections.emptyList();
	}

	@Override
	public String getUsage() {
		return usage;
	}
}
