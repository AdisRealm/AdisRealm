package adiitya.adisrealm.utils;

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
		player.setPlayerListName("§6[§lAFK§r§6]§7" + player.getDisplayName());
	}

	public void exitAFK(UUID uuid) {

		afkReasons.remove(uuid);

		Player player = Bukkit.getPlayer(uuid);
		player.setPlayerListName("§7" + player.getDisplayName());
	}

	public boolean isAFK(UUID uuid) {
		return afkReasons.containsKey(uuid);
	}
}
