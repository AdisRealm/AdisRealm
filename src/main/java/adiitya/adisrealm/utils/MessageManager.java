package adiitya.adisrealm.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * This class handles private messages between users and
 * is used by {@code MessageCommand} and {@code ReplyCommand}
 */
@UtilityClass
public final class MessageManager {

	/** Stores the reply pairs */
	private static final Map<UUID, UUID> messagePairs = new HashMap<>();
	/** The message format */
	private static final String MESSAGE = "%s §9-> %s§r: %s";

	/**
	 * Attempts to send a message between the specified players.
	 *
	 * This method will check if both players are online,
	 * then set the reply pair for each user and send the message.
	 *
	 * @param sender The message sender
	 * @param target The message target
	 * @param message The message
	 */
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

	/**
	 * Gets the formatted message being sent to the sender.
	 *
	 * @param sender The sender
	 * @param target The target
	 * @param message The raw message
	 *
	 * @return The formatted message intended for the sender
	 */
	private String getOutMessage(Player sender, Player target, String message) {
		String targetName = NameManager.getColoredName(target.getName());
		return String.format(MESSAGE, NameManager.getColor(sender.getName()) + "Me", targetName, message);
	}

	/**
	 * Gets the formatted message being sent to the target.
	 *
	 * @param sender The target
	 * @param target The sender
	 * @param message The raw message
	 *
	 * @return The formatted message intended for the target
	 */
	private String getInMessage(Player target, Player sender, String message) {
		String senderName = NameManager.getColoredName(sender.getName());
		return String.format(MESSAGE, senderName, NameManager.getColor(target.getName()) + "Me", message);
	}

	/**
	 * Gets an {@code Optional} contaning the UUID of the last
	 * person messaged by the specified player.
	 *
	 * @param uuid The UUID of the player
	 *
	 * @return A populated Optional, else empty if the player hasn't messaged anyone
	 */
	public Optional<UUID> getLastRecipient(UUID uuid) {
		return Optional.ofNullable(messagePairs.get(uuid));
	}
}
