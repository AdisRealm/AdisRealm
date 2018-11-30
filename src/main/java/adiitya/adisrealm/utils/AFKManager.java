package adiitya.adisrealm.utils;

import adiitya.adisrealm.utils.name.NameElement;
import adiitya.adisrealm.utils.name.NameManager;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@UtilityClass
public class AFKManager {

	private static final HashMap<UUID, Optional<String>> afkReasons = new HashMap<>();

	public void enterAFK(UUID uuid, String reason) {

		Optional<String> r = reason.isEmpty() ? Optional.empty() : Optional.of(reason);
		afkReasons.put(uuid, r);

		Player player = Bukkit.getPlayer(uuid);
		player.setPlayerListName(NameManager.getName(uuid, NameElement.FORMATTING_PREFIX, NameElement.AFK_PREFIX));
	}

	public Optional<String> getReason(UUID uuid) {
		return afkReasons.getOrDefault(uuid, Optional.empty());
	}

	public void exitAFK(UUID uuid) {

		Player player = Bukkit.getPlayer(uuid);
		String name = NameManager.getName(uuid, NameElement.FORMATTING_PREFIX);
		player.setPlayerListName(name);

		afkReasons.remove(uuid);
	}

	public boolean isAFK(UUID uuid) {
		return afkReasons.containsKey(uuid);
	}
}
