package adiitya.adisrealm.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;

import java.util.Optional;
import java.util.UUID;

@UtilityClass
public class Utils {

	/**
	 * Gets the UUID of a player. This method is special
	 * as it accepts nicknames as well as usernames.
	 *
	 * @param name The name or nickname
	 *
	 * @return The UUID
	 */
	public Optional<UUID> getUUID(String name) {

		Optional<UUID> nickUUID = DataManager.getUUIDFromNickname(name);

		return nickUUID.isPresent() ? nickUUID : MinecraftUtils.getUuid(name);
	}

	public boolean isMoved(Location loc1, Location loc2, int threshold) {

		int xDiff = loc1.getBlockX() - loc2.getBlockX();
		int yDiff = loc1.getBlockY() - loc2.getBlockY();
		int zDiff = loc1.getBlockZ() - loc2.getBlockZ();

		return xDiff + yDiff + zDiff > threshold;
	}
}
