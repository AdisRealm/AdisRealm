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

	private static final String MESSAGE = "§7%s §9-> §7%s§r: %s";

	public void sendMessage(Player sender, Player target, String message) {

		if (!sender.isOnline() || !target.isOnline())
			return;

		messagePairs.put(sender.getUniqueId(), target.getUniqueId());
		messagePairs.put(target.getUniqueId(), sender.getUniqueId());

		if (AFKManager.isAFK(target.getUniqueId()))
			sender.sendMessage(target.getDisplayName() + "§9 is currently AFK:§6§l " + AFKManager.getReason(target.getUniqueId()).orElse("No reason"));

		sender.sendMessage(getOutMessage(target, message));
		target.sendMessage(getInMessage(sender, message));
	}

	private String getOutMessage(Player target, String message) {
		return String.format(MESSAGE, "Me", target.getDisplayName(), message);
	}

	private String getInMessage(Player sender, String message) {
		return String.format(MESSAGE, sender.getDisplayName(), "Me", message);
	}

	public Optional<UUID> getLastRecipient(UUID uuid) {
		return Optional.ofNullable(messagePairs.get(uuid));
	}
}
