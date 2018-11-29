package adiitya.adisrealm.utils.name;

import adiitya.adisrealm.db.tables.records.NamesRecord;
import adiitya.adisrealm.utils.DataManager;
import adiitya.adisrealm.utils.MinecraftUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jooq.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@UtilityClass
public class NameManager {

	private static final Map<UUID, PlayerName> names = new HashMap<>();

	public void loadData() {

		Optional<Result<NamesRecord>> resultOptional = DataManager.getPlayerNames();

		resultOptional.ifPresent(res -> {

			for (NamesRecord rec : res) {

				UUID uuid = UUID.fromString(rec.getUuid());
				NameElement element = NameElement.valueOf(rec.getElement());
				String value = rec.getValue();
				String name = rec.getName();

				PlayerName playerName = names.getOrDefault(uuid, new PlayerName(name));
				playerName.setElement(element, value);

				names.put(uuid, playerName);
			}
		});
	}

	public String getName(UUID uuid, NameElement... elements) {

		if (!MinecraftUtils.playerExists(uuid))
			return "";

		OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
		PlayerName name = names.getOrDefault(uuid, new PlayerName(p.getName()));

		if (!names.containsKey(uuid))
			names.put(uuid, name);

		return name.getName(elements);
	}

	public void addPlayerName(UUID uuid, PlayerName name) {
		names.put(uuid, name);
		name.elements.forEach((k, v) -> DataManager.setPlayerNameElement(uuid, k, v, name.getPlayerName()));
	}

	public void setElement(UUID uuid, NameElement element, String value) {

		if (!MinecraftUtils.playerExists(uuid))
			return;

		OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
		PlayerName name = names.getOrDefault(uuid, new PlayerName(p.getName()));
		name.setElement(element, value);

		if (!names.containsKey(uuid))
			addPlayerName(uuid, name);

		DataManager.setPlayerNameElement(uuid, element, value, name.getPlayerName());
	}

	public String getElement(UUID uuid, NameElement element) {
		return names.getOrDefault(uuid, new PlayerName("")).getElement(element);
	}
}
