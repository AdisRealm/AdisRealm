package adiitya.adisrealm.commands;

import adiitya.adisrealm.command.SingleCommand;
import adiitya.adisrealm.command.completion.TabCompleter;
import adiitya.adisrealm.command.completion.TabCompletions;
import adiitya.adisrealm.utils.NameColorManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ColorCommand extends SingleCommand {

	public ColorCommand() {
		super("color", "<color>");
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use that!");
			return;
		}

		if (args.isEmpty()) {
			sender.sendMessage("§cUSAGE: " + getUsage());
			return;
		}

		String colorString = args.get(0).toUpperCase();

		if (!validateColor(colorString)) {
			sender.sendMessage("§9That's not a valid color!");
			return;
		}

		Player player = (Player) sender;
		ChatColor color = ChatColor.valueOf(colorString);

		if (NameColorManager.setColor(player.getUniqueId(), color))
			player.sendMessage("§9Successfully changed your name color to §" + color.getChar() + color.name().replace("_", " ".toLowerCase()));
		else
			player.sendMessage("§Unable to change your name color");
	}

	private boolean validateColor(String color) {

		try {
			return ChatColor.valueOf(color.toUpperCase()).isColor();
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public List<String> tabComplete(CommandSender sender, List<String> args) {

		List<String> colors = Arrays.stream(ChatColor.values())
				.filter(ChatColor::isColor)
				.map(c -> c.name().toLowerCase())
				.collect(Collectors.toList());

		return new TabCompleter()
				.add(1, colors, TabCompletions::startsWith)
				.get(args);
	}
}
