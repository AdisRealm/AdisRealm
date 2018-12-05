package adiitya.adisrealm.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@UtilityClass
public final class MessageManager {

	private static final Map<UUID, UUID> messagePairs = new HashMap<>();

	private static final String MESSAGE = "%s §9-> %s§r: %s";

	public void sendMessage(Player sender, Player target, String message) {

		if (!sender.isOnline() || !target.isOnline())
			return;

		messagePairs.put(sender.getUniqueId(), target.getUniqueId());
		messagePairs.put(target.getUniqueId(), sender.getUniqueId());

		String targetName = NameManager.getColoredName(target.getName());

		if (AFKManager.isAFK(target))
			sender.sendMessage(targetName + "§9 is currently AFK:§6§l " + AFKManager.getReason(target).orElse("No reason"));

		sender.sendMessage(getOutMessage(sender, target, message));
		target.sendMessage(getInMessage(target, sender, message));
	}

	private String getOutMessage(Player sender, Player target, String message) {
		String targetName = NameManager.getColoredName(target.getName());
		return String.format(MESSAGE, NameManager.getColor(sender.getName()) + "Me", targetName, message);
	}

	private String getInMessage(Player target, Player sender, String message) {
		String senderName = NameManager.getColoredName(sender.getName());
		return String.format(MESSAGE, senderName, NameManager.getColor(target.getName()) + "Me", message);
	}

	public Optional<UUID> getLastRecipient(UUID uuid) {
		return Optional.ofNullable(messagePairs.get(uuid));
	}
}
