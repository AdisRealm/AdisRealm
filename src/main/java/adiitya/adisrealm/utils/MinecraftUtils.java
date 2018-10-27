package adiitya.adisrealm.utils;

import adiitya.mojang4j.MojangAPI;
import adiitya.mojang4j.UserProfile;
import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.UUID;

@UtilityClass
public class MinecraftUtils {

	private static final MojangAPI mojang = MojangAPI.getInstance();

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
