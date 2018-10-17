package adiitya.adisrealm.utils;

import adiitya.mojang4j.MojangAPI;
import adiitya.mojang4j.UserProfile;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@UtilityClass
public class MinecraftUtils {

	private static MojangAPI mojang = MojangAPI.getInstance();
	private final Logger log = Bukkit.getLogger();

	public boolean playerExists(UUID uuid) {
		return mojang.getProfile(uuid).isPresent();
	}

	public Optional<UUID> getUuid(String name) {
		return mojang.getUUID(name);
	}

	public Optional<String> getUsername(UUID uuid) {

		Optional<UserProfile> profile = mojang.getProfile(uuid);

		return profile.map(UserProfile::getUsername);
	}
}
