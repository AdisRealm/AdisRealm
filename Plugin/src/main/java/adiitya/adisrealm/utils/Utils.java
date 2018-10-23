package adiitya.adisrealm.utils;

import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.UUID;

@UtilityClass
public class Utils {

	public Optional<UUID> getUUID(String name) {

		Optional<UUID> nickUUID = DataManager.getUserFromNickname(name);

		return nickUUID.isPresent() ? nickUUID : MinecraftUtils.getUuid(name);
	}
}
