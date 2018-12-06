package adiitya.adisrealm.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiPredicate;

/**
 * This class handles all AFK related functions.
 */
@UtilityClass
public class AFKManager {

	/** The predicate to check if the player is entering AFK */
	private static final BiPredicate<Player, AFKType> ENTER_PREDICATE = (p, type) -> type.equals(AFKType.ENTER) && !isAFK(p);
	/** The predicate to check if the player is exiting AFK */
	private static final BiPredicate<Player, AFKType> EXIT_PREDICATE = (p, type) -> type.equals(AFKType.EXIT) && isAFK(p);

	/** Stores all AFK reasons. Only AFK players will have a reason stored here */
	private static final HashMap<Player, Optional<String>> afkReasons = new HashMap<>();

	/**
	 * This method makes a player AFK by setting their tab prefix,
	 * registering the specified reason, and optionally announcing it.
	 *
	 * This will only be announced if the player is not currently AFK.
	 *
	 * @param p The player
	 * @param reason The reason for being AFK. Can be empty
	 */
	public void enterAFK(Player p, String reason) {

		NameManager.setTabPrefix(p.getUniqueId(), "§6§l[AFK]");
		announceAFK(new AFKInfo(p, AFKType.ENTER, reason));

		Optional<String> r = reason.isEmpty() ? Optional.empty() : Optional.of(reason);
		afkReasons.put(p, r);
	}

	/**
	 * This method makes the player not AFK by removing their tab prefix,
	 * clearing their AFK reason, and optionally announcing it.
	 *
	 * This will only be announced if the player is currently AFK.
	 *
	 * @param p The player
	 */
	public void exitAFK(Player p) {

		NameManager.setTabPrefix(p.getUniqueId(), "");
		announceAFK(new AFKInfo(p, AFKType.EXIT));

		afkReasons.remove(p);
	}

	private void announceAFK(AFKInfo info) {

		if (!ENTER_PREDICATE.or(EXIT_PREDICATE).test(info.p, info.type))
			return;

		String message = String.format("%s%s%s",
				info.type.message,
				NameManager.getColoredName(info.p.getName()),
				(!info.reason.isEmpty() && info.type == AFKType.ENTER ? "§6: " + info.reason : ""));

		Bukkit.getConsoleSender().sendMessage(message);
		Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(message));
	}

	/**
	 * This method returns an {@code Optional} containing the
	 * specified player's AFK reason.
	 *
	 * @param p The player
	 *
	 * @return A populated Optional, else empty if the player is not AFK or no reason was specified
	 */
	public Optional<String> getReason(Player p) {
		return afkReasons.getOrDefault(p, Optional.empty());
	}

	/**
	 * Gets whether the player is AFK
	 *
	 * @param p The player
	 * @return The AFK status
	 */
	public boolean isAFK(Player p) {
		return afkReasons.containsKey(p);
	}

	private static class AFKInfo {

		private final Player p;
		private final AFKType type;
		private final String reason;

		private AFKInfo(Player p, AFKType type) {
			this(p, type, "");
		}

		private AFKInfo(Player p, AFKType type, String reason) {
			this.p = p;
			this.type = type;
			this.reason = reason;
		}
	}

	private enum AFKType {

		ENTER("§6§l[§a§l+§6§l]§f"),
		EXIT("§6§l[§c§l-§6§l]§f");

		private String message;

		AFKType(String message) {
			this.message = message;
		}
	}
}
