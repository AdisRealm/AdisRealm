package adiitya.adisrealm.utils;

import adiitya.adisrealm.utils.name.PlayerName;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static adiitya.adisrealm.NameColorManager.*;

@UtilityClass
public class AFKManager {

	private static final HashMap<UUID, Optional<String>> afkReasons = new HashMap<>();

	public void enterAFK(UUID uuid, String reason) {

		Optional<String> r = reason.isEmpty() ? Optional.empty() : Optional.of(reason);
		afkReasons.put(uuid, r);

		Player player = Bukkit.getPlayer(uuid);
		player.setPlayerListName(PlayerName.AFK_TAB.build(player.getName()).getName());
	}

	public Optional<String> getReason(UUID uuid) {
		return afkReasons.getOrDefault(uuid, Optional.empty());
	}

	public void exitAFK(UUID uuid) {

		Player player = Bukkit.getPlayer(uuid);
		String name = getColoredName(player.getName());
		player.setPlayerListName(name);

		afkReasons.remove(uuid);
	}

	public boolean isAFK(UUID uuid) {
		return afkReasons.containsKey(uuid);
	}
}
